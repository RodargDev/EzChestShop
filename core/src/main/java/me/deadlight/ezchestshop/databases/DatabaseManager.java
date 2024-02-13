package me.deadlight.ezchestshop.databases;

import jakarta.persistence.Persistence;
import me.deadlight.ezchestshop.EzChestShop;
import me.deadlight.ezchestshop.data.Config;
import me.deadlight.ezchestshop.databases.persistence.DatabaseShopObject;
import me.deadlight.ezchestshop.databases.repositories.ShopRepository;
import me.deadlight.ezchestshop.databases.repositories.TransactionRepository;
import me.deadlight.ezchestshop.enums.Database;
import me.deadlight.ezchestshop.utils.Utils;
import me.deadlight.ezchestshop.utils.objects.EzShop;

import jakarta.persistence.EntityManagerFactory;
import me.deadlight.ezchestshop.utils.objects.ShopSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class DatabaseManager {
    public static EntityManagerFactory emf;
    public static ShopRepository shopRepository;
    public static TransactionRepository transactionRepository;

    public static void initializeDatabase() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.schema-generation.database.action", "update");
        properties.put("hibernate.hbm2ddl.auto", "update"); // Hibernate-specific, complements the JPA setting

        if (Config.database_type == Database.MYSQL) {
            // Set MySQL-specific properties
            String databaseName = Config.databasemysql_databasename;
            String ip = Config.databasemysql_ip;
            int port = Config.databasemysql_port;
            String username = Config.databasemysql_username;
            String password = Config.databasemysql_password;
            boolean useSSL = Config.databasemysql_use_ssl;

            properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://" + ip + ":" + port + "/" + databaseName + "?useSSL=" + useSSL);
            properties.put("jakarta.persistence.jdbc.user", username);
            properties.put("jakarta.persistence.jdbc.password", password);
            properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        } else {
            // Set SQLite-specific properties
            File dataFolder = new File(EzChestShop.getPlugin().getDataFolder(), "ecs-database.db");
            if (!dataFolder.exists()) {
                dataFolder.getParentFile().mkdirs();
            }

            properties.put("jakarta.persistence.jdbc.url", "jdbc:sqlite:" + dataFolder.getAbsolutePath());
            properties.put("jakarta.persistence.jdbc.driver", "org.sqlite.JDBC");
            properties.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
        }

        // Common HikariCP settings
        properties.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        properties.put("hibernate.hikari.minimumIdle", "5");
        properties.put("hibernate.hikari.maximumPoolSize", "10");
        properties.put("hibernate.hikari.idleTimeout", "30000");
        properties.put("hibernate.hikari.connectionTimeout", "30000");
        properties.put("hibernate.hikari.poolName", "EzChestShopPool");

        // Attempt to create an EntityManagerFactory
        try {
            emf = Persistence.createEntityManagerFactory("DynamicUnit", properties);
            shopRepository = new ShopRepository(emf);
            transactionRepository = new TransactionRepository(emf);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it, notify the user, etc.)
        }
    }

    public static void closeDatabase() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

     public static List<EzShop> queryStoredShops() {
        List<DatabaseShopObject> shops = shopRepository.findAllShops();
        //convert the database object to EzShop
        return shops.stream().map(DatabaseManager::convertToEzShop).collect(Collectors.toList());
     }

     public static HashMap<Location, EzShop> queryStoredShopsMap() {
        List<DatabaseShopObject> shops = shopRepository.findAllShops();
        //convert the database object to EzShop
        return shops.stream().collect(Collectors.toMap(shop -> Utils.StringtoLocation(shop.getLocation()), DatabaseManager::convertToEzShop, (a, b) -> a, HashMap::new));
     }

     public static void deleteShop(Location loc) {
        shopRepository.deleteShop(Utils.locationToString(loc));
     }

    public static EzShop createShop(Location location, OfflinePlayer owner, ItemStack item, double buyPrice, double sellPrice, boolean messageToggle, boolean disableBuyOption, boolean disableSellOption, List<String> admins, boolean shareIncomeOption, boolean isAdminShop, String rotation, List<String> customMessages) {
        DatabaseShopObject shop = new DatabaseShopObject();
        shop.setLocation(Utils.locationToString(location));
        shop.setOwner(owner.getUniqueId().toString());
        shop.setItem(Utils.encodeItem(item));
        shop.setBuyPrice(buyPrice);
        shop.setSellPrice(sellPrice);
        shop.setMessageToggle(messageToggle);
        shop.setDisableBuyOption(disableBuyOption);
        shop.setDisableSellOption(disableSellOption);
        shop.setAdmins(admins);
        shop.setShareIncomeOption(shareIncomeOption);
        shop.setAdminShopStatus(isAdminShop);
        shop.setRotation(rotation);
        shop.setCustomMessages(customMessages);
        shop = shopRepository.createShop(shop);
        return convertToEzShop(shop);
     }

     public static void setShopOwner(Location loc, OfflinePlayer owner) {
        shopRepository.setShopOwner(Utils.locationToString(loc), owner.getUniqueId().toString());
     }

     public static void setShopBuyPrice(Location loc, double buyPrice) {
        shopRepository.setShopBuyPrice(Utils.locationToString(loc), buyPrice);
     }

     public static void setShopSellPrice(Location loc, double sellPrice) {
        shopRepository.setShopSellPrice(Utils.locationToString(loc), sellPrice);
    }

    public static void setShopMessageToggle(Location loc, boolean messageToggle) {
        shopRepository.setShopMessageToggle(Utils.locationToString(loc), messageToggle);
    }

    public static void setShopDisableBuyOption(Location loc, boolean disableBuyOption) {
        shopRepository.setShopBuyOptionDisable(Utils.locationToString(loc), disableBuyOption);
    }

    public static void setShopDisableSellOption(Location loc, boolean disableSellOption) {
        shopRepository.setShopSellOptionDisable(Utils.locationToString(loc), disableSellOption);
    }

    public static void setShopAdmins(Location loc, List<String> admins) {
        shopRepository.setShopAdmins(Utils.locationToString(loc), admins);
    }

    public static void setShopShareIncomeOption(Location loc, boolean shareIncomeOption) {
        shopRepository.setShopShareIncome(Utils.locationToString(loc), shareIncomeOption);
    }

    public static void setShopAdminShopStatus(Location loc, boolean isAdminShop) {
        shopRepository.setShopAdminShop(Utils.locationToString(loc), isAdminShop);
    }

    public static void setShopRotation(Location loc, String rotation) {
        shopRepository.setShopRotation(Utils.locationToString(loc), rotation);
    }

    public static void setShopCustomMessages(Location loc, List<String> customMessages) {
        shopRepository.setShopCustomMessages(Utils.locationToString(loc), customMessages);
    }

     public static EzShop convertToEzShop(DatabaseShopObject shop) {
        Long shopId = shop.getId();
        String location = shop.getLocation();
        String owner = shop.getOwner();
        String item = shop.getItem();
        double buyPrice = shop.getBuyPrice();
        double sellPrice = shop.getSellPrice();
        boolean messageToggle = shop.isMessageToggle();
        boolean disableBuyOption = shop.isDisableBuyOption();
        boolean disableSellOption = shop.isDisableSellOption();
        boolean shareIncome = shop.isShareIncomeOption();
        boolean isAdminShop = shop.isAdminShopStatus();
        List<String> customMessages = shop.getCustomMessages();
        String rotation = shop.getRotation();
        List<String> shopAdmins = shop.getAdmins();
        Location loc = Utils.StringtoLocation(location);
        OfflinePlayer offowner = Bukkit.getOfflinePlayer(UUID.fromString(owner));
        ItemStack itemstack = Utils.decodeItem(item);
        //now this section is for Shop Settings
        ShopSettings settings = new ShopSettings(messageToggle, disableBuyOption, disableSellOption, shopAdmins, shareIncome, isAdminShop, rotation, customMessages);
        return new EzShop(shopId, loc, offowner, itemstack, buyPrice, sellPrice, settings);
     }

}

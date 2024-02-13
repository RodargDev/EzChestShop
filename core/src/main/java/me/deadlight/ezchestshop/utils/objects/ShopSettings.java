package me.deadlight.ezchestshop.utils.objects;

import me.deadlight.ezchestshop.data.Config;
import me.deadlight.ezchestshop.data.ShopContainer;
import org.bukkit.Location;

import java.util.*;
import java.util.stream.Collectors;

public class ShopSettings {
    private boolean msgtoggle;
    private boolean dbuy;
    private boolean dsell;
    private List<String> admins;
    private boolean shareincome;
    private boolean adminshop;
    private String rotation;
    private List<String> customMessages;
    private static List<String> customMessagesInitialChecked = new ArrayList<>();
    private static Map<UUID, Map<Location, String>> customMessagesTotal = new HashMap<>();

    public ShopSettings(boolean msgtoggle, boolean dbuy, boolean dsell, List<String> admins, boolean shareincome,
             boolean adminshop, String rotation, List<String> customMessages) {
        this.msgtoggle = msgtoggle;
        this.dbuy = dbuy;
        this.dsell = dsell;
        this.admins = admins;
        this.shareincome = shareincome;
        this.adminshop = adminshop;
        this.rotation = rotation;
        this.customMessages = customMessages;
    }

    private ShopSettings(ShopSettings settings) {
        this.msgtoggle = settings.msgtoggle;
        this.dbuy = settings.dbuy;
        this.dsell = settings.dsell;
        this.admins = settings.admins;
        this.shareincome = settings.shareincome;
        this.adminshop = settings.adminshop;
        this.rotation = settings.rotation;
        this.customMessages = settings.customMessages;
    }

    public ShopSettings clone() {
        return new ShopSettings(this);
    }

    public boolean isMsgtoggle() {
        return msgtoggle;
    }

    public ShopSettings setMsgtoggle(boolean msgtoggle) {
        this.msgtoggle = msgtoggle;
        return this;
    }

    public boolean isDbuy() {
        return dbuy;
    }

    public ShopSettings setDbuy(boolean dbuy) {
        this.dbuy = dbuy;
        return this;
    }

    public boolean isDsell() {
        return dsell;
    }

    public ShopSettings setDsell(boolean dsell) {
        this.dsell = dsell;
        return this;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setUUIDAdmins(List<UUID> admins) {
        this.admins = admins.stream().map(UUID::toString).collect(Collectors.toList());
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public List<String> getCustomMessages() {
        return customMessages;
    }

    public void setCustomMessages(List<String> customMessages) {
        this.customMessages = customMessages;
    }

    public boolean isShareincome() {
        return shareincome;
    }

    public ShopSettings setShareincome(boolean shareincome) {
        this.shareincome = shareincome;
        return this;
    }


    public boolean isAdminshop() {
        return adminshop;
    }

    public ShopSettings setAdminshop(boolean adminshop) {
        this.adminshop = adminshop;
        return this;
    }

    public String getRotation() {
        return rotation == null ? Config.settings_defaults_rotation : rotation;
    }

    public ShopSettings setRotation(String rotation) {
        this.rotation = rotation;
        return this;
    }

    public static Map<Location, String> getAllCustomMessages(String owner) {

        List<EzShop> ezShops = ShopContainer.getShopFromOwner(UUID.fromString(owner)).stream().filter(
                ezShop -> !ezShop.getSettings().customMessages.isEmpty()
        ).collect(Collectors.toList());

        Map<Location, String> stringMap = new HashMap<>();

        for (EzShop ezShop : ezShops) {
            stringMap.put(ezShop.getLocation(),ezShop.getSettings().customMessages.get(0));
        }

        return stringMap;
    }

    private static Map<Location, String> fetchAllCustomMessages(String owner) {
        return getAllCustomMessages(owner);
    }

    public static List<String> getCustomMessagesInitialChecked() {
        return customMessagesInitialChecked;
    }

    public static void setCustomMessagesInitialChecked(List<String> customMessagesInitialChecked) {
        ShopSettings.customMessagesInitialChecked = customMessagesInitialChecked;
    }

    public static Map<UUID, Map<Location, String>> getCustomMessagesTotal() {
        return customMessagesTotal;
    }

    public static void setCustomMessagesTotal(Map<UUID, Map<Location, String>> customMessagesTotal) {
        ShopSettings.customMessagesTotal = customMessagesTotal;
    }
}

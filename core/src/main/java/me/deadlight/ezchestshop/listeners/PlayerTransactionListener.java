package me.deadlight.ezchestshop.listeners;
import me.deadlight.ezchestshop.data.Config;
import me.deadlight.ezchestshop.data.LanguageManager;
import me.deadlight.ezchestshop.events.PlayerTransactEvent;
import me.deadlight.ezchestshop.EzChestShop;
import me.deadlight.ezchestshop.utils.Utils;
import me.deadlight.ezchestshop.utils.WebhookSender;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class PlayerTransactionListener implements Listener {

    LanguageManager lm = new LanguageManager();

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @EventHandler
    public void onTransaction(PlayerTransactEvent event) {
        sendDiscordWebhook(event);
        if (((TileState)event.getContainerBlock().getState()).getPersistentDataContainer().get(new NamespacedKey(EzChestShop.getPlugin(), "msgtoggle"), PersistentDataType.INTEGER) == 1) {

            OfflinePlayer owner = event.getOwner();
            List<UUID> getters = event.getAdminsUUID();
            getters.add(owner.getUniqueId());

            if (event.isBuy()) {
                for (UUID adminUUID : getters) {
                    Player admin = Bukkit.getPlayer(adminUUID);
                    if (admin != null) {
                        if (admin.isOnline()) {
                            admin.getPlayer().sendMessage(lm.transactionBuyInform(event.getCustomer().getName(), event.getCount(),
                                    event.getItemName(), event.getPrice()));
                        }
                    }
                }
            } else {
                for (UUID adminUUID : getters) {
                    Player admin = Bukkit.getPlayer(adminUUID);
                    if (admin != null) {
                        if (admin.isOnline()) {
                            if (admin.isOnline()) {
                                admin.getPlayer().sendMessage(lm.transactionSellInform(event.getCustomer().getName(), event.getCount(),
                                        event.getItemName(), event.getPrice()));
                            }
                        }
                    }
                }
            }






        }

    }


    public void sendDiscordWebhook(PlayerTransactEvent event) {

        if (event.isBuy()) {
            //Discord Webhook
            EzChestShop.getPlugin().getServer().getScheduler().runTaskAsynchronously(
                    EzChestShop.getPlugin(), () -> {
                        WebhookSender.sendDiscordNewTransactionAlert(
                                event.getCustomer().getName(),
                                event.getOwner().getName(),
                                //Show Item name if it has custom name, otherwise show localized name
                                event.getItem().getItemMeta().hasDisplayName() ? event.getItem().getItemMeta().getDisplayName() : event.getItemName(),
                                //Turn the price into string
                                String.valueOf(event.getPrice()),
                                Config.currency,
                                //Display shop location as this: world, x, y, z
                                event.getContainerBlock().getWorld().getName() + ", " + event.getContainerBlock().getX() + ", " + event.getContainerBlock().getY() + ", " + event.getContainerBlock().getZ(),
                                //Display Time as this: 2023/5/1 | 23:10:23
                                formatter.format(event.getTime()).replace("T", " | ").replace("Z", "").replace("-", "/"),
                                String.valueOf(event.getCount()),
                                event.getOwner().getName()
                        );
                    }
            );
        } else {
            //Discord Webhook
            EzChestShop.getPlugin().getServer().getScheduler().runTaskAsynchronously(
                    EzChestShop.getPlugin(), () -> {
                        WebhookSender.sendDiscordNewTransactionAlert(
                                event.getOwner().getName(),
                                event.getCustomer().getName(),
                                //Show Item name if it has custom name, otherwise show localized name
                                event.getItem().getItemMeta().hasDisplayName() ? event.getItem().getItemMeta().getDisplayName() : event.getItemName(),
                                //Turn the price into string
                                String.valueOf(event.getPrice()),
                                Config.currency,
                                //Display shop location as this: world, x, y, z
                                event.getContainerBlock().getWorld().getName() + ", " + event.getContainerBlock().getX() + ", " + event.getContainerBlock().getY() + ", " + event.getContainerBlock().getZ(),
                                //Display Time as this: 2023/5/1 | 23:10:23
                                formatter.format(event.getTime()),
                                String.valueOf(event.getCount()),
                                event.getOwner().getName()
                        );
                    }
            );
        }

    }

}

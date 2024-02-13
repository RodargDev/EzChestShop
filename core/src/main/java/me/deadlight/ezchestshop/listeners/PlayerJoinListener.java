package me.deadlight.ezchestshop.listeners;

import me.deadlight.ezchestshop.data.Config;
import me.deadlight.ezchestshop.data.LanguageManager;
import me.deadlight.ezchestshop.EzChestShop;
import me.deadlight.ezchestshop.utils.BlockOutline;
import me.deadlight.ezchestshop.utils.Utils;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerJoinListener implements Listener {

    LanguageManager lm = new LanguageManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws NoSuchFieldException, IllegalAccessException {
        Player player = event.getPlayer();
        Utils.versionUtils.injectConnection(player);
        /**
         * Prepare the database player values.
         *
         * @param evt
         */

        if (Config.emptyShopNotificationOnJoin) {
            List<Block> blocks = Utils.getNearbyEmptyShopForAdmins(player);
            if (blocks.isEmpty()) {
                return;
            }
            List<Note.Tone> tones = new ArrayList<>();
            //add the tones to the list altogether
            AtomicInteger noteIndex = new AtomicInteger();
            tones.add(Note.Tone.A);
            tones.add(Note.Tone.B);
            tones.add(Note.Tone.C);
            tones.add(Note.Tone.D);
            tones.add(Note.Tone.E);
            tones.add(Note.Tone.F);
            tones.add(Note.Tone.G);
            AtomicInteger actionBarCounter = new AtomicInteger();
            EzChestShop.getPlugin().getServer().getScheduler().runTaskLaterAsynchronously(EzChestShop.getPlugin(), () -> {

                //Iterate through each block with an asychronous delay of 5 ticks
                blocks.forEach(b -> {
                    BlockOutline outline = new BlockOutline(player, b);
                    outline.destroyAfter = 10;
                    int index = blocks.indexOf(b);
                    EzChestShop.getPlugin().getServer().getScheduler().runTaskLater(EzChestShop.getPlugin(), () -> {
                        outline.showOutline();
                        if (outline.muted) {
                            return;
                        }
                        actionBarCounter.getAndIncrement();
                        Utils.sendActionBar(
                                player,
                                lm.emptyShopActionBar(actionBarCounter.get())
                        );

                        player.playNote(b.getLocation(), Instrument.BIT, Note.flat(1, tones.get(noteIndex.get())));
                        noteIndex.getAndIncrement();
                        if (noteIndex.get() == 7) {
                            noteIndex.set(0);
                        }

                    }, 2L * index);
                });

            }, 80L);
        }
    }

}

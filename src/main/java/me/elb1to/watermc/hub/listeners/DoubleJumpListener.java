package me.elb1to.watermc.hub.listeners;

import me.elb1to.watermc.hub.user.HubPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener {

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        Sound sound = Sound.BAT_TAKEOFF;

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
        if (!hubPlayer.isFlyMode()) {
            event.setCancelled(true);
            player.setAllowFlight(true);
            player.setFlying(false);
            player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
            player.playSound(player.getLocation(), sound, 2.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

        if (!hubPlayer.isFlyMode()) {
            if (player.getGameMode() != GameMode.CREATIVE && player.getLocation().subtract(0.0, 2.0, 0.0).getBlock().getType() != Material.AIR && !player.isFlying()) {
                player.setAllowFlight(true);
            }
        }
    }
}

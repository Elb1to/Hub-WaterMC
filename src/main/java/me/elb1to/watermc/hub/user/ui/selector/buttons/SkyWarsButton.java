package me.elb1to.watermc.hub.user.ui.selector.buttons;

import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import me.elb1to.watermc.hub.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/5/2021 @ 9:50 AM
 */
public class SkyWarsButton extends Button {

	Queue queue = new Queue("SKYWARS");

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.BOW)
				.setName("&3&lSkyWars")
				.setLore(Arrays.asList(
						"&7Jugadores: &f0",
						"&7En Cola: &f" + queue.getPlayers().size(),
						" ",
						"&7 ▶ &bTorneos mensuales",
						"&7 ▶ &bSkyWars Rush y PotPvP",
						"&7 ▶ &bVotacion de eventos en partida",
						"&7 ▶ &bStats y Opciones por jugador",
						" ",
						"&aClick para entrar en cola!"
						)
				).get();
	}

	@Override
	public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
		playSuccess(player);
		queue.add(player);
	}
}

package me.elb1to.watermc.hub.user.ui.selector.buttons;

import me.elb1to.watermc.hub.Hub;
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
 * Date: 2/5/2021 @ 9:49 AM
 */
public class PracticeButton extends Button {

	Queue queue = new Queue("PRACTICE");

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.POTION)
				.setDurability(69)
				.setName("&3&lPractice")
				.setLore(Arrays.asList(
						"&7Jugadores: &f" + Hub.getInstance().getServerPlayers().get("practice"),
						"&7En Cola: &f" + queue.getPlayers().size(),
						" ",
						"&7 ▶ &bHolograma y Menu de Leaderboards",
						"&7 ▶ &bColas Unranked/Ranked/Premium",
						"&7 ▶ &bStats y Opciones por jugador",
						"&7 ▶ &bMuchos eventos (/host)",
						" ",
						"&aClick para entrar en cola!"
						)
				).get();
	}

	@Override
	public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
		player.closeInventory();

		if (Hub.getInstance().getQueueManager().isQueueing(player)) {
			playFail(player);
			return;
		}
		playSuccess(player);
		player.performCommand("queue join PRACTICE");
	}
}

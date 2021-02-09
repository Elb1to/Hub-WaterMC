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
 * Date: 2/5/2021 @ 9:50 AM
 */
public class SkyKitsButton extends Button {

	Queue queue = new Queue("SKYKITS");

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.INK_SACK)
				.setDurability(2)
				.setName("&3&lSkyKits")
				.setLore(Arrays.asList(
						"&7Jugadores: &f" + Hub.getInstance().getServerPlayers().get("dev"),
						"&7En Cola: &f" + queue.getPlayers().size(),
						" ",
						"&7 ‣ &bMapa 2500x2500",
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
		player.performCommand("queue join SKYKITS");
	}
}

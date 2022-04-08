package me.elb1to.watermc.hub.user.ui.selector.buttons;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.user.NewHubPlayer;
import me.elb1to.watermc.hub.utils.CC;
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
public class HCKButton extends Button {

	Queue queue = new Queue("HCK");

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.INK_SACK)
				.setAmount(30)
				.setDurability(14)
				.setName("&3&lHCK")
				.setLore(Arrays.asList(
						"&7Jugadores: &f" + Hub.getInstance().getServerPlayers().get("HCK"),
						"&7En Cola: &f" + queue.getPlayers().size(),
						" ",
						"&7 ‣ &bMapa 2500x2500",
						"&7 ‣ &b30 Mans &f- &b0 allies",
						"&7 ‣ &bKoths & Eventos diarios",
						"&7 ‣ &bGKits &a&lGRATIS &bpara todos",
						"&7 ‣ &bNo hay &c&lVIDAS &bpara nadie",
						"&7 ‣ &bProteccion II &f- &bSharpness II",
						"&7 ‣ &bPremio &9&lPayPal &bpara FTop",
						" ",
						"&aClick para entrar en cola!"
						)
				).get();
	}

	@Override
	public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
		player.closeInventory();

		NewHubPlayer newHubPlayer = Hub.getInstance().getHubPlayerManager().getPlayerData(player.getUniqueId());
		newHubPlayer.setClickedHCFServer("HCK");
		player.sendMessage("[Debug] Clicked Server == " + newHubPlayer.getClickedHCFServer());

		if (Hub.getInstance().getQueueManager().isQueueing(player)) {
			playFail(player);
			player.sendMessage(CC.translate("&cYa estás en cola!"));
			return;
		}

		playSuccess(player);
		player.performCommand("queue join HCK");
	}
}

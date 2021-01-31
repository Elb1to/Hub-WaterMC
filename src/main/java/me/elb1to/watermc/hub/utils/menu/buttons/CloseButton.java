package me.elb1to.watermc.hub.utils.menu.buttons;

import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import me.elb1to.watermc.hub.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CloseButton extends Button {

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.INK_SACK).setDurability(1).setName("&cClose").get();
	}

	@Override
	public void clicked(Player player, int i, ClickType clickType, int hb) {
		playNeutral(player);
		player.closeInventory();
	}
}

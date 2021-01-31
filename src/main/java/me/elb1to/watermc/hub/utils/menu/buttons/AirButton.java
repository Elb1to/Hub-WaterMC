package me.elb1to.watermc.hub.utils.menu.buttons;

import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import me.elb1to.watermc.hub.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class AirButton extends Button {

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.AIR).get();
	}

	@Override
	public boolean shouldCancel(Player player, int slot, ClickType clickType) {
		return true;
	}
}

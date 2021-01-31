package me.elb1to.watermc.hub.utils.menu.pagination;

import lombok.AllArgsConstructor;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@AllArgsConstructor
public class JumpToPageButton extends Button {

	private int page;
	private PaginatedMenu menu;
	private boolean current;

	@Override
	public ItemStack getButtonItem(Player player) {
		ItemStack itemStack = new ItemStack(this.current ? Material.ENCHANTED_BOOK : Material.BOOK, this.page);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(CC.translate("&cPage " + this.page));

		if (this.current) {
			itemMeta.setLore(Arrays.asList(
					"",
					CC.translate("&aCurrent page")
			));
		}
		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	@Override
	public void clicked(Player player, int i, ClickType clickType, int hb) {
		this.menu.modPage(player, this.page - this.menu.getPage());

		Button.playNeutral(player);
	}
}

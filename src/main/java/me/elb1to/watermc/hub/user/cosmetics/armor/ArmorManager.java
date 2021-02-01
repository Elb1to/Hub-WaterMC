package me.elb1to.watermc.hub.user.cosmetics.armor;

import lombok.Getter;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.config.FileConfig;
import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */
@Getter
public class ArmorManager {

	private final Hub plugin = Hub.getInstance();
	private final List<Armor> armorsList = new ArrayList<>();
	private final FileConfig config = plugin.getArmorConfig();

	public static void putPlayerArmor(Armor.ArmorContents armorContents, Player player) {
		switch (armorContents.getArmorType()) {
			case HELMET:
				player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET)
						.setName(armorContents.getName())
						.setArmorColor(CC.getBukkitColor(armorContents.getColor()))
						.setLore(armorContents.getLore())
						.setAmount(1)
						.get()
				);
				break;
			case CHESPLATE:
				player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE)
						.setName(armorContents.getName())
						.setArmorColor(CC.getBukkitColor(armorContents.getColor()))
						.setLore(armorContents.getLore())
						.setAmount(1)
						.get()
				);
				break;
			case LEGGINGS:
				player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS)
						.setName(armorContents.getName())
						.setArmorColor(CC.getBukkitColor(armorContents.getColor()))
						.setLore(armorContents.getLore())
						.setAmount(1)
						.get()
				);
				break;
			case BOOTS:
				player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS)
						.setName(armorContents.getName())
						.setArmorColor(CC.getBukkitColor(armorContents.getColor()))
						.setLore(armorContents.getLore())
						.setAmount(1)
						.get()
				);
				break;
		}
	}

	public void registerArmor() {
		try {
			for (String path : plugin.getArmorConfig().getConfiguration().getKeys(false)) {
				String rankName = config.getString(path + ".RANK");
				List<Armor.ArmorContents> armorList = new ArrayList<>();
				for (String armorPath : plugin.getArmorConfig().getConfiguration().getConfigurationSection(path + ".CONTENTS").getKeys(false)) {
					Armor.ArmorContents armorContents = new Armor.ArmorContents(
							Armor.ArmorType.valueOf(armorPath.toUpperCase()),
							config.getBoolean(path + ".CONTENTS." + armorPath + ".ENABLED"),
							config.getString(path + ".CONTENTS." + armorPath + ".COLOR"),
							config.getString(path + ".CONTENTS." + armorPath + ".NAME"),
							config.getStringList(path + ".CONTENTS." + armorPath + ".LORE").isEmpty() ? new ArrayList<>() : config.getStringList(path + ".CONTENTS." + armorPath + ".LORE")
					);
					armorList.add(armorContents);
				}
				Armor armor = new Armor(rankName, armorList);
				this.armorsList.add(armor);
			}
			plugin.getLogger().info("[Armor] Successfully register " + this.armorsList.size() + " armors.");
		} catch (Exception e) {
			plugin.getLogger().info("[Armor] An error occurred while loading the armors. Please check your config!");
		}
	}

	public Armor getArmorByRank(String rank) {
		return armorsList.stream().filter(armor -> armor.getRankName().equalsIgnoreCase(rank)).findFirst().orElse(null);
	}
}

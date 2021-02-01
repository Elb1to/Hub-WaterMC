package me.elb1to.watermc.hub.user.cosmetics.armor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */
@Getter @Setter @ToString
public class Armor {

	private String rankName;

	private List<ArmorContents> armorContentsList;
	private List<ArmorContents> activeContents;

	public Armor(String rankName, List<ArmorContents> armorContentsList) {
		this.rankName = rankName;
		this.armorContentsList = armorContentsList;
		this.activeContents = new ArrayList<>();

		setup();
	}

	public void setup() {
		armorContentsList.stream().filter(ArmorContents::isEnabled).forEach(armorContents -> activeContents.add(armorContents));
	}

	public void handlePlayer(Player player) {
		activeContents.forEach(armorContents -> ArmorManager.putPlayerArmor(armorContents, player));
	}

	public enum ArmorType {
		HELMET, CHESPLATE, LEGGINGS, BOOTS
	}

	@AllArgsConstructor @Getter @Setter @ToString
	public static class ArmorContents {

		private ArmorType armorType;
		private boolean enabled;
		private String color;
		private String name;
		private List<String> lore;
	}
}

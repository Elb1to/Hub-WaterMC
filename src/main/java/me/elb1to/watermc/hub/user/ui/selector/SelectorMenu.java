package me.elb1to.watermc.hub.user.ui.selector;

import me.elb1to.watermc.hub.user.ui.selector.buttons.*;
import me.elb1to.watermc.hub.utils.menu.Button;
import me.elb1to.watermc.hub.utils.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/5/2021 @ 9:49 AM
 */
public class SelectorMenu extends Menu {

	@Override
	public String getTitle(Player player) {
		return ChatColor.DARK_GRAY + "Selecciona un Servidor";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		buttons.put(10, new PracticeButton());
		buttons.put(11, new SkyWarsButton());
		buttons.put(13, new SkyKitsButton());
		buttons.put(14, new KitmapButton());
		buttons.put(15, new HCFButton());
		buttons.put(16, new HCKButton());

		return buttons;
	}

	@Override
	public int size(Map<Integer, Button> buttons) {
		return 9 * 3;
	}
}

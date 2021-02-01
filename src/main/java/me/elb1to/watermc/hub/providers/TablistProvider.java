package me.elb1to.watermc.hub.providers;

import club.frozed.tablist.adapter.TabAdapter;
import club.frozed.tablist.entry.TabEntry;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 1/31/2021 @ 2:42 PM
 */
public class TablistProvider implements TabAdapter {

	@Override
	public String getHeader(Player player) {
		return null;
	}

	@Override
	public String getFooter(Player player) {
		return null;
	}

	@Override
	public List<TabEntry> getLines(Player player) {
		return null;
	}
}

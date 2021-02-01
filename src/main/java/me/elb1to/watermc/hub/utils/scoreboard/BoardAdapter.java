package me.elb1to.watermc.hub.utils.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface BoardAdapter {

	String getTitle(Player player);

	List<String> getLines(Player player);

	BoardStyle getBoardStyle(Player player);
}


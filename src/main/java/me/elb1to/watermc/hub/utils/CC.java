package me.elb1to.watermc.hub.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 30/01/2020 @ 21:03
 */
public class CC {

	public static String MENU_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------";
	public static String CHAT_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";

	public static String translate(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static List<String> translate(List<String> lines) {
		List<String> toReturn = new ArrayList<>();

		for (String line : lines) {
			toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
		}

		return toReturn;
	}

	public static String strip(String in) {
		return ChatColor.stripColor(translate(in));
	}

	public static List<String> translate(String[] lines) {
		List<String> toReturn = new ArrayList<>();

		for (String line : lines) {
			if (line != null) {
				toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
			}
		}

		return toReturn;
	}
}

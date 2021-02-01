package me.elb1to.watermc.hub.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 30/01/2020 @ 21:03
 */
public class CC {

	public static String SB_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "----------------------";
	public static String MENU_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------";
	public static String CHAT_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";

	public static String translate(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
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

	public static Color getBukkitColor(String color) {
		String name = color.toUpperCase();
		switch (name) {
			case "RED":
				return Color.RED;
			case "BLUE":
				return Color.BLUE;
			case "BLACK":
				return Color.BLACK;
			case "YELLOW":
				return Color.YELLOW;
			case "AQUA":
				return Color.AQUA;
			case "FUCHSIA":
				return Color.FUCHSIA;
			case "GRAY":
				return Color.GRAY;
			case "GREEN":
				return Color.GREEN;
			case "MAROON":
				return Color.MAROON;
			case "NAVY":
				return Color.NAVY;
			case "ORANGE":
				return Color.ORANGE;
			case "LIME":
				return Color.LIME;
			case "OLIVE":
				return Color.OLIVE;
			case "PURPLE":
				return Color.PURPLE;
			case "TEAL":
				return Color.TEAL;
			case "SILVER":
				return Color.SILVER;
		}

		return Color.WHITE;
	}
}

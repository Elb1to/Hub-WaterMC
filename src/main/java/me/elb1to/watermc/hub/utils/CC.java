package me.elb1to.watermc.hub.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 30/01/2020 @ 21:03
 */
public class CC {

	public static String SB_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------";
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

	public static String centerMessage(String message) {
		String[] lines = ChatColor.translateAlternateColorCodes('&', message).split("\n", 40);
		StringBuilder returnMessage = new StringBuilder();

		for (String line : lines) {
			int messagePxSize = 0;
			boolean previousCode = false;
			boolean isBold = false;

			for (char c : line.toCharArray()) {
				if (c == '§') {
					previousCode = true;
				} else if (previousCode) {
					previousCode = false;
					isBold = c == 'l';
				} else {
					CenteringEnum centeringEnum = CenteringEnum.getDefaultCenteringInfo(c);
					messagePxSize = isBold ? messagePxSize + centeringEnum.getBoldLength() : messagePxSize + centeringEnum.getLength();
					messagePxSize++;
				}
			}

			int toCompensate = 154 - messagePxSize / 2;
			int spaceLength = CenteringEnum.SPACE.getLength() + 1;
			int compensated = 0;

			StringBuilder sb = new StringBuilder();
			while (compensated < toCompensate) {
				sb.append(" ");
				compensated += spaceLength;
			}

			returnMessage.append(sb.toString()).append(line).append("\n");
		}

		return returnMessage.toString();
	}

	public static String build(final String[] args, final int start) {
		if (start >= args.length) {
			return "";
		}

		return ChatColor.stripColor(String.join(" ", Arrays.copyOfRange(args, start, args.length)));
	}
}

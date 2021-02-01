package me.elb1to.watermc.hub.managers;

import me.elb1to.watermc.hub.Hub;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 30/01/2020 @ 20:30
 */
public class VaultManager {

	private Chat chat;

	public void register() {
		RegisteredServiceProvider<Chat> registration = Hub.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
		if (registration == null) {
			return;
		}

		this.chat = registration.getProvider();
	}

	public String getRankPrefix(Player player) {
		return chat == null ? "Unknown" : chat.getPlayerPrefix(player);
	}

	public String getRankName(Player player) {
		return chat == null ? "Unknown" : chat.getPrimaryGroup(player);
	}
}

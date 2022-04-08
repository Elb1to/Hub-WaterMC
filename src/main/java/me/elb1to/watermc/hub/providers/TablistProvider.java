package me.elb1to.watermc.hub.providers;

import club.frozed.tablist.adapter.TabAdapter;
import club.frozed.tablist.entry.TabEntry;
import club.frozed.tablist.skin.Skin;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.utils.CC;
import me.ryzeon.rtags.data.player.PlayerData;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 1/31/2021 @ 2:42 PM
 */
public class TablistProvider implements TabAdapter {

	private final Hub plugin = Hub.getInstance();

	@Override
	public String getHeader(Player player) {
		return CC.translate("&3&lWaterMC &fNetwork");
	}

	@Override
	public String getFooter(Player player) {
		return CC.translate("&7Estas jugando en &fwatermc.gg");
	}

	@Override
	public List<TabEntry> getLines(Player player) {
		List<TabEntry> lines = new ArrayList<>();
		int playerPing = ((CraftPlayer) player).getHandle().ping;

		GameProfile profile = ((CraftPlayer) player).getHandle().getProfile();
		Property property = profile.getProperties().get("textures").stream().findFirst().orElse(null);

		// Header
		lines.add(new TabEntry(1, 1, "&3&lWaterMC").setSkin(Skin.CREEPER_SKIN).setPing(-1));
		try {
			lines.add(new TabEntry(1, 2, "&7Jugadores: &f" + this.plugin.getNetworkTotalPlayer("BUNGEE", true) + "&7/&f2,500").setPing(-1));
		} catch (Exception e) {
			lines.add(new TabEntry(1, 2, "&7Jugadores: &c≠_FETCHED&7/&f2,500").setPing(-1));
		}

		// Player Info
		lines.add(new TabEntry(1, 4, "&3&lPerfil").setPing(-1));
		if (property != null) {
			lines.add(new TabEntry(1, 5, player.getName()).setPing(playerPing).setSkin(new Skin(property.getValue(), property.getSignature())));
		} else {
			lines.add(new TabEntry(1, 5, player.getName()).setPing(playerPing));
		}

		lines.add(new TabEntry(0, 5, "&3&lRango").setPing(-1));
		lines.add(new TabEntry(0, 6, this.plugin.getVaultPerm().getPrimaryGroup(player)).setPing(-1));

		lines.add(new TabEntry(2, 5, "&3&lTokens").setPing(-1));
		try {
			lines.add(new TabEntry(2, 6, "" + PlayerData.getData(player.getUniqueId()).getTokens()).setPing(-1));
		} catch (Exception e) {
			lines.add(new TabEntry(2, 6, "&cERROR();").setPing(-1));
		}

		// Servers
		lines.add(new TabEntry(1, 8, "&3&lServidores").setPing(-1));

		lines.add(new TabEntry(0, 10, "&3&lPractice").setPing(-1).setSkin(Skin.PING_SKIN));
		lines.add(new TabEntry(0, 11, "&7Jugadores: " + this.plugin.getServerPlayers().get("practice")).setPing(-1));

		lines.add(new TabEntry(1, 10, "&3&lSkyWars").setPing(-1).setSkin(Skin.NO_PING_SKIN));
		lines.add(new TabEntry(1, 11, "&cOffline").setPing(-1));

		lines.add(new TabEntry(2, 10, "&3&lSkyKits").setPing(-1).setSkin(Skin.PING_SKIN));
		lines.add(new TabEntry(2, 11, "&7Jugadores: " + this.plugin.getServerPlayers().get("dev")).setPing(-1));

		lines.add(new TabEntry(0, 13, "&3&lKitMap").setPing(-1).setSkin(Skin.PING_SKIN));
		lines.add(new TabEntry(0, 14, "&7Jugadores: " + this.plugin.getServerPlayers().get("kits")).setPing(-1));

		lines.add(new TabEntry(1, 13, "&3&lHCF").setPing(-1).setSkin(Skin.PING_SKIN));
		lines.add(new TabEntry(1, 14, "&7Jugadores: " + this.plugin.getServerPlayers().get("HCF")).setPing(-1));

		lines.add(new TabEntry(2, 13, "&3&lHCK").setPing(-1).setSkin(Skin.PING_SKIN));
		lines.add(new TabEntry(2, 14, "&7Jugadores: " + this.plugin.getServerPlayers().get("HCK")).setPing(-1));

		// Footer
		lines.add(new TabEntry(0, 17, "&a&lTienda").setPing(-1).setSkin(Skin.DOLAR_SKIN));
		lines.add(new TabEntry(1, 17, "&b&lTwitter").setPing(-1).setSkin(Skin.TWITTER_SKIN));
		lines.add(new TabEntry(2, 17, "&9&lDiscord").setPing(-1).setSkin(Skin.DISCORD_SKIN));

		lines.add(new TabEntry(0, 18, "&fstore.watermc.gg").setPing(-1));
		lines.add(new TabEntry(1, 18, "&f@WaterMCNetwork").setPing(-1));
		lines.add(new TabEntry(2, 18, "&fdiscord.watermc.gg").setPing(-1));

		return lines;
	}
}

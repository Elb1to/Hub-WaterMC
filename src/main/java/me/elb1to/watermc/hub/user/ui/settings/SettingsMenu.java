package me.elb1to.watermc.hub.user.ui.settings;

import lombok.AllArgsConstructor;
import me.elb1to.watermc.hub.user.NewHubPlayer;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import me.elb1to.watermc.hub.utils.menu.Button;
import me.elb1to.watermc.hub.utils.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static me.elb1to.watermc.hub.utils.menu.Button.playNeutral;
import static me.elb1to.watermc.hub.utils.menu.Button.playSuccess;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 03/02/2020 @ 12:43
 */
public class SettingsMenu extends Menu {

	private final NewHubPlayer newHubPlayer;

	public SettingsMenu(NewHubPlayer newHubPlayer) {
		this.newHubPlayer = newHubPlayer;
	}

	@Override
	public String getTitle(Player player) {
		return ChatColor.DARK_GRAY + "Opciones de Jugador";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		buttons.put(11, new ParticleHiderButton());
		buttons.put(12, new PlayerHiderButton());
		buttons.put(13, new PlayerFlightButton());
		buttons.put(14, new OpenTagsMenu());
		buttons.put(15, new OpenCosmeticsButton());

		return buttons;
	}

	@Override
	public int size(Map<Integer, Button> buttons) {
		return 9 * 3;
	}

	private void playSound(boolean enabled, Player player) {
		if (enabled) {
			playSuccess(player);
		} else {
			playNeutral(player);
		}
	}

	@AllArgsConstructor
	private class ParticleHiderButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			return new ItemBuilder(Material.EMERALD)
					.setName("&bMostrar Particulas")
					.setLore(Arrays.asList(
							"&fActualmente " + (newHubPlayer.isHidingParticles() ? "&c&locultas" : "&a&lvisibles"),
							" ",
							"&7Si están ocultas no podras",
							"&7ver las particulas de nadie"
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			playSound(!newHubPlayer.isHidingParticles(), player);
			newHubPlayer.setHidingParticles(!newHubPlayer.isHidingParticles());
			player.sendMessage(CC.translate(newHubPlayer.isHidingParticles() ? "&cOcultando todas las particulas en el servidor..." : "&aMostrando todas las particulas en el servidor!"));
		}
	}

	@AllArgsConstructor
	private class PlayerHiderButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			return new ItemBuilder(Material.EYE_OF_ENDER)
					.setName("&bMostrar Jugadores")
					.setLore(Arrays.asList(
							"&fActualmente " + (newHubPlayer.isHidingPlayers() ? "&c&locultos" : "&a&lvisibles"),
							" ",
							"&7Si los jugadores están ocultos",
							"&7todos serán invisibles para ti."
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			playSound(!newHubPlayer.isHidingPlayers(), player);
			newHubPlayer.setHidingPlayers(!newHubPlayer.isHidingPlayers());
			player.sendMessage(CC.translate(newHubPlayer.isHidingPlayers() ? "&cOcultando todas los jugadores en el servidor..." : "&aMostrando todos los jugadores en el servidor!"));
		}
	}

	@AllArgsConstructor
	private class PlayerFlightButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			return new ItemBuilder(Material.FEATHER)
					.setName("&bActivar Vuelo")
					.setLore(Arrays.asList(
							"&fActualmente " + (newHubPlayer.isFlyMode() ? "&a&lactivado" : "&c&ldesactivado"),
							" ",
							"&7Si esta activado podras",
							"&7volar por todo el Hub."
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			if (!player.hasPermission("hub.perks.fly")) {
				player.sendMessage(CC.translate("&cNo tienes permisos para usar el modo de vuelo."));
				return;
			}

			playSound(!newHubPlayer.isFlyMode(), player);
			newHubPlayer.setFlyMode(!newHubPlayer.isFlyMode());
			player.sendMessage(CC.translate(newHubPlayer.isFlyMode() ? "&aHaz activado el modo de vuelo!" : "&cHaz desactivado el modo de vuelo."));
		}
	}

	@AllArgsConstructor
	private class OpenCosmeticsButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			return new ItemBuilder(Material.ENDER_CHEST)
					.setName("&bParticulas")
					.setLore(Arrays.asList(
							"&7Al clickear aquí podras",
							"&7escoger particulas para ti!"
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			player.sendMessage(CC.translate(CC.centerMessage("&a{AbrirMenuDeCosmeticos}")));
		}
	}

	@AllArgsConstructor
	private class OpenTagsMenu extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			return new ItemBuilder(Material.PAPER)
					.setName("&bPrefijos")
					.setLore(Arrays.asList(
							"&7Al clickear aquí podras",
							"&7escoger un prefijo para ti!"
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			player.performCommand("tags");
		}
	}
}

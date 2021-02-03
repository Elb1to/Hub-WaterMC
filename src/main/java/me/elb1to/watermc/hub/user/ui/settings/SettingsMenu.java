package me.elb1to.watermc.hub.user.ui.settings;

import lombok.AllArgsConstructor;
import me.elb1to.watermc.hub.user.HubPlayer;
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

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 03/02/2020 @ 12:43
 */
public class SettingsMenu extends Menu {

	private HubPlayer hubPlayer;

	public SettingsMenu(HubPlayer hubPlayer) {
		this.hubPlayer = hubPlayer;
	}

	@Override
	public String getTitle(Player player) {
		return ChatColor.DARK_GRAY + "Opciones de Jugador";
	}

	@Override
	public Map<Integer, Button> getButtons(Player player) {
		Map<Integer, Button> buttons = new HashMap<>();

		buttons.put(10, new ParticleHiderButton());
		buttons.put(11, new PlayerHiderButton());
		buttons.put(12, new PlayerFlightButton());
		buttons.put(13, new PlayerSpeedButton());

		buttons.put(16, new OpenCosmeticsButton());

		return buttons;
	}

	@AllArgsConstructor
	private class ParticleHiderButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			return new ItemBuilder(Material.FIREBALL)
					.setName("&bMostrar Particulas")
					.setLore(Arrays.asList(
							CC.MENU_BAR,
							"&fActualmente " + (hubPlayer.isHidingParticles() ? "&c&locultas" : "&a&lvisibles"),
							" ",
							"&7Si están ocultas no podras",
							"&7ver las particulas de nadie",
							CC.MENU_BAR
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			playSound(!hubPlayer.isHidingParticles(), player);
			hubPlayer.setHidingParticles(!hubPlayer.isHidingParticles());
			player.sendMessage(CC.translate(hubPlayer.isHidingParticles() ? "&cOcultando todas las particulas en el servidor..." : "&aMostrando todas las particulas en el servidor!"));
		}

		private void playSound(boolean enabled, Player player) {
			if (enabled) {
				playSuccess(player);
			} else {
				playNeutral(player);
			}
		}
	}

	@AllArgsConstructor
	private class PlayerHiderButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			return new ItemBuilder(Material.EYE_OF_ENDER)
					.setName("&bMostrar Jugadores")
					.setLore(Arrays.asList(
							CC.MENU_BAR,
							"&fActualmente " + (hubPlayer.isHidingPlayers() ? "&c&locultos" : "&a&lvisibles"),
							" ",
							"&7Si los jugadores están ocultos",
							"&7todos serán invisibles para ti.",
							CC.MENU_BAR
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			playSound(!hubPlayer.isHidingPlayers(), player);
			hubPlayer.setHidingPlayers(!hubPlayer.isHidingPlayers());
			player.sendMessage(CC.translate(hubPlayer.isHidingPlayers() ? "&cOcultando todas los jugadores en el servidor..." : "&aMostrando todos los jugadores en el servidor!"));
		}

		private void playSound(boolean enabled, Player player) {
			if (enabled) {
				playSuccess(player);
			} else {
				playNeutral(player);
			}
		}
	}

	@AllArgsConstructor
	private class PlayerFlightButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			return new ItemBuilder(Material.FEATHER)
					.setName("&bActivar Vuelo")
					.setLore(Arrays.asList(
							CC.MENU_BAR,
							"&fActualmente " + (hubPlayer.isFlyModeEnabled() ? "&c&ldesactivado" : "&a&lactivado"),
							" ",
							"&7Si esta activado podras",
							"&7volar por todo el Hub.",
							CC.MENU_BAR
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			playSound(!hubPlayer.isFlyModeEnabled(), player);
			hubPlayer.setFlyModeEnabled(!hubPlayer.isFlyModeEnabled());
			player.sendMessage(CC.translate(hubPlayer.isFlyModeEnabled() ? "&cHaz desactivado el modo de vuelo." : "&aHaz activado el modo de vuelo!"));
		}

		private void playSound(boolean enabled, Player player) {
			if (enabled) {
				playSuccess(player);
			} else {
				playNeutral(player);
			}
		}
	}

	@AllArgsConstructor
	private class PlayerSpeedButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			return new ItemBuilder(Material.SUGAR)
					.setName("&bActivar Velocidad")
					.setLore(Arrays.asList(
							CC.MENU_BAR,
							"&fActualmente " + (hubPlayer.isSpeedModeEnabled() ? "&c&ldesactivado" : "&a&lactivado"),
							" ",
							"&7Si esta activado podras",
							"&7correr mucho mas rapido",
							"&7por todo el Hub.",
							CC.MENU_BAR
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			//HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

			playSound(!hubPlayer.isSpeedModeEnabled(), player);
			hubPlayer.setSpeedModeEnabled(!hubPlayer.isSpeedModeEnabled());
			player.sendMessage(CC.translate(hubPlayer.isSpeedModeEnabled() ? "&cHaz desactivado tu aumento de velocidad." : "&aHaz activado la velocidad extra!"));
		}

		private void playSound(boolean enabled, Player player) {
			if (enabled) {
				playSuccess(player);
			} else {
				playNeutral(player);
			}
		}
	}

	@AllArgsConstructor
	private class OpenCosmeticsButton extends Button {

		@Override
		public ItemStack getButtonItem(Player player) {

			return new ItemBuilder(Material.ENDER_CHEST)
					.setName("&bCosmetics")
					.setLore(Arrays.asList(
							CC.MENU_BAR,
							"&7Al clickear aquí podras",
							"&7escoger particulas para ti!",
							" ",
							"&7Tipos de particulas:",
							"&8 > &aJugador",
							"&8 > &cDoble Salto",
							"&8 > &cAl Shiftear",
							CC.MENU_BAR
							)
					).get();
		}

		@Override
		public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
			player.sendMessage(CC.translate(CC.centerMessage("&a{AbrirMenuDeCosmeticos}")));
		}
	}

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * 3;
    }
}

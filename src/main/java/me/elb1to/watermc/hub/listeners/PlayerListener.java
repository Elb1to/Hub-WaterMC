package me.elb1to.watermc.hub.listeners;

import com.nametagedit.plugin.NametagEdit;
import com.nametagedit.plugin.api.events.NametagEvent;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.managers.QueueManager;
import me.elb1to.watermc.hub.providers.ServerRanks;
import me.elb1to.watermc.hub.user.HubPlayer;
import me.elb1to.watermc.hub.user.ui.selector.SelectorMenu;
import me.elb1to.watermc.hub.user.ui.settings.SettingsMenu;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.config.ConfigCursor;
import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import me.ryzeon.rtags.data.player.PlayerData;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

import java.util.Objects;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 11/09/2020 @ 16:03
 */
public class PlayerListener implements Listener {

	private final Hub plugin = Hub.getInstance();
	ConfigCursor messages = new ConfigCursor(Hub.getInstance().getMessagesConfig(), "PLAYER");
	ConfigCursor settings = new ConfigCursor(Hub.getInstance().getSettingsConfig(), "SERVER");
	private QueueManager manager = Hub.getInstance().getQueueManager();

	@EventHandler
	public void gearPlayerOnJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		/*Armor armor = plugin.getArmorManager().getArmorByRank(plugin.getCoreHandler().getRankName(player));
		if (armor != null) {
			armor.handlePlayer(player);
		}*/
	}

	@EventHandler
	public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
		if (!Hub.getInstance().getMongoDbManager().isAuthentication()) {
			return;
		}

		HubPlayer hubPlayer = HubPlayer.getByUuid(event.getUniqueId());
		if (hubPlayer == null) {
			hubPlayer = new HubPlayer(event.getUniqueId());
		}
		if (!hubPlayer.isDataLoaded()) {
			hubPlayer.loadData(hubPlayer);
		}
		if (!hubPlayer.isDataLoaded()) {
			event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
			event.setKickMessage(CC.translate("&cAn error has occurred while loading your profile. Please reconnect."));
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
		if (hubPlayer == null) {
			hubPlayer = new HubPlayer(player.getUniqueId());
		} else {
			hubPlayer.loadData(hubPlayer);
		}
		for (int i = 0; i < 80; i++) {
			player.sendMessage(" ");
		}
		for (String welcomeMsg : messages.getStringList("JOIN-MESSAGE")) {
			if (welcomeMsg.contains("{C}")) {
				welcomeMsg = welcomeMsg.replace("{C}", "");
				player.sendMessage(CC.translate(CC.centerMessage(welcomeMsg).replace("{0}", "\n")));
			} else {
				player.sendMessage(CC.translate(welcomeMsg.replace("{0}", "\n")));
			}
		}

		player.setHealth(20D);
		player.setFoodLevel(20);
		player.setWalkSpeed(0.5F);

		Location loc = player.getWorld().getSpawnLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		player.teleport(loc);
		player.playSound(loc, Sound.VILLAGER_YES, 1.0F, 1.0F);

		player.getInventory().clear();
		player.getInventory().setItem(1, new ItemBuilder(Material.ENDER_PEARL).setName(CC.translate("&b&lPerla Acuática. &8(&7Clic-Derecho&8)")).get());
		player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setName(CC.translate("&b&lServidores. &8(&7Clic-Derecho&8)")).get());
		player.getInventory().setItem(7, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName(CC.translate("&b&lOpciones. &8(&7Clic-Derecho&8)")).get());

		if (hubPlayer.isFlyMode()) {
			player.setAllowFlight(true);
			player.setFlying(true);
		}

		Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
			if (Bukkit.getPluginManager().getPlugin("NametagEdit") != null) {
				NametagEdit.getApi().setPrefix(player, getNametagColor(player));
			}
		}, 2L);

		event.setJoinMessage(null);
	}

	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent event) {
		Player player = event.getPlayer();
		saveData(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(null);

		if (manager.isQueueing(event.getPlayer())) {
			Queue queue = manager.getPlayerQueue(player);
			queue.remove(player);
		}

		HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
		if (hubPlayer.isFlyMode()) {
			player.setAllowFlight(false);
		}

		saveData(player);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		PlayerData playerTag = PlayerData.getData(player.getUniqueId());
		String playerRank = this.plugin.getVaultChat().getPlayerPrefix(player) + player.getName();

		if (playerTag.getActiveTag() != null) {
			event.setFormat(CC.translate(playerTag.getActiveTag().getPrefix() + " " + playerRank + "&7: &f" + event.getMessage()));
		} else {
			event.setFormat(CC.translate(playerRank + "&7: &f" + event.getMessage()));
		}
	}

	@EventHandler
	public void onItemInteraction(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null) {
				if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&b&lServidores. &8(&7Clic-Derecho&8)"))) {
					new SelectorMenu().openMenu(player);
				}
				if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&b&lOpciones. &8(&7Clic-Derecho&8)"))) {
					new SettingsMenu(hubPlayer).openMenu(player);
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!settings.getBoolean("ALLOW-BLOCK-PLACE")) {
			if (settings.getBoolean("ALLOW-OP-PLAYERS-BYPASS")) {
				if (event.getPlayer().isOp()) {
					return;
				}
			}

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!settings.getBoolean("ALLOW-BLOCK-BREAK")) {
			if (settings.getBoolean("ALLOW-OP-PLAYERS-BYPASS")) {
				if (event.getPlayer().isOp()) {
					return;
				}
			}

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (!settings.getBoolean("ALLOW-ITEM-DROP")) {
			if (settings.getBoolean("ALLOW-OP-PLAYERS-BYPASS")) {
				if (event.getPlayer().isOp()) {
					return;
				}
			}

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPickupItem(PlayerPickupItemEvent event) {
		if (!settings.getBoolean("ALLOW-ITEM-PICKUP")) {
			if (settings.getBoolean("ALLOW-OP-PLAYERS-BYPASS")) {
				if (event.getPlayer().isOp()) {
					return;
				}
			}

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if (!settings.getBoolean("ALLOW-HUNGER")) {
			if (settings.getBoolean("ALLOW-OP-PLAYERS-BYPASS")) {
				if (event.getEntity() instanceof Player && event.getEntity().isOp()) {
					event.setCancelled(false);
				}
			}

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (!settings.getBoolean("ALLOW-DAMAGE")) {
				if (settings.getBoolean("ALLOW-OP-PLAYERS-BYPASS")) {
					if (event.getEntity() instanceof Player && event.getEntity().isOp()) {
						event.setCancelled(false);
					}
				}

				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInteractionDripParticleTest(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		/*CandyParticle candyParticle = new CandyParticle();
		FusionParticle fusionParticle = new FusionParticle();
		YingYangParticle yingYangParticle = new YingYangParticle();
		FlameRingsParticle flameRingsParticle = new FlameRingsParticle();
		FrozedFieldParticle frozedFieldParticle = new FrozedFieldParticle();
		EmeraldCrownParticle emeraldCrownParticle = new EmeraldCrownParticle();
		RadialMeteorParticle radialMeteorParticle = new RadialMeteorParticle();
		EmeraldSpiralParticle emeraldSpiralParticle = new EmeraldSpiralParticle();
		RainbowTwirlsParticle rainbowTwirlsParticle = new RainbowTwirlsParticle();

		new BukkitRunnable() {
			public void run() {
				if (player.getItemInHand().getType().equals(Material.DIAMOND_SWORD)) {
					emeraldSpiralParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.IRON_SWORD)) {
					flameRingsParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.GOLD_SWORD)) {
					emeraldCrownParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.WOOD_SWORD)) {
					fusionParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.STONE_SWORD)) {
					yingYangParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.IRON_AXE)) {
					rainbowTwirlsParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.GOLD_AXE)) {
					candyParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.DIAMOND_AXE)) {
					radialMeteorParticle.playParticle(player);
				} else if (player.getItemInHand().getType().equals(Material.STONE_AXE)) {
					frozedFieldParticle.playParticleTest(player);
				}
			}
		}.runTaskTimerAsynchronously(Hub.getInstance(), 1, 1);*/
	}

	/*@EventHandler
	public void blank(PlayerToggleSneakEvent event) {
		final Player player = event.getPlayer();
		new BukkitRunnable() {
			double t = Math.PI / 4;
			Location loc = player.getLocation();

			public void run() {
				t = t + 0.1 * Math.PI;
				for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
					double x = t * Math.cos(theta);
					double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
					double z = t * Math.sin(theta);
					loc.add(x, y, z);
					ParticleUtils.PlayParticle(ParticleUtils.ParticleType.RED_DUST, loc, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
					loc.subtract(x, y, z);

					theta = theta + Math.PI / 64;

					x = t * Math.cos(theta);
					y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
					z = t * Math.sin(theta);
					loc.add(x, y, z);
					ParticleUtils.PlayParticle(ParticleUtils.ParticleType.FIREWORKS_SPARK, loc, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
					loc.subtract(x, y, z);
				}
				if (t > 20) {
					this.cancel();
				}
			}
		}.runTaskTimer(this.plugin, 0, 1L);
	}*/

	/*@EventHandler
	public void blank(PlayerToggleSneakEvent event) {
		final Player player = event.getPlayer();
		final DustSpellColor AQUA = new DustSpellColor(Color.AQUA);

		new BukkitRunnable() {
			Location loc = player.getLocation();

			public void run() {
				double phi = 0;
				phi += Math.PI / 10;

				for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 40) {
					double r = 1.5;
					double x = r * Math.cos(theta) * Math.sin(phi);
					double y = r * Math.cos(phi) + 1.5;
					double z = r * Math.sin(theta) * Math.sin(phi);

					loc.add(x, y, z);
					new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, AQUA, loc).display();
					loc.subtract(x, y, z);
				}
				if (phi > 8 * Math.PI) {
					this.cancel();
				}
			}
		}.runTaskTimer(this.plugin, 0, 1L);
	}*/

	private void saveData(Player player) {
		HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
		if (hubPlayer == null) {
			return;
		}

		hubPlayer.saveData(hubPlayer);
	}

	private String getNametagColor(Player player) {
		String color;
		String group = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();

		if (group.equalsIgnoreCase(ServerRanks.OWNER.getName())) {
			color = ServerRanks.OWNER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.CO_OWNER.getName())) {
			color = ServerRanks.CO_OWNER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.DEVELOPER.getName())) {
			color = ServerRanks.DEVELOPER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.PLAT_ADMIN.getName())) {
			color = ServerRanks.PLAT_ADMIN.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.SR_ADMIN.getName())) {
			color = ServerRanks.SR_ADMIN.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.ADMIN.getName())) {
			color = ServerRanks.ADMIN.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.JR_ADMIN.getName())) {
			color = ServerRanks.JR_ADMIN.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.SR_MOD.getName())) {
			color = ServerRanks.SR_MOD.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.MOD_PLUS.getName())) {
			color = ServerRanks.MOD_PLUS.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.MOD.getName())) {
			color = ServerRanks.MOD.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.TRIAL_MOD.getName())) {
			color = ServerRanks.TRIAL_MOD.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.HELPER.getName())) {
			color = ServerRanks.HELPER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.PARTNER.getName())) {
			color = ServerRanks.PARTNER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.FAMOUS.getName())) {
			color = ServerRanks.FAMOUS.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.STREAMER.getName())) {
			color = ServerRanks.STREAMER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.YOUTUBER.getName())) {
			color = ServerRanks.YOUTUBER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.MINI_YT.getName())) {
			color = ServerRanks.MINI_YT.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.WATER.getName())) {
			color = ServerRanks.WATER.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.KRAKEN_PLUS.getName())) {
			color = ServerRanks.KRAKEN_PLUS.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.KRAKEN.getName())) {
			color = ServerRanks.KRAKEN.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.POSEIDON.getName())) {
			color = ServerRanks.POSEIDON.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.APOLO.getName())) {
			color = ServerRanks.APOLO.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.TRITON.getName())) {
			color = ServerRanks.TRITON.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.VERIFIED.getName())) {
			color = ServerRanks.VERIFIED.getNametag();
		} else if (group.equalsIgnoreCase(ServerRanks.USER.getName())) {
			color = ServerRanks.USER.getNametag();
		} else {
			color = ServerRanks.DEFAULT.getNametag();
		}

		return color;
	}
}

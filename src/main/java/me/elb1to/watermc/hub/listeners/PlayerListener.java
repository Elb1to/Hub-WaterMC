package me.elb1to.watermc.hub.listeners;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.managers.QueueManager;
import me.elb1to.watermc.hub.user.HubPlayer;
import me.elb1to.watermc.hub.user.ui.settings.SettingsMenu;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.config.ConfigCursor;
import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 11/09/2020 @ 16:03
 */
public class PlayerListener implements Listener {

	private final Hub plugin = Hub.getInstance();
	private QueueManager manager = Hub.getInstance().getQueueManager();

	ConfigCursor messages = new ConfigCursor(Hub.getInstance().getMessagesConfig(), "PLAYER");
	ConfigCursor settings = new ConfigCursor(Hub.getInstance().getSettingsConfig(), "SERVER");

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
			hubPlayer = new HubPlayer(event.getUniqueId(), event.getName());
		}
		if (!hubPlayer.isDataLoaded()) {
			hubPlayer.loadData();
		}
		if (!hubPlayer.isDataLoaded()) {
			event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
			event.setKickMessage(CC.translate("&cAn error has occurred while loading your profile. Please reconnect."));
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
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

		Location loc = player.getWorld().getSpawnLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		player.teleport(loc);

		player.getInventory().clear();
		player.getInventory().setItem(1, new ItemBuilder(Material.ENDER_PEARL).setName(CC.translate("&b&lPerla Acuatica &8(&7Click-Derecho&8)")).get());
		player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setName(CC.translate("&b&lServidores &8(&7Click-Derecho&8)")).get());
		player.getInventory().setItem(7, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName(CC.translate("&b&lOpciones &8(&7Click-Derecho&8)")).get());

		event.setJoinMessage(null);
	}

	@EventHandler
	public void onPlayerKickEvent(PlayerKickEvent event) {
		saveData(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		saveData(event.getPlayer());
		event.setQuitMessage(null);

		if (manager.isQueueing(event.getPlayer())) {
			Queue queue = manager.getPlayerQueue(event.getPlayer());
			queue.remove(event.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		event.setFormat(CC.translate(this.plugin.getVaultChat().getPlayerPrefix(player) + player.getName() + "&7: &f" + event.getMessage()));
	}

	@EventHandler
	public void onItemInteraction(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null) {
				if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&b&lServidores &8(&7Click-Derecho&8)"))) {
					//new SettingsMenu().openMenu(event.getPlayer());
					event.getPlayer().sendMessage(CC.translate("Abrir selector de modos -> CraftPlayer{}"));
				}
				if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&b&lOpciones &8(&7Click-Derecho&8)"))) {
					new SettingsMenu().openMenu(event.getPlayer());
				}
				if (event.getItem().getType().equals(Material.REDSTONE_COMPARATOR)) {
					new SettingsMenu().openMenu(event.getPlayer());
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
	public void onTESTCMD(AsyncPlayerChatEvent event) {
		if (event.getMessage().equalsIgnoreCase("-test")) {
			//new CosmeticsMenu().openMenu(event.getPlayer());
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

	@EventHandler
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
	}

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

	private void saveData(Player player){
		HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
		if (hubPlayer == null) {
			return;
		}

		hubPlayer.saveData();
	}
}

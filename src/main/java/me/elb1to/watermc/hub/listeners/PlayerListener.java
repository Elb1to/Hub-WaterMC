package me.elb1to.watermc.hub.listeners;

import com.nametagedit.plugin.NametagEdit;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.managers.QueueManager;
import me.elb1to.watermc.hub.providers.ServerRanks;
import me.elb1to.watermc.hub.user.NewHubPlayer;
import me.elb1to.watermc.hub.user.PlayerState;
import me.elb1to.watermc.hub.user.particles.*;
import me.elb1to.watermc.hub.user.ui.selector.SelectorMenu;
import me.elb1to.watermc.hub.user.ui.settings.SettingsMenu;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.config.ConfigCursor;
import me.elb1to.watermc.hub.utils.extra.ItemBuilder;
import me.elb1to.watermc.hub.utils.menu.Button;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 04/02/2020 @ 16:03
 */
public class PlayerListener implements Listener {

	private final Hub plugin = Hub.getInstance();
	ConfigCursor messages = new ConfigCursor(Hub.getInstance().getMessagesConfig(), "PLAYER");
	ConfigCursor settings = new ConfigCursor(Hub.getInstance().getSettingsConfig(), "SERVER");
	private BukkitTask runnable;
	private QueueManager manager = Hub.getInstance().getQueueManager();

	@EventHandler
	public static void removePlayerFromQueue(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		NewHubPlayer newHubPlayer = Hub.getInstance().getHubPlayerManager().getPlayerData(player.getUniqueId());

		if (Hub.getInstance().getQueueManager().isQueueing(player)) {
			Queue queue = Hub.getInstance().getQueueManager().getByString(newHubPlayer.getClickedHCFServer());
			if (event.getResult().equals(PlayerLoginEvent.Result.KICK_OTHER)) {
				queue.remove(player);
				Button.playFail(player);
				player.sendMessage(CC.translate("&cHas sido removido de la cola porque tu deathban aun no expira."));
			}
		}
	}

	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent event) {
		Player player = Bukkit.getServer().getPlayerExact(event.getName());
		if (player == null) {
			return;
		}

		if (player.isOnline()) {
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cA player with the same name is already connected!\n§cPlease try again later.");
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		NewHubPlayer newHubPlayer = this.plugin.getHubPlayerManager().getPlayerData(player.getUniqueId());
		this.plugin.getHubPlayerManager().createPlayerData(player);

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

		if (newHubPlayer != null) {
			newHubPlayer.setPlayerState(PlayerState.LOBBY);
			if (newHubPlayer.isFlyMode()) {
				player.setAllowFlight(true);
				player.setFlying(true);
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
		NewHubPlayer newHubPlayer = this.plugin.getHubPlayerManager().getPlayerData(player.getUniqueId());
		newHubPlayer.setPlayerState(PlayerState.LOBBY);
		runnable.cancel();
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		NewHubPlayer newHubPlayer = this.plugin.getHubPlayerManager().getPlayerData(player.getUniqueId());
		event.setQuitMessage(null);

		if (manager.isQueueing(player)) {
			Queue queue = manager.getPlayerQueue(player);
			queue.remove(player);
		}

		runnable.cancel();
		newHubPlayer.setPlayerState(PlayerState.LOBBY);
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
		NewHubPlayer newHubPlayer = this.plugin.getHubPlayerManager().getPlayerData(player.getUniqueId());

		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null) {
				if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&b&lServidores. &8(&7Clic-Derecho&8)"))) {
					new SelectorMenu().openMenu(player);
				}
				if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&b&lOpciones. &8(&7Clic-Derecho&8)"))) {
					new SettingsMenu(newHubPlayer).openMenu(player);
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
	public void onMoveItem(InventoryClickEvent event) {
		if (event.getClickedInventory() == null || event.getClickedInventory().getName() == null) {
			return;
		}
		if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onVoidFall(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location spawnLocation = player.getPlayer().getWorld().getSpawnLocation();
		Location location = player.getPlayer().getLocation();

		if (location.getY() <= 0) {
			player.teleport(spawnLocation);
		}
	}

	@EventHandler
	public void playParticleTest(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		TritonParticle tritonParticle = new TritonParticle();
		ApoloParticle apoloParticle = new ApoloParticle();
		PoseidonParticle poseidonParticle = new PoseidonParticle();
		KrakenParticle krakenParticle = new KrakenParticle();
		DeveloperParticle developerParticle = new DeveloperParticle();
		KrakenPlusParticle krakenPlusParticle = new KrakenPlusParticle();
		WaterParticle waterParticle = new WaterParticle();

		runnable = new BukkitRunnable() {
			public void run() {
				switch (player.getItemInHand().getType()) {
					case EMERALD:
						tritonParticle.playParticle(player);
						break;
					case GOLD_INGOT:
						apoloParticle.playParticle(player);
						break;
					case DIAMOND:
						poseidonParticle.playParticle(player);
						break;
					case WATER_BUCKET:
						krakenParticle.playParticle(player);
						break;
					case IRON_INGOT:
						krakenPlusParticle.playParticle(player);
						break;
					case SAPLING:
						waterParticle.playParticle(player);
						break;
					case NETHER_STAR:
						developerParticle.playParticle(player);
						break;
				}
			}
		}.runTaskTimerAsynchronously(Hub.getInstance(), 1, 1);
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

	//private void saveData(Player player) {
	//	HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
	//	if (hubPlayer == null) {
	//		return;
	//	}

	//	hubPlayer.saveData(hubPlayer);
	//}

	private String getNametagColor(Player player) {
		String color;
		String group = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();

		switch (group.toLowerCase()) {
			case "owner":
				color = ServerRanks.OWNER.getNametag();
				break;
			case "co-owner":
				color = ServerRanks.CO_OWNER.getNametag();
				break;
			case "developer":
				color = ServerRanks.DEVELOPER.getNametag();
				break;
			case "platadmin":
				color = ServerRanks.PLAT_ADMIN.getNametag();
				break;
			case "sradmin":
				color = ServerRanks.SR_ADMIN.getNametag();
				break;
			case "admin":
				color = ServerRanks.ADMIN.getNametag();
				break;
			case "jradmin":
				color = ServerRanks.JR_ADMIN.getNametag();
				break;
			case "srmod":
				color = ServerRanks.SR_MOD.getNametag();
				break;
			case "mod+":
				color = ServerRanks.MOD_PLUS.getNametag();
				break;
			case "mod":
				color = ServerRanks.MOD.getNametag();
				break;
			case "trialmod":
				color = ServerRanks.TRIAL_MOD.getNametag();
				break;
			case "helper":
				color = ServerRanks.HELPER.getNametag();
				break;
			case "partner":
				color = ServerRanks.PARTNER.getNametag();
				break;
			case "famous":
				color = ServerRanks.FAMOUS.getNametag();
				break;
			case "streamer":
				color = ServerRanks.STREAMER.getNametag();
				break;
			case "youtube":
				color = ServerRanks.YOUTUBER.getNametag();
				break;
			case "miniyt":
				color = ServerRanks.MINI_YT.getNametag();
				break;
			case "water":
				color = ServerRanks.WATER.getNametag();
				break;
			case "kraken+":
				color = ServerRanks.KRAKEN_PLUS.getNametag();
				break;
			case "kraken":
				color = ServerRanks.KRAKEN.getNametag();
				break;
			case "poseidon":
				color = ServerRanks.POSEIDON.getNametag();
				break;
			case "apolo":
				color = ServerRanks.APOLO.getNametag();
				break;
			case "triton":
				color = ServerRanks.TRITON.getNametag();
				break;
			case "verified":
				color = ServerRanks.VERIFIED.getNametag();
				break;
			case "user":
				color = ServerRanks.USER.getNametag();
				break;
			case "default":
				color = ServerRanks.DEFAULT.getNametag();
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + group.toLowerCase());
		}

		return color;
	}
}

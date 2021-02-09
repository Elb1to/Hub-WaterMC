package me.elb1to.watermc.hub;

import club.frozed.tablist.FrozedTablist;
import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.elb1to.watermc.hub.commands.admin.DeleteDataCommand;
import me.elb1to.watermc.hub.commands.admin.PlayerDebugCommand;
import me.elb1to.watermc.hub.commands.general.HelpCommand;
import me.elb1to.watermc.hub.commands.general.SpawnCommand;
import me.elb1to.watermc.hub.commands.general.SudoAllCommand;
import me.elb1to.watermc.hub.commands.queue.*;
import me.elb1to.watermc.hub.listeners.*;
import me.elb1to.watermc.hub.managers.HubPlayerManager;
import me.elb1to.watermc.hub.managers.MongoDbManager;
import me.elb1to.watermc.hub.managers.QueueManager;
import me.elb1to.watermc.hub.providers.ScoreboardProvider;
import me.elb1to.watermc.hub.providers.TablistProvider;
import me.elb1to.watermc.hub.tasks.PlayerDataWorkerRunnable;
import me.elb1to.watermc.hub.user.NewHubPlayer;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.CommandFramework;
import me.elb1to.watermc.hub.utils.config.FileConfig;
import me.elb1to.watermc.hub.utils.extra.PlayerUtils;
import me.elb1to.watermc.hub.utils.menu.ButtonListener;
import me.elb1to.watermc.hub.utils.menu.MenuUpdateTask;
import me.elb1to.watermc.hub.utils.scoreboard.BoardManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

@Getter
public final class Hub extends JavaPlugin implements PluginMessageListener {

	@Getter private static Hub instance;

	private Map<String, Integer> serverPlayers = Maps.newHashMap();
	private Set<String> bungeeServers = new HashSet<>();
	private BukkitTask countPlayerTask;

	private Permission vaultPerm = null;
	private Chat vaultChat = null;

	private CommandFramework commandFramework;
	private HubPlayerManager hubPlayerManager;
	private QueueManager queueManager;

	private MongoDbManager mongoDbManager;
	private FileConfig settingsConfig, databaseConfig, queuesConfig, messagesConfig, hotbarConfig, armorConfig;

	private PlayerDataWorkerRunnable playerDataWorkerRunnable;

	@Override
	public void onEnable() {
		instance = this;

		if (!this.getDescription().getAuthors().contains("Elb1to") || !this.getDescription().getName().equals("Hub")
			|| !this.getDescription().getWebsite().equals("www.frozed.club") || !this.getDescription().getDescription().equals("WaterMC's Hubcore made by Elb1to")) {
			System.exit(0);
			Bukkit.shutdown();
		}

		this.commandFramework = new CommandFramework(this);

		this.armorConfig = new FileConfig(this, "armor.yml");
		this.hotbarConfig = new FileConfig(this, "hotbar.yml");
		this.queuesConfig = new FileConfig(this, "queues.yml");
		this.databaseConfig = new FileConfig(this, "database.yml");
		this.settingsConfig = new FileConfig(this, "settings.yml");
		this.messagesConfig = new FileConfig(this, "messages.yml");

		this.setupChat();
		this.setupPermissions();

		this.mongoDbManager = new MongoDbManager();
		this.mongoDbManager.connect();

		this.hubPlayerManager = new HubPlayerManager();
		this.queueManager = new QueueManager();

		Bukkit.getConsoleSender().sendMessage(CC.CHAT_BAR);
		Bukkit.getConsoleSender().sendMessage(CC.translate("&bHub [WaterMC] &8- &fv" + getDescription().getVersion()));
		Bukkit.getConsoleSender().sendMessage(CC.translate("&7Made on &bFrozed Club Development &7by &bElb1to"));
		Bukkit.getConsoleSender().sendMessage(CC.CHAT_BAR);

		playerDataWorkerRunnable = new PlayerDataWorkerRunnable();
		Thread thread = new Thread(playerDataWorkerRunnable);
		thread.setName("Hub PlayerData Worker");
		thread.start();

		Bukkit.getServer().getPluginManager().registerEvents(new PlayerUtils(), this);
		registerStuff();
		startPlayerCountTask();
	}

	@Override
	public void onDisable() {
		for (NewHubPlayer newHubPlayer : this.getHubPlayerManager().getAllData()) {
			this.hubPlayerManager.saveData(newHubPlayer);
		}

		if (countPlayerTask != null) {
			countPlayerTask.cancel();
		}

		this.mongoDbManager.disconnect();
	}

	private void registerStuff() {
		registerListeners();
		registerCommands();

		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new MenuUpdateTask(), 5L, 5L);

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

		new BoardManager(new ScoreboardProvider(), 20);

		if (settingsConfig.getConfiguration().getBoolean("SETTINGS.SERVER.TABLIST")) {
			new FrozedTablist(this, new TablistProvider(), 0, 20);
		}
	}

	private void registerListeners() {
		Arrays.asList(
				new ServerListener(),
				new PlayerListener(),
				new ButtonListener(),
				new EnderbuttListener(),
				new DoubleJumpListener()
		).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
	}

	private void registerCommands() {
		new QueueDebugCommand();
		new PlayerDebugCommand();

		new SpawnCommand();
		new HelpCommand();
		new SudoAllCommand();

		new DeleteDataCommand();

		new QueueCommand();
		new QueueInfoCommand();
		new JoinQueueCommand();
		new LeaveQueueCommand();
		new PauseQueueCommand();
		new LimitQueueCommand();
		new QueueCheckCommand();
	}

	@Override
	public void onPluginMessageReceived(String string, Player player, byte[] bytes) {
		if (!string.equals("BungeeCord")) {
			return;
		}

		ByteArrayDataInput byteArrayDataInput = ByteStreams.newDataInput(bytes);
		String readUTF = byteArrayDataInput.readUTF();
		if (readUTF.equalsIgnoreCase("GetServers")) {
			Collections.addAll(bungeeServers, byteArrayDataInput.readUTF().split(", "));
			return;
		}
		if (!readUTF.equalsIgnoreCase("PlayerCount")) {
			return;
		}

		String server = byteArrayDataInput.readUTF();
		if (this.serverPlayers.containsKey(server)) {
			this.serverPlayers.remove(server);
		}

		this.serverPlayers.put(server, byteArrayDataInput.readInt());
	}

	private void startPlayerCountTask() {
		countPlayerTask = new BukkitRunnable() {
			public void run() {
				if (Bukkit.getOnlinePlayers() == null || Bukkit.getOnlinePlayers().isEmpty()) {
					return;
				}
				if (bungeeServers.isEmpty()) {
					ByteArrayDataOutput send = ByteStreams.newDataOutput();
					send.writeUTF("GetServers");
					Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(Hub.getPlugin(Hub.class), "BungeeCord", send.toByteArray());
					return;
				}
				for (String s : bungeeServers) {
					ByteArrayDataOutput send = ByteStreams.newDataOutput();
					send.writeUTF("PlayerCount");
					send.writeUTF(s);
					Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(Hub.getPlugin(Hub.class), "BungeeCord", send.toByteArray());
				}
			}
		}.runTaskTimer(this, 0L, 20L);
	}

	public int getNetworkTotalPlayer(String server, boolean global) {
		if (this.serverPlayers.isEmpty()) {
			return 0;
		}

		int i = 0;
		if (global) {
			for (Map.Entry<String, Integer> e : serverPlayers.entrySet()) {
				i += e.getValue();
			}
		} else {
			for (Map.Entry<String, Integer> entry : serverPlayers.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(server)) {
					i += entry.getValue();
				}
			}

		}

		return i;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		vaultPerm = rsp.getProvider();

		return vaultPerm != null;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		vaultChat = rsp.getProvider();

		return vaultChat != null;
	}
}

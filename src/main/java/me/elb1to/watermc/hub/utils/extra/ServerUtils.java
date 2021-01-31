package me.elb1to.watermc.hub.utils.extra;

import com.google.common.collect.Lists;
import me.elb1to.watermc.hub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 1/16/2021 @ 1:21 PM
 */
public class ServerUtils {

	private static final Hub hub = Hub.getInstance();

	public static Player[] getPlayers() {
		return getServer().getOnlinePlayers().toArray(new Player[0]);
	}

	public static Collection<? extends Player> getPlayersCollection() {
		return getServer().getOnlinePlayers();
	}

	public static List<Player> getSortedPlayers() {
		return getSortedPlayers(Comparator.comparing(HumanEntity::getName));
	}

	public static List<Player> getSortedPlayers(Comparator<Player> comparator) {
		ArrayList<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
		Collections.sort(players, comparator);
		
		return players;
	}

	public static Server getServer() {
		return Bukkit.getServer();
	}

	public static void repeatRunnable(BukkitRunnable runnable, long time) {
		runnable.runTaskTimer(hub, time, time);
	}

	public static Collection<Player> getServerPlayers() {
		return Lists.newArrayList(getPlayers());
	}

	public static BukkitTask runAsync(Runnable runnable) {
		return hub.getServer().getScheduler().runTaskAsynchronously(hub, runnable);
	}

	public static BukkitTask runAsync(Runnable runnable, long time) {
		return hub.getServer().getScheduler().runTaskLaterAsynchronously(hub, runnable, time);
	}

	public static BukkitTask runAsyncTimer(Runnable runnable, long time, long period) {
		return hub.getServer().getScheduler().runTaskTimerAsynchronously(hub, runnable, time, period);
	}

	public static BukkitTask runSync(Runnable runnable) {
		return hub.getServer().getScheduler().runTask(hub, runnable);
	}

	public static BukkitTask runSyncLater(Runnable runnable, long delay) {
		return hub.getServer().getScheduler().runTaskLater(hub, runnable, delay);
	}

	public static BukkitTask runSyncTimer(Runnable runnable, long delay, long period) {
		return hub.getServer().getScheduler().runTaskTimer(hub, runnable, delay, period);
	}

	public static BukkitTask runSyncTimer(BukkitRunnable runnable, long delay, long period) {
		return runnable.runTaskTimer(hub, delay, period);
	}
}

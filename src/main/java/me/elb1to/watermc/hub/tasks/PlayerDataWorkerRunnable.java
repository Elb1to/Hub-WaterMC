package me.elb1to.watermc.hub.tasks;

import me.elb1to.watermc.hub.user.HubPlayer;
import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 1/31/2021 @ 2:36 PM
 */
@SuppressWarnings("InfiniteLoopStatement")
public class PlayerDataWorkerRunnable implements Runnable {

	@Override
	public void run() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(() -> {
			while (true) {
				Bukkit.getOnlinePlayers().forEach(player -> HubPlayer.getAllData().forEach(playerData -> {
					if (!playerData.isDataLoaded()) {
						HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
						hubPlayer.loadData();
						playerData.setDataLoaded(true);
					}
				}));
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
	}
}

package me.elb1to.watermc.hub.tasks;

import me.elb1to.watermc.hub.Hub;
import org.bukkit.Bukkit;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 1/31/2021 @ 2:36 PM
 */
public class PlayerDataWorkerRunnable implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Bukkit.getOnlinePlayers().forEach(player -> Hub.getInstance().getHubPlayerManager().getAllData().forEach(hubPlayer -> {
				if (!hubPlayer.isDataLoaded()) {
					Hub.getInstance().getHubPlayerManager().loadData(hubPlayer);
					hubPlayer.setDataLoaded(true);
				}
			}));
		}
	}
}

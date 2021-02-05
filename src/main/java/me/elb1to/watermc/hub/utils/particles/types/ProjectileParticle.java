package me.elb1to.watermc.hub.utils.particles.types;

import me.elb1to.watermc.hub.Hub;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/4/2021 @ 10:10 AM
 */
public class ProjectileParticle {

	public static void play(Entity item, NormalParticle effect) {
		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {
				if (item == null || item.isOnGround() || item.isDead()) {
					cancel();
					return;
				}

				effect.setLocation(item.getLocation());
				effect.display(1);
			}
		};

		runnable.runTaskTimer(Hub.getInstance(), 0L, 1L);
	}
}

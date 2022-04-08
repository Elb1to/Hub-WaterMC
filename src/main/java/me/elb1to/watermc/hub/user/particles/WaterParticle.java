package me.elb1to.watermc.hub.user.particles;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.utils.extra.AlgebraUtils;
import me.elb1to.watermc.hub.utils.extra.PlayerUtils;
import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import me.elb1to.watermc.hub.utils.particles.data.DustSpellColor;
import me.elb1to.watermc.hub.utils.particles.types.ColoredParticle;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/5/2021 @ 9:00 PM
 */
public class WaterParticle {

	private static final double DELTA_THETA = Math.PI / 20;
	private static final double DELTA_Y = 0.05;
	private static final double RADIUS = 1.4;
	private static final double RADIUS_MOVING = 0.4;
	private static final double MAX_HEIGHT = 2.5;
	private static final DustSpellColor BLUE = new DustSpellColor(Color.AQUA);
	private static final DustSpellColor BLACK = new DustSpellColor(Color.BLACK);

	private double theta, y, radius;
	private boolean isUp;

	public void playParticle(Player player) {
		Location locationA, locationB;

		if (PlayerUtils.isMoving(player)) {
			locationA = player.getLocation().add(0, 1, 0);
			locationB = locationA.clone();
			Location fixed = locationA.clone();
			fixed.setPitch(0);
			double x = RADIUS_MOVING * Math.cos(theta), y = RADIUS_MOVING * Math.sin(theta), r = Math.toRadians(fixed.getYaw());

			locationA.add(x, y, 0);
			locationB.subtract(x, y, 0);

			Vector vectorA = locationA.toVector().subtract(fixed.toVector()), vectorB = locationB.toVector().subtract(fixed.toVector());

			AlgebraUtils.rotateAroundYAxis(vectorA, r);
			AlgebraUtils.rotateAroundYAxis(vectorB, r);

			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.FIREWORKS_SPARK, fixed.clone().add(vectorA), 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.MAGIC_CRIT, fixed.clone().add(vectorB), 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		} else {
			locationA = player.getLocation().add(0, 0.1, 0);
			locationB = locationA.clone();
			double x = radius * Math.cos(theta), z = radius * Math.sin(theta);

			locationA.add(x, y, z);
			locationB.add(-x, y, -z);

			if (isUp) {
				y += DELTA_Y;
				if (y > MAX_HEIGHT) {
					isUp = false;
				}
			} else {
				y -= DELTA_Y;
				if (y < 0) {
					isUp = true;
				}
			}

			theta += DELTA_THETA;
			radius = (MAX_HEIGHT - y) / MAX_HEIGHT * RADIUS;

			if (isUp) {
				ParticleUtils.PlayParticle(ParticleUtils.ParticleType.DRIP_WATER, locationA, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
				ParticleUtils.PlayParticle(ParticleUtils.ParticleType.DRIP_WATER, locationB, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
			} else {
				ParticleUtils.PlayParticle(ParticleUtils.ParticleType.WITCH_MAGIC, locationA, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
				ParticleUtils.PlayParticle(ParticleUtils.ParticleType.MAGIC_CRIT, locationB, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
				Bukkit.getServer().getScheduler().runTaskLater(Hub.getInstance(),() -> {
					ParticleUtils.PlayParticle(ParticleUtils.ParticleType.BUBBLE, locationA, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
					ParticleUtils.PlayParticle(ParticleUtils.ParticleType.BUBBLE, locationB, 0, 0, 0, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
				}, 15L);
			}
		}
	}
}

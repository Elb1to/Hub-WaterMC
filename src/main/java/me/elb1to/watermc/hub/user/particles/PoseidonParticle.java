package me.elb1to.watermc.hub.user.particles;

import me.elb1to.watermc.hub.utils.extra.PlayerUtils;
import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/5/2021 @ 8:49 PM
 */
public class PoseidonParticle {
	public void playParticle(Player player) {
		int tick = player.getTicksLived();

		Location location = player.getLocation().add(0, 1, 0), locForward, locMidA, locMidB, locReverse;
		double angle = tick * ((2 * Math.PI) / 32);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		locForward = location.clone().add(1.0 * cos, 0.5 + (1.0 * cos), 1.0 * sin);
		locMidA = location.clone().add(1.0 * cos, 0.5, 1.0 * sin);
		locMidB = location.clone().add(1.0 * -cos, 0.5, 1.0 * -sin);
		locReverse = location.clone().add(1.0 * -cos, 0.5 + (1.0 * cos), 1.0 * -sin);

		if (PlayerUtils.isMoving(player)) {
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.WATER_WAKE, player.getLocation().add(0, 0.2, 0), 0.3f, 0f, 0.3f, 0, 3, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		} else {
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.DRIP_WATER, locForward, 0f, 0f, 0f, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.DRIP_WATER, locMidA, 0f, 0f, 0f, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.DRIP_WATER, locMidB, 0f, 0f, 0f, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.DRIP_WATER, locReverse, 0f, 0f, 0f, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		}
	}
}

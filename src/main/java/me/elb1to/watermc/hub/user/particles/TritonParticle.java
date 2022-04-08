package me.elb1to.watermc.hub.user.particles;

import me.elb1to.watermc.hub.utils.extra.PlayerUtils;
import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/5/2021 @ 8:49 PM
 */
public class TritonParticle {
	public void playParticle(Player player) {
		int tick = player.getTicksLived();

		float x = (float) (Math.sin(tick / 7d) * 1f);
		float z = (float) (Math.cos(tick / 7d) * 1f);
		float y = (float) (Math.cos(tick / 17d) * 1f + 1f);

		if (PlayerUtils.isMoving(player)) {
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.HAPPY_VILLAGER, player.getLocation().add(0, 0.2, 0), 0.3f, 0f, 0.3f, 0, 2, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		} else {
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.HAPPY_VILLAGER, player.getLocation().add(x, y, z), 0f, 0f, 0f, 0, 1, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		}
	}
}

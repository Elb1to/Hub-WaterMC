package me.elb1to.watermc.hub.user.particles;

import me.elb1to.watermc.hub.utils.extra.PlayerUtils;
import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import me.elb1to.watermc.hub.utils.particles.data.DustSpellColor;
import me.elb1to.watermc.hub.utils.particles.types.ColoredParticle;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/5/2021 @ 8:49 PM
 */
public class ApoloParticle {

	private static final DustSpellColor YELLOW = new DustSpellColor(Color.YELLOW);
	private static final DustSpellColor GOLD = new DustSpellColor(Color.ORANGE);

	public void playParticle(Player player) {
		Location locationA, locationB;
		int tick = player.getTicksLived();

		float x = (float) (Math.sin(tick / 7d) * 1f);
		float z = (float) (Math.cos(tick / 7d) * 1f);
		float y = (float) (Math.cos(tick / 17d) * 1f + 1f);

		locationA = player.getLocation().add(0, 0.1, 0);
		locationB = locationA.clone();

		locationA.add(x, y, z);
		locationB.add(-x, y, -z);

		if (PlayerUtils.isMoving(player)) {
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.DRIP_LAVA, player.getLocation().add(0, 0.2, 0), 0.3f, 0f, 0.3f, 0, 2, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		} else {
			new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, YELLOW, locationA).display();
			new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, GOLD, locationB).display();
		}
	}
}

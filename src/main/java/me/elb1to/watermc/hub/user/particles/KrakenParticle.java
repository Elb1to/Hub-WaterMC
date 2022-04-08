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
 * Date: 2/5/2021 @ 9:00 PM
 */
public class KrakenParticle {

	private static final DustSpellColor TEAL = new DustSpellColor(Color.TEAL);

	public void playParticle(Player player) {
		int tick = player.getTicksLived();

		double angle = tick * ((2 * Math.PI) / 16);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		if (!PlayerUtils.isMoving(player)) {
			for (double initialCircle = 0.7; initialCircle < 2.6; initialCircle = initialCircle + 0.1) {
				Location loc = player.getLocation().clone().add(initialCircle * cos, 0.1, initialCircle * sin);

				new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, TEAL, loc).display();
			}
		} else {
			new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, TEAL, player.getLocation().add(0, 0.2, 0)).display();
			ParticleUtils.PlayParticle(ParticleUtils.ParticleType.SPLASH, player.getLocation().add(0, 0.2, 0), 0.3f, 0f, 0.3f, 0, 3, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		}
	}
}

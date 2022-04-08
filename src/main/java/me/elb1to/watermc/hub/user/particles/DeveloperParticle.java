package me.elb1to.watermc.hub.user.particles;

import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/9/2021 @ 9:51 AM
 */
public class DeveloperParticle {

	private double y = 2.5;
	private boolean down = false;

	public void playParticle(Player player) {
		Location loc = player.getLocation();

		double radius = 1.1;
		double x = radius * Math.cos((this.down ? -2 : 2) * this.y);
		double z = radius * Math.sin((this.down ? -2 : 2) * this.y);

		double y2 = 2.5 - this.y;

		Location L1 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
		Location L2 = new Location(loc.getWorld(), loc.getX() - x, loc.getY() + y2, loc.getZ() - z);

		ParticleUtils.PlayParticle(ParticleUtils.ParticleType.FLAME, L1, 0, 0, 0, 0, 2, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
		ParticleUtils.PlayParticle(ParticleUtils.ParticleType.FLAME, L2, 0, 0, 0, 0, 2, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());

		if (this.y >= 2.5) {
			this.down = true;
		} else if (this.y <= 0.0) {
			this.down = false;
		}
		if (this.down) {
			this.y -= 0.15;
		} else {
			this.y += 0.15;
		}
	}
}

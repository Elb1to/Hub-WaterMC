package me.elb1to.watermc.hub.utils.particles.data;

import me.elb1to.watermc.hub.utils.extra.ServerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ParticleData {

	protected ParticleUtils.ParticleType particleType;
	protected Location location;

	public ParticleData(ParticleUtils.ParticleType particleType, Location location) {
		this.particleType = particleType;
		this.location = location;
	}

	public void display(int count) {
		display(count, ParticleUtils.ViewDist.NORMAL, ServerUtils.getPlayers());
	}

	public void display(ParticleUtils.ViewDist viewDist, Player... players) {
		ParticleUtils.PlayParticle(particleType, location, 0f, 0f, 0f, 0f, 1, viewDist, players);
	}

	public void display(int count, ParticleUtils.ViewDist viewDist, Player... players) {
		ParticleUtils.PlayParticle(particleType, location, 0f, 0f, 0f, 0f, count, viewDist, players);
	}

	public void display(ParticleUtils.ViewDist viewDist) {
		display(viewDist, ServerUtils.getPlayers());
	}

	public void display() {
		display(ParticleUtils.ViewDist.NORMAL);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
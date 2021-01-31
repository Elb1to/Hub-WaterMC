package me.elb1to.watermc.hub.utils.particles.data;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 1/16/2021 @ 4:00 PM
 */
public class EffectLocation {

	private Location location;
	private Entity entity;

	public EffectLocation(Location location) {
		this.location = location;
		entity = null;
	}

	public EffectLocation(Entity entity) {
		location = entity.getLocation();
		this.entity = entity;
	}

	public Location getLocation() {
		if (entity != null && entity.isValid() && !entity.isDead()) {
			return entity.getLocation().clone();
		}
		if (location != null) {
			return location.clone();
		}
		return null;
	}

	public Location getFixedLocation() {
		if (location != null) {
			return location.clone();
		}
		return null;
	}
}

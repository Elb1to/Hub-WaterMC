package me.elb1to.watermc.hub.utils.particles.types;

import me.elb1to.watermc.hub.utils.extra.AlgebraUtils;
import me.elb1to.watermc.hub.utils.extra.BlockUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LineParticle {

	private final Location startingLocation;
	private final double incrementedRange;
	private final double maxRange;
	private final ParticleUtils.ParticleType particleType;
	private final Player[] toDisplay;
	private Vector direction;
	private Location lastLocation;
	private double curRange;
	private boolean ignoreAllBlocks;

	public LineParticle(Location start, Vector direction, double incrementedRange, double maxRange, ParticleUtils.ParticleType particleType, Player... toDisplay) {
		this(start, null, direction, incrementedRange, maxRange, particleType, toDisplay);
	}

	public LineParticle(Location start, Location end, Vector direction, double incrementedRange, double maxRange, ParticleUtils.ParticleType particleType, Player... toDisplay) {
		startingLocation = start;
		this.direction = direction;
		lastLocation = start;

		curRange = 0;
		this.incrementedRange = incrementedRange;
		this.maxRange = maxRange;

		this.particleType = particleType;
		this.toDisplay = toDisplay;

		if (this.direction == null) {
			this.direction = AlgebraUtils.getTrajectory(start, end);
		}
	}

	/**
	 * Advances the line.
	 *
	 * @return true when the line has reached its target or has collided with a solid block.
	 */
	public boolean update() {
		boolean done = curRange > maxRange;
		Location newTarget = startingLocation.clone().add(direction.clone().multiply(curRange));
		if (newTarget.getY() < 0) {
			newTarget.add(0, 0.2, 0);
		}

		lastLocation = newTarget;
		if (!ignoreAllBlocks && BlockUtils.solid(newTarget.getBlock()) && BlockUtils.solid(newTarget.getBlock().getRelative(BlockFace.UP))) {
			done = true;
		}

		curRange += incrementedRange;
		ParticleUtils.PlayParticle(particleType, newTarget, null, 0, 1, ParticleUtils.ViewDist.LONG, toDisplay);

		return done;
	}

	public void setIgnoreAllBlocks(boolean b) {
		ignoreAllBlocks = b;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public Location getDestination() {
		return lastLocation.subtract(direction);
	}
}

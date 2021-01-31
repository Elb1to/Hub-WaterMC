package me.elb1to.watermc.hub.utils.particles.types;

import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import me.elb1to.watermc.hub.utils.particles.data.DustSpellColor;
import me.elb1to.watermc.hub.utils.particles.data.NoteColor;
import me.elb1to.watermc.hub.utils.particles.data.ParticleColor;
import me.elb1to.watermc.hub.utils.particles.data.ParticleData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ColoredParticle extends ParticleData {

	private ParticleColor _color;

	public ColoredParticle(ParticleUtils.ParticleType particleType, ParticleColor color, Location location) {
		super(particleType, location);
		if ((particleType == ParticleUtils.ParticleType.RED_DUST || particleType == ParticleUtils.ParticleType.MOB_SPELL_AMBIENT)
				&& !(color instanceof DustSpellColor))
			throw new IllegalArgumentException("RED_DUST and MOB_SPELL_AMBIENT particle types require a DustSpellColor!");
		else if (particleType == ParticleUtils.ParticleType.NOTE && !(color instanceof NoteColor))
			throw new IllegalArgumentException("NOTE particle type requires a NoteColor!");
		else if (particleType != ParticleUtils.ParticleType.RED_DUST && particleType != ParticleUtils.ParticleType.MOB_SPELL_AMBIENT
				&& particleType != ParticleUtils.ParticleType.NOTE)
			throw new IllegalArgumentException("Particle Type must be RED_DUST, MOB_SPELL_AMBIENT!");
		this.particleType = particleType;
		_color = color;
		this.location = location;
	}

	@Override
	public void display(ParticleUtils.ViewDist viewDist, Player... players) {
		float x = _color.getX();
		if (particleType == ParticleUtils.ParticleType.RED_DUST && x == 0)
			x = Float.MIN_NORMAL;
		ParticleUtils.PlayParticle(particleType, location, x, _color.getY(), _color.getZ(), 1, 0, viewDist, players);
	}

	@Override
	public void display(int count, ParticleUtils.ViewDist viewDist, Player... players) {
		// It's not possible to have colored particles with count, so just repeat it
		for (int i = 0; i < count; i++) {
			display(viewDist, players);
		}
	}

	public void setColor(ParticleColor color) {
		_color = color;
	}
}
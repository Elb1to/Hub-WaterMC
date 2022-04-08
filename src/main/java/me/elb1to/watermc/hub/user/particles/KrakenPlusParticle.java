package me.elb1to.watermc.hub.user.particles;

import me.elb1to.watermc.hub.utils.extra.PlayerUtils;
import me.elb1to.watermc.hub.utils.particles.ParticleUtils;
import me.elb1to.watermc.hub.utils.particles.data.DustSpellColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/9/2021 @ 9:36 AM
 */
public class KrakenPlusParticle {

	private static final DustSpellColor AQUA = new DustSpellColor(Color.AQUA);
	private final Byte[] bytes = new Byte[]{1, 2, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15};

	public void playParticle(Player player) {
		Location loc = player.getLocation().add(Math.random() * 2 - 1, 2.3 + Math.random() * 0.7, Math.random() * 2 - 1);

		List<Byte> list = Arrays.asList(bytes);
		Collections.shuffle(list);
		for (int i = 0; i < 1; i++) {
			String particle = ParticleUtils.ParticleType.RED_DUST.getParticle(Material.INK_SACK, list.get(i));
			if (PlayerUtils.isMoving(player)) {
				ParticleUtils.playParticleFor(player, particle, player.getLocation().add(0, 1, 0), null, 0.08f, 1, ParticleUtils.ViewDist.NORMAL);
			} else {
				ParticleUtils.playParticleFor(player, particle, loc, null, 0.08f, 10, ParticleUtils.ViewDist.NORMAL);
			}
		}
	}

	/*public void playParticle(Player player) {
		int tick = player.getTicksLived();
		Location location = player.getLocation();

		//double angle = tick * ((2 * Math.PI) / 16);
		*//*double cos = Math.cos(angle);
		double sin = Math.sin(angle);*//*

		double phi = 0;
		phi += Math.PI / 10;

		for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 16) {
			double r = 1.5;
			double x = r * Math.cos(theta) * Math.sin(phi);
			double y = r * Math.cos(phi) + 1.5;
			//double y = 0.3;
			double z = r * Math.sin(theta) * Math.sin(phi);

			location.add(x, y, z);
			new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, AQUA, location).display();
			location.subtract(x, 0, z);
		}
		*//*if (phi > 8 * Math.PI) {
			this.cancel();
		}*//*

	 *//*double ring = Math.PI * Math.sqrt(1);
		double iCircle = ring;

		for (double i = 0; ring < 2.0; ring = ring + 0.1) {
			Location loc = player.getLocation().clone().add(ring * cos, 0.1, ring * sin);
			new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, AQUA, loc).display();
		}

		for (double initialCircle = 0.6; initialCircle < 2.0; initialCircle++) {
			Location loc = player.getLocation().clone().add(initialCircle * cos, 0.1, initialCircle * sin);

			new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, TEAL, loc).display();
		}*//*
	}*/

	/*public void playParticle(Player player) {
		Location location = player.getLocation();
		new BukkitRunnable() {
			@Override
			public void run() {
				double phi = 0;
				phi += Math.PI / 10;

				for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 40) {
					double r = 1.5;
					double x = r * Math.cos(theta) * Math.sin(phi);
					double y = r * Math.cos(phi) + 1.5;
					double z = r * Math.sin(theta) * Math.sin(phi);

					location.add(x, y, z);
					new ColoredParticle(ParticleUtils.ParticleType.RED_DUST, AQUA, location).display();
					location.subtract(x, y, z);
				}
				if (phi > 8 * Math.PI) {
					this.cancel();
				}
			}
		}.runTaskTimer(Hub.getInstance(), 0, 1L);
	}*/
}

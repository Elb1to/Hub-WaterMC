package me.elb1to.watermc.hub.utils.particles.data;

import org.bukkit.Color;

public class NoteColor extends ParticleColor {

	private int red, green, blue;

	public NoteColor(Color color) {
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
	}

	public NoteColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public float getX() {
		return (float) red / 24f;
	}

	public float getY() {
		return 0f;
	}

	public float getZ() {
		return 0f;
	}
}

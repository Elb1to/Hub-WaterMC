package me.elb1to.watermc.hub;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Hub extends JavaPlugin {

	@Getter private static Hub instance;

	@Override
	public void onEnable() {
		instance = this;
	}

	@Override
	public void onDisable() {
		instance = null;
	}
}

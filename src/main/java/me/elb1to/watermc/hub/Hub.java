package me.elb1to.watermc.hub;

import lombok.Getter;
import me.elb1to.watermc.hub.database.Database;
import me.elb1to.watermc.hub.managers.vault.VaultManager;
import me.elb1to.watermc.hub.utils.command.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Hub extends JavaPlugin {

	@Getter private static Hub instance;

	private Database databaseHandler;
	private VaultManager vaultManager;
	private CommandFramework commandFramework;

	@Override
	public void onEnable() {
		instance = this;

		commandFramework = new CommandFramework(this);
	}

	@Override
	public void onDisable() {
		instance = null;
	}
}

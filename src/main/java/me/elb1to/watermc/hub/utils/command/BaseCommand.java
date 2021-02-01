package me.elb1to.watermc.hub.utils.command;

import me.elb1to.watermc.hub.Hub;

public abstract class BaseCommand {

	public Hub plugin = Hub.getInstance();

	public BaseCommand() {
		plugin.getCommandFramework().registerCommands(this);
	}

	public abstract void onCommand(CommandArgs command);
}

package me.elb1to.watermc.hub.utils.command;

import me.elb1to.watermc.hub.Hub;

public abstract class BaseCMD {

	public Hub plugin = Hub.getInstance();

	public BaseCMD() {
		this.plugin.getCommandFramework().registerCommands(this);
	}

	public abstract void onCommand(CommandArgs cmd);
}

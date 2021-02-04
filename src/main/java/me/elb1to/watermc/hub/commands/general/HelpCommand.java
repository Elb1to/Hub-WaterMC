package me.elb1to.watermc.hub.commands.general;

import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/3/2021 @ 12:50 AM
 */
public class HelpCommand extends BaseCommand {

	@Override @Command(name = "help")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();

		player.sendMessage(CC.CHAT_BAR);
		player.sendMessage(CC.translate(CC.centerMessage("&3&lWaterMC &fNetwork")));
		player.sendMessage(" ");
		player.sendMessage(CC.translate(" &8 ▶ &bTwitter: &f@WaterMCNetwork"));
		player.sendMessage(CC.translate(" &8 ▶ &bTienda: &fhttps://store.watermc.gg/"));
		player.sendMessage(CC.translate(" &8 ▶ &bDiscord: &fhttps://discord.gg/xbN5Mfx3vD"));
		player.sendMessage(CC.CHAT_BAR);
	}
}

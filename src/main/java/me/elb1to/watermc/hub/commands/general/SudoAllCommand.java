package me.elb1to.watermc.hub.commands.general;

import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/3/2021 @ 10:24 PM
 */
public class SudoAllCommand extends BaseCommand {

	@Override @Command(name = "sudoall", permission = "hub.commands.sudoall")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();

		if (args.length == 0) {
			player.sendMessage(CC.translate("&cUsage: /sudoall <command>"));
			return;
		}

		Bukkit.getServer().getOnlinePlayers().forEach(online -> online.chat(CC.build(args, 0)));
		player.sendMessage(CC.translate("&8[&bHub &7| &fWaterMC&8] &aYou made everyone execute the following command: &o" + CC.build(args, 0)));
	}
}

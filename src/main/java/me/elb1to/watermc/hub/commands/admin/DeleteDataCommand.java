package me.elb1to.watermc.hub.commands.admin;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub
 * Date: 2/8/2021 @ 1:39 PM
 */
public class DeleteDataCommand extends BaseCommand {

	@Override @Command(name = "wipedata", permission = "hub.commands.admin.wipedata")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();

		if (args.length == 0) {
			player.sendMessage(CC.translate("&cUsage: /wipedata <user>"));
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(CC.translate("&cThe player named '" + args[0] + "' is not online!"));
			return;
		}

		Hub.getInstance().getHubPlayerManager().removePlayerData(target.getUniqueId());
		Bukkit.getServer().getPlayer(target.getUniqueId()).kickPlayer(CC.translate("&cPlease Reconnect!\n&cYour HubPlayer data has been wiped."));
		player.sendMessage(CC.translate("&8[&bHub &7| &fWaterMC&8] &aSuccessfully wiped " + target.getName() + "'s hub player data."));
	}
}

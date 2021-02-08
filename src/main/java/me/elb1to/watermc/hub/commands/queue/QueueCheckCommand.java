package me.elb1to.watermc.hub.commands.queue;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/1/2021 @ 3:00 PM
 */
public class QueueCheckCommand extends BaseCommand {

	@Override @Command(name = "queue.check", permission = "hub.commands.queue.check")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();

		if (args.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /queue check <player>"));
		} else {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null || !target.isOnline()) {
				player.sendMessage(Hub.getInstance().getMessagesConfig().getString("PLAYER.NOT-FOUND"));
			} else {
				if (!Hub.getInstance().getQueueManager().isQueueing(target)) {
					player.sendMessage(Hub.getInstance().getMessagesConfig().getString("PLAYER.NOT-QUEUEING"));
				} else {
					Queue queue = Hub.getInstance().getQueueManager().getPlayerQueue(target);
					Hub.getInstance().getMessagesConfig().getStringList("PLAYER.QUEUE-CHECK").forEach(msg -> {
						msg = msg.replace("<PLAYERNAME>", target.getName());
						msg = msg.replace("<QUEUE-NAME>", queue.getServer());
						msg = msg.replace("<QUEUE-BUNGEE-NAME>", queue.getBungeeName());
						msg = msg.replace("<QUEUE-POSITION>", String.valueOf(queue.getPosition(player)));

						player.sendMessage(CC.translate(msg));
					});
				}
			}
		}
	}
}

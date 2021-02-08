package me.elb1to.watermc.hub.commands.queue;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/1/2021 @ 3:00 PM
 */
public class JoinQueueCommand extends BaseCommand {

	@Override @Command(name = "queue.join")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();

		if (Hub.getInstance().getQueueManager().isQueueing(player)) {
			player.sendMessage(CC.translate(Hub.getInstance().getMessagesConfig().getString("QUEUE.ALREADY-QUEUEING")
					.replace("<QUEUE-NAME>", Hub.getInstance().getQueueManager().getPlayerQueue(player).getServer())
			));

			return;
		}

		if (args.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /queue join <server>"));
		} else {
			Queue queue = Hub.getInstance().getQueueManager().getByString(args[0]);
			if (queue == null) {
				player.sendMessage(CC.translate(Hub.getInstance().getMessagesConfig().getString("QUEUE.NOT-FOUND")));
			} else {
				queue.add(player);
			}
		}
	}
}

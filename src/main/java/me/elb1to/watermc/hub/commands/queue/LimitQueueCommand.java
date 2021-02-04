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
 * Date: 2/1/2021 @ 3:01 PM
 */
public class LimitQueueCommand extends BaseCommand {

	@Override @Command(name = "queue.limit", permission = "hub.commands.queue.limit")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();

		if (args.length < 2) {
			player.sendMessage(CC.translate("&cUsage: /queue limit <server> <limit>"));
			return;
		}

		Queue queue = Hub.getInstance().getQueueManager().getByString(args[0]);
		if (queue == null) {
			player.sendMessage(CC.translate(Hub.getInstance().getMessagesConfig().getString("QUEUE.NOT-FOUND")));
		} else {
			int limit;
			try {
				limit = Integer.parseInt(args[1]);
			} catch (Exception ignored) {
				player.sendMessage(CC.translate("&cInvalid amount."));
				return;
			}

			queue.setLimit(limit);
			player.sendMessage(CC.translate(Hub.getInstance().getMessagesConfig().getString("QUEUE.LIMIT")
					.replace("{QUEUE-LIMIT}", String.valueOf(limit))
					.replace("{QUEUE-NAME}", queue.getServer())
			));
		}
	}
}

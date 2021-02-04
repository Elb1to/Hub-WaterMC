package me.elb1to.watermc.hub.commands.queue;

import me.elb1to.watermc.hub.Hub;
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
public class LeaveQueueCommand extends BaseCommand {

	@Override @Command(name = "queue.leave")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();

		if (!Hub.getInstance().getQueueManager().isQueueing(player)) {
			player.sendMessage(CC.translate(Hub.getInstance().getMessagesConfig().getString("QUEUE.NOT-QUEUEING")));
			return;
		}

		Hub.getInstance().getQueueManager().getPlayerQueue(player).remove(player);
	}
}

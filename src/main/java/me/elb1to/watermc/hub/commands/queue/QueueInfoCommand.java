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
public class QueueInfoCommand extends BaseCommand {

	@Override
	@Command(name = "queue.info", permission = "root.command.queue.info")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();

		if (args.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /queue info <server>"));
			return;
		}

		Queue queue = Hub.getInstance().getQueueManager().getByString(args[0]);
		if (queue == null) {
			player.sendMessage(Hub.getInstance().getMessagesConfig().getString("QUEUE.NOT-FOUND"));
		} else {
			Hub.getInstance().getMessagesConfig().getStringList("QUEUE.QUEUE-INFO").forEach(s -> {
				s = s.replace("{QUEUE-NAME}", queue.getServer());
				s = s.replace("{QUEUE-BUNGEE-NAME}", queue.getBungeeName());
				s = s.replace("{QUEUE-SIZE}", String.valueOf(queue.getUuids().size()));
				s = s.replace("{QUEUE-STATUS}", Hub.getInstance().getMessagesConfig().getString("QUEUE.STATUS." + (queue.isPaused() ? "" : "UN-") + "PAUSED"));
				s = s.replace("{QUEUE-LIMIT}", String.valueOf(queue.getLimit()));

				player.sendMessage(s);
			});
		}
	}
}

package me.elb1to.watermc.hub.commands.queue;

import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/1/2021 @ 3:07 PM
 */
public class QueueCommand extends BaseCommand {

	@Override @Command(name = "queue")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();

		player.sendMessage(CC.CHAT_BAR);
		player.sendMessage(CC.translate("&b&lQueue Command:"));
		player.sendMessage(" ");
		player.sendMessage(CC.translate("&8 ▶ &b/queue join <server> &7- &fJoin to the queue for a server"));
		player.sendMessage(CC.translate("&8 ▶ &b/queue leave &7- &fLeave from your current server queue"));
		if (player.hasPermission("hub.commands.queue.pause")) {
			player.sendMessage(CC.translate("&8 ▶ &b/queue pause <server> &7- &fToggle pause for server queue (Will be turned off when stopped)"));
		}
		if (player.hasPermission("hub.commands.queue.limit")) {
			player.sendMessage(CC.translate("&8 ▶ &b/queue limit <server> <limit> &7- &fSet server queue limit (Will be restored when stopped)"));
		}
		if (player.hasPermission("hub.commands.queue.check")) {
			player.sendMessage(CC.translate("&8 ▶ &b/queue check <player> &7- &fCheck player's server queue"));
		}
		if (player.hasPermission("hub.commands.queue.info")) {
			player.sendMessage(CC.translate("&8 ▶ &b/queue info <server> &7- &fGet more information about a queue"));
		}
		player.sendMessage(CC.CHAT_BAR);
	}
}

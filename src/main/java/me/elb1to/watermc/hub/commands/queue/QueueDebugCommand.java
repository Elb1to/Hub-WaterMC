package me.elb1to.watermc.hub.commands.queue;

import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * Created by Elb1to
 * Project: Hub
 * Date: 2/8/2021 @ 2:57 PM
 */
public class QueueDebugCommand extends BaseCommand {

	@Override @Command(name = "queue.debug", inGameOnly = false)
	public void onCommand(CommandArgs command) {
		CommandSender sender = command.getSender();

		Queue hck = new Queue("HCK");
		Queue hcf = new Queue("HCF");
		Queue kits = new Queue("KITS");
		Queue skykits = new Queue("SKYKITS");

		sender.sendMessage("HCK Queued Players: " + hck.getPlayers().size());
		sender.sendMessage("HCK UUIDs Players: " + hck.getUuids().size());
		sender.sendMessage("HCK UUIDs Player Strings: " + hck.getUuids().toString());

		sender.sendMessage("-------------------------------------");

		sender.sendMessage("HCF Queued Players: " + hcf.getPlayers().size());
		sender.sendMessage("KITS Queued Players: " + kits.getPlayers().size());
		sender.sendMessage("SKYKITS Queued Players: " + skykits.getPlayers().size());
	}
}

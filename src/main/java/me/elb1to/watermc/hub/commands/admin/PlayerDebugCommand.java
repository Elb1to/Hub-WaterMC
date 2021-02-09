package me.elb1to.watermc.hub.commands.admin;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.user.NewHubPlayer;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub
 * Date: 2/8/2021 @ 3:28 PM
 */
public class PlayerDebugCommand extends BaseCommand {

	@Override @Command(name = "pdebug", permission = "hub.commands.admin.playerdebug", inGameOnly = false)
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();

		if (args.length == 0) {
			player.sendMessage(CC.translate("&cUsage: /pdebug <user>"));
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(CC.translate("&cThe player named '" + args[0] + "' is not online!"));
			return;
		}

		NewHubPlayer newHubPlayer = Hub.getInstance().getHubPlayerManager().getPlayerData(target.getUniqueId());
		Queue queue = Hub.getInstance().getQueueManager().getPlayerQueue(player);

		player.sendMessage(CC.CHAT_BAR);
		player.sendMessage(CC.translate("&b&lPlayer Debug"));
		player.sendMessage(CC.translate("&8 -> &fUser: " + target.getName()));
		player.sendMessage(CC.translate("&8 -> &fState: " + newHubPlayer.getPlayerState()));
		player.sendMessage(CC.translate("&8 -> &fisQueueing: " + queue.isQueueing(player)));
		player.sendMessage(CC.translate("&8 -> &fQueued Server: " + queue.getServer()));
		player.sendMessage(CC.translate("&8 -> &fQueue Position: " + queue.getPosition(player)));
		player.sendMessage(CC.translate("&8 -> &fQueued Priority: " + queue.getPriority(player)));
		player.sendMessage(CC.CHAT_BAR);
	}
}

package me.elb1to.watermc.hub.commands.general;

import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.command.BaseCommand;
import me.elb1to.watermc.hub.utils.command.Command;
import me.elb1to.watermc.hub.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: Hub
 * Date: 2/8/2021 @ 12:41 PM
 */
public class SpawnCommand extends BaseCommand {

	@Override @Command(name = "spawn")
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();

		player.teleport(player.getWorld().getSpawnLocation());
		player.sendMessage(CC.translate("&aYou've been sent back to the spawnpoint."));
	}
}

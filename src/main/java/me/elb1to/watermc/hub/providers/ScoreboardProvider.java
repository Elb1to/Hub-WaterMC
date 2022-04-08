package me.elb1to.watermc.hub.providers;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.user.NewHubPlayer;
import me.elb1to.watermc.hub.user.PlayerState;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.scoreboard.BoardAdapter;
import me.elb1to.watermc.hub.utils.scoreboard.BoardStyle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 1/31/2021 @ 2:43 PM
 */
public class ScoreboardProvider implements BoardAdapter {

	private final Hub plugin = Hub.getInstance();

	@Override
	public String getTitle(Player player) {
		return CC.translate("&3&lWaterMC &fNetwork");
	}

	@Override
	public BoardStyle getBoardStyle(Player player) {
		return BoardStyle.MODERN;
	}

	@Override
	public List<String> getLines(Player player) {
		List<String> lines = new ArrayList<>();
		NewHubPlayer newHubPlayer = Hub.getInstance().getHubPlayerManager().getPlayerData(player.getUniqueId());
		Queue queue = this.plugin.getQueueManager().getPlayerQueue(player);

		lines.add(CC.SB_BAR);
		lines.add(CC.translate("&fJugadores:"));
		lines.add(CC.translate("&b" + this.plugin.getNetworkTotalPlayer("BUNGEE", true) + " / 2,500"));
		lines.add(CC.translate(" "));
		lines.add(CC.translate("&fRango:"));
		lines.add(CC.translate(this.plugin.getVaultPerm().getPrimaryGroup(player)));
		if (newHubPlayer.getPlayerState().equals(PlayerState.QUEUE)) {
			lines.add(CC.translate(" "));
			lines.add(CC.translate("&fEn Cola:"));
			lines.add(CC.translate(" &bServer&f: &3" + (queue.getServer() == null ? "NOT_FOUND" : queue.getServer())));
			lines.add(CC.translate(" &bPosicion&f: &3" + queue.getPosition(player) + "/" + queue.getPlayers().size()));
		}
		lines.add(CC.translate(" "));
		lines.add(CC.translate("&bwatermc.gg"));
		lines.add(CC.SB_BAR);

		return lines;
	}
}

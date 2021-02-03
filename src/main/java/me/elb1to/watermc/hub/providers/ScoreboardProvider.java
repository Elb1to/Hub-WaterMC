package me.elb1to.watermc.hub.providers;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import me.elb1to.watermc.hub.user.HubPlayer;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.scoreboard.BoardAdapter;
import me.elb1to.watermc.hub.utils.scoreboard.BoardStyle;
import org.apache.commons.lang3.StringUtils;
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
		HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());

		lines.add(CC.SB_BAR);
		switch (hubPlayer.getState()) {
			case LOBBY:
				lines.add(CC.translate("&fJugadores:"));
				lines.add(CC.translate("&b" + this.plugin.getNetworkTotalPlayer("BUNGEE", true) + " / 2,500"));
				lines.add(CC.translate(" "));
				lines.add(CC.translate("&fRango:"));
				lines.add(CC.translate(StringUtils.capitalize(this.plugin.getVaultPerm().getPrimaryGroup(player))));
				break;
			case QUEUE:
				Queue queue = this.plugin.getQueueManager().getPlayerQueue(player);
				lines.add(CC.translate("&fJugadores:"));
				lines.add(CC.translate("&b" + this.plugin.getNetworkTotalPlayer("BUNGEE", true) + " / 2,500"));
				lines.add(CC.translate(" "));
				lines.add(CC.translate("&fRango:"));
				lines.add(CC.translate(this.plugin.getVaultChat().getPlayerPrefix(player)));
				lines.add(CC.translate(StringUtils.capitalize(this.plugin.getVaultPerm().getPrimaryGroup(player))));
				lines.add(CC.translate(" "));
				lines.add(CC.translate("&fEn Cola:"));
				lines.add(CC.translate("  &bServer&f: &3" + queue.getServer()));
				lines.add(CC.translate("  &bPosicion&f: &3" + queue.getPosition(player) + "/" + queue.getPlayers().size()));
				break;
		}
		lines.add(CC.translate(" "));
		lines.add(CC.translate("&bwatermc.gg"));
		lines.add(CC.SB_BAR);

		return lines;
	}

	private String rankGetter(Player player) {
		String rank = "";


		return rank;
	}
}

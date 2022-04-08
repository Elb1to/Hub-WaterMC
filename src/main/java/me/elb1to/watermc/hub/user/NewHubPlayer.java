package me.elb1to.watermc.hub.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by Elb1to
 * Project: Hub
 * Date: 2/8/2021 @ 1:14 PM
 */
@Getter @Setter @RequiredArgsConstructor
public class NewHubPlayer {

	private final UUID uniqueId;
	private PlayerState playerState = PlayerState.LOBBY;

	private String clickedHCFServer;
	private String currentParticle;

	private boolean hidingParticles = false;
	private boolean hidingPlayers = false;
	private boolean flyMode = false;

	private boolean dataLoaded;
}

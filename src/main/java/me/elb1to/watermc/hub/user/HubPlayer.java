package me.elb1to.watermc.hub.user;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.elb1to.watermc.hub.Hub;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/1/2021 @ 12:06 PM
 */
@Getter @Setter
public class HubPlayer {

	public static Map<UUID, HubPlayer> playersData = new HashMap<>();

	private UUID uuid;
	private PlayerState state;

	private String currentArmor;
	private String currentParticle;

	private boolean hidingParticles = false;
	private boolean hidingPlayers = false;
	private boolean flyModeEnabled = false;
	private boolean speedModeEnabled = false;

	private boolean dataLoaded;

	public HubPlayer(UUID uuid) {
		this.uuid = uuid;
		playersData.put(uuid, this);
		this.state = PlayerState.LOBBY;
		this.dataLoaded = false;
	}

	public static HubPlayer getByUuid(UUID uuid) {
		return playersData.get(uuid);
	}

	public static Collection<HubPlayer> getAllData() {
		return playersData.values();
	}

	public HubPlayer getPlayerData(UUID uuid) {
		return playersData.get(uuid);
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.uuid);
	}

	public void loadData(HubPlayer hubPlayer) {
		Document document = Hub.getInstance().getMongoDbManager().getHubPlayerData().find(Filters.eq("uuid", hubPlayer.getUuid().toString())).first();
		if (document == null) {
			hubPlayer.setCurrentArmor("none");
			hubPlayer.setCurrentParticle("none");
			hubPlayer.setState(PlayerState.LOBBY);

			this.saveData(hubPlayer);
			return;
		}

		Document settingsDocument = (Document) document.get("hubPlayerSettings");
		hubPlayer.setHidingPlayers(settingsDocument.getBoolean("hidingPlayers"));
		hubPlayer.setHidingParticles(settingsDocument.getBoolean("hidingParticles"));
		hubPlayer.setFlyModeEnabled(settingsDocument.getBoolean("flyMode"));
		hubPlayer.setSpeedModeEnabled(settingsDocument.getBoolean("speedMode"));

		this.dataLoaded = true;
	}

	public void saveData(HubPlayer hubPlayer) {
		/*if (!this.dataLoaded) {
			return;
		}*/
		Document document = new Document();
		document.put("uuid", this.uuid.toString());
		document.put("currentArmor", this.currentArmor);
		document.put("currentParticle", this.currentParticle);

		Document settingsDocument = new Document();
		settingsDocument.put("hidingPlayers", hubPlayer.isHidingPlayers());
		settingsDocument.put("hidingParticles", hubPlayer.isHidingParticles());
		settingsDocument.put("flyMode", hubPlayer.isFlyModeEnabled());
		settingsDocument.put("speedMode", hubPlayer.isSpeedModeEnabled());

		document.put("hubPlayerSettings", settingsDocument);

		this.dataLoaded = false;

		playersData.remove(this.uuid);

		Hub.getInstance().getMongoDbManager().getHubPlayerData().replaceOne(Filters.eq("uuid", hubPlayer.getUuid().toString()), document, new ReplaceOptions().upsert(true));
	}
}

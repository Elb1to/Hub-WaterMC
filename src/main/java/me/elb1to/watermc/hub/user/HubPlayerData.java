package me.elb1to.watermc.hub.user;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import me.elb1to.watermc.hub.Hub;
import org.bson.Document;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 1/30/2021 @ 9:27 PM
 */
@Getter
@Setter
public class HubPlayerData {

	@Getter private static Map<UUID, HubPlayerData> playerData = Maps.newHashMap();
	@Getter private static Map<String, HubPlayerData> playerDataNames = Maps.newHashMap();
	private Hub plugin = Hub.getInstance();
	private String name;
	private UUID uuid;
	private boolean dataLoaded = false;
	private PlayerState state;

	private HubPlayerData(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;
		this.state = PlayerState.LOBBY;
		load(plugin.getDatabaseHandler().loadData(uuid, name));
	}

	public static HubPlayerData createPlayerData(UUID uuid, String name) {
		if (playerData.containsKey(uuid)) return getPlayerData(uuid);
		HubPlayerData hubPlayerData = new HubPlayerData(name, uuid);
		playerData.put(uuid, hubPlayerData);
		playerDataNames.put(name, hubPlayerData);
		return getPlayerData(uuid);
	}

	public static HubPlayerData getPlayerData(UUID uuid) {
		return playerData.get(uuid);
	}

	public static HubPlayerData getPlayerData(String name) {
		return playerDataNames.get(name);
	}

	public void load(Document document) {
		if (document != null) {
			// TODO: Write shit here
		}
		this.dataLoaded = true;
		this.plugin.getLogger().info(this.getName() + "'s data was successfully loaded.");
	}

	public void saveData() {
		Document document = new Document();

		document.put("uuid", this.uuid.toString());
		document.put("name", this.name);
		document.put("name_lowecase", this.name.toLowerCase());

		this.plugin.getDatabaseHandler().saveData(document);
	}

	public void removeData() {
		this.saveData();
		playerData.remove(this.uuid);
		playerDataNames.remove(this.name);
	}

	public static enum PlayerState {
		LOBBY,
		QUEUE
	}
}

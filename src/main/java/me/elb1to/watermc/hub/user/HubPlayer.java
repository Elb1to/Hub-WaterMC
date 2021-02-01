package me.elb1to.watermc.hub.user;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import lombok.Setter;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.managers.MongoDbManager;
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
	public static Map<String, HubPlayer> playersDataNames = new HashMap<>();

	private UUID uuid;
	private String name;
	private boolean dataLoaded;
	private PlayerState state;

	private boolean hasCosmetic;
	private String currentArmor;
	private String currentParticle;

	public HubPlayer(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		playersData.put(uuid, this);
		playersDataNames.put(name, this);
		this.state = PlayerState.LOBBY;
		this.dataLoaded = false;
	}

	public static HubPlayer getByUuid(UUID uuid) {
		return playersData.get(uuid);
	}

	public static HubPlayer getByName(String name) {
		return playersDataNames.get(name);
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

	public boolean isOnline() {
		return (Bukkit.getPlayer(this.uuid) != null);
	}

	public void loadData() {
		MongoDbManager mongoDB = Hub.getInstance().getMongoDbManager();
		Document document = mongoDB.getHubPlayerData().find(Filters.eq("uuid", this.uuid)).first();
		if (document != null) {
			this.currentArmor = document.getString("currentArmor");
			this.currentParticle = document.getString("currentParticle");
		}

		this.dataLoaded = true;
		Hub.getInstance().getLogger().info(getName() + "'s data was successfully loaded.");
	}

	public void saveData() {
		if (!this.dataLoaded) {
			return;
		}

		Document document = new Document();

		document.put("name", this.name);
		document.put("uuid", this.uuid.toString());

		document.put("currentArmor", this.currentArmor);
		document.put("currentParticle", this.currentParticle);

		playersDataNames.remove(this.name);
		playersData.remove(this.uuid);

		this.dataLoaded = false;

		MongoDbManager mongoDB = Hub.getInstance().getMongoDbManager();
		mongoDB.getHubPlayerData().replaceOne(Filters.eq("uuid", this.uuid), document, (new UpdateOptions()).upsert(true));
	}
}

package me.elb1to.watermc.hub.managers;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.user.NewHubPlayer;
import me.elb1to.watermc.hub.user.PlayerState;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Elb1to
 * Project: Hub
 * Date: 2/8/2021 @ 1:12 PM
 */
public class HubPlayerManager {

	private final Hub plugin = Hub.getInstance();
	private final Map<UUID, NewHubPlayer> playerData = new ConcurrentHashMap<>();

	public void createPlayerData(Player player) {
		NewHubPlayer data = new NewHubPlayer(player.getUniqueId());

		this.playerData.put(data.getUniqueId(), data);
	}

	public void loadData(NewHubPlayer NewHubPlayer) {
		NewHubPlayer.setPlayerState(PlayerState.LOBBY);

		Document document = Hub.getInstance().getMongoDbManager().getHubPlayerData().find(Filters.eq("uuid", NewHubPlayer.getUniqueId().toString())).first();
		if (document == null) {
			NewHubPlayer.setCurrentArmor("none");
			NewHubPlayer.setCurrentParticle("none");

			this.saveData(NewHubPlayer);
			return;
		}

		Document settingsDocument = (Document) document.get("playerSettings");
		NewHubPlayer.setHidingPlayers(settingsDocument.getBoolean("hidingPlayers"));
		NewHubPlayer.setHidingParticles(settingsDocument.getBoolean("hidingParticles"));
		NewHubPlayer.setFlyMode(settingsDocument.getBoolean("flyMode"));
	}

	public void removePlayerData(UUID uuid) {
		this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
			HubPlayerManager.this.saveData(HubPlayerManager.this.playerData.get(uuid));
			HubPlayerManager.this.playerData.remove(uuid);
		});
	}

	public void saveData(NewHubPlayer NewHubPlayer) {
		Document document = new Document();
		Document settingsDocument = new Document();

		document.put("uuid", NewHubPlayer.getUniqueId().toString());
		document.put("currentArmor", NewHubPlayer.getCurrentArmor());
		document.put("currentParticle", NewHubPlayer.getCurrentParticle());

		settingsDocument.put("hidingPlayers", NewHubPlayer.isHidingPlayers());
		settingsDocument.put("hidingParticles", NewHubPlayer.isHidingParticles());
		settingsDocument.put("flyMode", NewHubPlayer.isFlyMode());

		document.put("playerSettings", settingsDocument);

		Hub.getInstance().getMongoDbManager().getHubPlayerData().replaceOne(Filters.eq("uuid", NewHubPlayer.getUniqueId().toString()), document, new ReplaceOptions().upsert(true));
	}

	public Collection<NewHubPlayer> getAllData() {
		return this.playerData.values();
	}

	public NewHubPlayer getPlayerData(UUID uuid) {
		return this.playerData.get(uuid);
	}
}

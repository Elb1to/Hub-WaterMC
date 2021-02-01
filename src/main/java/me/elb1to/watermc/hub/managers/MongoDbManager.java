package me.elb1to.watermc.hub.managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.config.ConfigCursor;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Collections;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 2/1/2021 @ 12:00 PM
 */
@Getter
public class MongoDbManager {

	private MongoClient client;
	private MongoDatabase mongoDatabase;

	private final ConfigCursor mongoConfig = new ConfigCursor(Hub.getInstance().getDatabaseConfig(), "MONGO");

	private final String host = mongoConfig.getString("HOST");
	private final int port = mongoConfig.getInt("PORT");
	private final String database = mongoConfig.getString("DATABASE");
	private final boolean authentication = mongoConfig.getBoolean("AUTH.ENABLED");

	private final String user = mongoConfig.getString("AUTH.USERNAME");
	private final String password = mongoConfig.getString("AUTH.PASSWORD");
	private final String authDatabase = mongoConfig.getString("AUTH.AUTH-DATABASE");

	private boolean connected;

	private MongoCollection<Document> hubPlayerData;

	public void connect() {
		try {
			Hub.getInstance().getLogger().info("Connecting to MongoDB...");
			if (authentication) {
				MongoCredential mongoCredential = MongoCredential.createCredential(this.user, this.authDatabase, this.password.toCharArray());
				this.client = new MongoClient(new ServerAddress(this.host, this.port), Collections.singletonList(mongoCredential));
				this.connected = true;
				Bukkit.getConsoleSender().sendMessage(CC.translate("&8[&bWaterMC - Hub&8] &aSuccessfully connected to MongoDB."));
			} else {
				this.client = new MongoClient(new ServerAddress(this.host, this.port));
				this.connected = true;
				Bukkit.getConsoleSender().sendMessage(CC.translate("&8[&bWaterMC - Hub&8] &aSuccessfully connected to MongoDB."));
			}
			this.mongoDatabase = this.client.getDatabase(this.database);
			this.hubPlayerData = this.mongoDatabase.getCollection("WaterMC-HubPlayer");
		} catch (Exception e) {
			this.connected = false;
			Bukkit.getConsoleSender().sendMessage(CC.translate("&cDisabling &b[WaterMC - Hub] &cbecause an error occurred while trying to connect to &aMongoDB."));
			Bukkit.getPluginManager().disablePlugins();
			Bukkit.shutdown();
		}
	}

	public void reconnect() {
		try {
			if (authentication) {
				MongoCredential mongoCredential = MongoCredential.createCredential(this.user, this.authDatabase, this.password.toCharArray());
				this.client = new MongoClient(new ServerAddress(this.host, this.port), Collections.singletonList(mongoCredential));
			} else {
				this.client = new MongoClient(new ServerAddress(this.host, this.port));
			}
			this.mongoDatabase = this.client.getDatabase(this.database);
			this.hubPlayerData = this.mongoDatabase.getCollection("WaterMC-HubPlayer");
		} catch (Exception e) {
			Hub.getInstance().getLogger().info("[MongoDB] An error occurred while trying to connect to MongoDB.");
		}
	}

	public void disconnect() {
		if (this.client != null) {
			Hub.getInstance().getLogger().info("[MongoDB] Disconnecting...");
			this.client.close();
			this.connected = false;
			Hub.getInstance().getLogger().info("[MongoDB] Successfully disconnected.");
		}
	}
}

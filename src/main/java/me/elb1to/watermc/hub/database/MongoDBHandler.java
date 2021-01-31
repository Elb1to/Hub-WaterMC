package me.elb1to.watermc.hub.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.utils.CC;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 30/01/2020 @ 19:28
 */
public class MongoDBHandler implements Database {

    private MongoClient client;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> playerData;
    private final Hub plugin = Hub.getInstance();

    @Override
    public Document loadData(UUID uuid, String name) {
        return playerData.find(Filters.eq("uuid", uuid.toString())).first();
    }

    @Override
    public void saveData(Document document) {
        this.playerData.replaceOne(Filters.eq("uuid", document.getString("uuid")), document, (new UpdateOptions()).upsert(true));
    }

    @Override
    public void connect(DatabaseCredentials credentials) {
        try {
            plugin.getLogger().info("Connecting to MongoDB...");
            if (credentials.isAuth()) {
                MongoCredential mongoCredential = MongoCredential.createCredential(credentials.getUser(), credentials.getAuthDatabase(), credentials.getPassword().toCharArray());
                this.client = new MongoClient(new ServerAddress(credentials.getHost(), credentials.getPort()), Collections.singletonList(mongoCredential));
            } else {
                this.client = new MongoClient(new ServerAddress(credentials.getHost(), credentials.getPort()));
            }

            Bukkit.getConsoleSender().sendMessage(CC.translate("&8[&bHub&8] &aSuccessfully connected to MongoDB."));
            this.mongoDatabase = this.client.getDatabase(credentials.getDatabase());
            this.playerData = this.mongoDatabase.getCollection("Hub-PlayerData");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cDisabling &bHub &cbecause an error occurred while trying to connect to &aMongoDB."));
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public void close() {
        if (this.client != null) {
            plugin.getLogger().info("[MongoDB] Disconnecting...");
            this.client.close();
            plugin.getLogger().info("[MongoDB] Successfully disconnected.");
        }
    }
}

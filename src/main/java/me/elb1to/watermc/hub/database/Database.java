package me.elb1to.watermc.hub.database;

import org.bson.Document;

import java.util.UUID;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 30/01/2020 @ 19:28
 */
public interface Database {
	Document loadData(UUID uuid, String name);

	void saveData(Document document);

	void connect(DatabaseCredentials credentials);

	void close();
}

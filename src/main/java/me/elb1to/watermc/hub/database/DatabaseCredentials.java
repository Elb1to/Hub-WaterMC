package me.elb1to.watermc.hub.database;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 30/01/2020 @ 19:28
 */
@Getter @Setter
public class DatabaseCredentials {

	private String host, database, user, password, authDatabase, table;
	private int port;
	private boolean auth;

	public DatabaseCredentials(String host, int port, String database, boolean auth, String user, String password, String authDatabase) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.auth = auth;
		this.user = user;
		this.password = password;
		this.authDatabase = authDatabase;
	}
}

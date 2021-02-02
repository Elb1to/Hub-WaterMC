package me.elb1to.watermc.hub;

import lombok.Getter;
import lombok.NonNull;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.config.FileConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 01/02/2020 @ 13:30
 */
@Getter
public enum Lang {

	LOBBY_SCOREBOARD_TITLE(Type.STRING, Config.SCOREBOARD, "SCOREBOARDS.LOBBY.TITLE"),
	LOBBY_SCOREBOARD_LINES(Type.ARRAY, Config.SCOREBOARD, "SCOREBOARDS.LOBBY.LINES"),
	QUEUE_SCOREBOARD_TITLE(Type.STRING, Config.SCOREBOARD, "SCOREBOARDS.QUEUE.TITLE"),
	QUEUE_SCOREBOARD_LINES(Type.ARRAY, Config.SCOREBOARD, "SCOREBOARDS.QUEUE.LINES"),
	ALLOW_MOBS_SPAWN(Type.BOOLEAN, Config.SETTINGS, "SETTINGS.SERVER.ALLOW-MOBS-SPAWN"),
	ALLOW_ANIMALS_SPAWN(Type.BOOLEAN, Config.SETTINGS, "ALLOW-ANIMALS-SPAWN"),
	NO_COSMETIC_PERMISSION(Type.STRING, Config.MESSAGES, "PLAYER.NO-COSMETIC-PERMISSION");

	private final Hub plugin = Hub.getInstance();
	public Type type;
	public Config config;
	private final String path;

	Lang(Type type, Config config, String path) {
		this.type = type;
		this.config = config;
		this.path = path;
	}

	@NonNull
	private FileConfig getConfig() {
		FileConfig fileConfig;
		switch (this.config) {
			case QUEUES:
				fileConfig = plugin.getQueuesConfig();
				break;
			case SETTINGS:
				fileConfig = plugin.getSettingsConfig();
				break;
			case MESSAGES:
				fileConfig = plugin.getMessagesConfig();
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + this.config);
		}
		return fileConfig;
	}

	public String getString() {
		if (this.type != Type.STRING) return "Is a string :/ not " + this.type.name();
		String string = getConfig().getString(this.path);
		if (string != null) {
			return CC.translate(string);
		}
		return "No found: " + this.name();
	}

	public List<String> getStringList() {
		if (this.type != Type.ARRAY) {
			return Collections.singletonList("Is a string :/ not " + this.type.name());
		}
		List<String> list = new ArrayList<>();
		if (getConfig().getConfiguration().contains(this.path)) {
			getConfig().getStringList(this.path).forEach(text -> list.add(CC.translate(text)));
		} else {
			list.add("No Found:" + this.path);
		}

		return list;
	}

	public double getDouble() {
		if (this.type != Type.DOUBLE) {
			return 0;
		}
		return getConfig().getDouble(this.path);
	}

	public int getInt() {
		if (this.type != Type.INT) {
			return 0;
		}
		return getConfig().getInt(this.path);
	}

	public boolean getBoolean() {
		if (this.type != Type.BOOLEAN) {
			return false;
		}
		return getConfig().getBoolean(this.path);
	}

	public long getLong() {
		if (this.type != Type.DOUBLE) {
			return 0;
		}
		return getConfig().getLong(this.path);
	}

	@NonNull
	public Object get() {
		Object toReturn;
		switch (type) {
			case INT:
				toReturn = getInt();
				break;
			case LONG:
				toReturn = getLong();
				break;
			case ARRAY:
				toReturn = getStringList();
				break;
			case DOUBLE:
				toReturn = getDouble();
				break;
			case STRING:
				toReturn = getString();
				break;
			case BOOLEAN:
				toReturn = getBoolean();
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + type);
		}

		return toReturn;
	}

	private enum Type {
		STRING,
		ARRAY,
		DOUBLE,
		INT,
		BOOLEAN,
		LONG
	}

	private enum Config {
		MESSAGES,
		QUEUES,
		SCOREBOARD,
		SETTINGS
	}
}

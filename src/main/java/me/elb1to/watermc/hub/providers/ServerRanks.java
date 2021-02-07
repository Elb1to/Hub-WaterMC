package me.elb1to.watermc.hub.providers;

import lombok.Getter;

/**
 * Created by Elb1to
 * Project: Hub
 * Date: 2/6/2021 @ 11:52 PM
 */
@Getter
public enum ServerRanks {

	OWNER("&4❃&l", "owner"),
	CO_OWNER("&4✦&l", "co-owner"),
	DEVELOPER("&3❈&b&o", "developer"),
	PLAT_ADMIN("&c&o", "platadmin"),
	SR_ADMIN("&c", "sradmin"),
	ADMIN("&c", "admin"),
	JR_ADMIN("&a", "jradmin"),
	SR_MOD("&3&o", "srmod"),
	MOD_PLUS("&5", "mod+"),
	MOD("&5", "mod"),
	TRIAL_MOD("&3", "trialmod"),
	HELPER("&b", "helper"),

	PARTNER("&d&l", "partner"),
	FAMOUS("&d&o", "famous"),
	STREAMER("", "streamer"),
	YOUTUBER("&5★&d", "youtube"),
	MINI_YT("&d", "miniyt"),

	WATER("&3❃", "water"),
	KRAKEN_PLUS("&2❃", "kraken+"),
	KRAKEN("&2❃", "kraken"),
	POSEIDON("&b⚘&3", "poseidon"),
	APOLO("&3❋&b", "apolo"),
	TRITON("&6‡&e", "triton"),
	VERIFIED("&f", "verified"),
	USER("&7", "user"),
	DEFAULT("&7", "default");

	private final String nametag;
	private final String name;

	ServerRanks(String nametag, String name) {
		this.nametag = nametag;
		this.name = name;
	}
}

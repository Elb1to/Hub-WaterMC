package me.elb1to.watermc.hub.utils.config;

import me.elb1to.watermc.hub.utils.CC;

import java.util.HashMap;
import java.util.Map;

public class ConfigReplacement {

	private Map<Object, Object> replacements = new HashMap<>();
	private String message;

	public ConfigReplacement(String message) {
		this.message = message;
	}

	public Map<Object, Object> getReplacements() {
		return this.replacements;
	}

	public void setReplacements(Map<Object, Object> replacements) {
		this.replacements = replacements;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ConfigReplacement add(Object current, Object replacement) {
		this.replacements.put(current, replacement);
		return this;
	}

	public String toString() {
		this.replacements.keySet().forEach(current -> this.message = this.message.replace(String.valueOf(current), String.valueOf(this.replacements.get(current))));
		return CC.translate(this.message);
	}

	public String toString(boolean ignored) {
		this.replacements.keySet().forEach(current -> this.message = this.message.replace(String.valueOf(current), String.valueOf(this.replacements.get(current))));
		return this.message;
	}
}


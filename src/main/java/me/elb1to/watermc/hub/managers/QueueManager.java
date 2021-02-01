package me.elb1to.watermc.hub.managers;

import com.google.common.collect.Lists;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.impl.Queue;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 01/02/2020 @ 16:03
 */
public class QueueManager {

	private List<Queue> queues = Lists.newArrayList();

	public QueueManager() {
		Hub.getInstance().getQueuesConfig().getConfiguration().getConfigurationSection("QUEUE").getKeys(false).forEach(s -> queues.add(new Queue(s)));
	}

	public Queue getByString(String s) {
		return queues.stream().filter(queue -> queue.getBungeeName().equalsIgnoreCase(s) || queue.getServer().equalsIgnoreCase(s)).findFirst().orElse(null);
	}

	public boolean isQueueing(Player player) {
		return getPlayerQueue(player) != null;
	}

	public Queue getPlayerQueue(Player player) {
		return queues.stream().filter(queue -> queue.isQueueing(player)).findFirst().orElse(null);
	}
}

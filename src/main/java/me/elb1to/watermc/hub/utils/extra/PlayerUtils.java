package me.elb1to.watermc.hub.utils.extra;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 1/16/2021 @ 1:00 PM
 */
public class PlayerUtils implements Listener {

	private static final Map<Player, Long> LAST_MOVE = new HashMap<>();

	public static void sendPacket(Player player, Packet... packets) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		for (Packet packet : packets) {
			if (packet == null) {
				continue;
			}

			connection.sendPacket(packet);
		}
	}

	public static boolean timeElapsed(long from, long required) {
		return System.currentTimeMillis() - from > required;
	}

	public static boolean isMoving(Player player) {
		return !isGrounded(player) || !timeElapsed(LAST_MOVE.getOrDefault(player, Long.MAX_VALUE), 500);
	}

	public static boolean isGrounded(Entity ent) {
		if (!(ent instanceof Player)) {
			return ent.isOnGround();
		}

		AxisAlignedBB box = ((CraftEntity) ent).getHandle().getBoundingBox();
		Location bottom_corner_1 = new Location(ent.getWorld(), box.a, ent.getLocation().getY() - 0.1, box.c);
		Location bottom_corner_2 = new Location(ent.getWorld(), box.d, ent.getLocation().getY() - 0.1, box.f);

		for (Block b : BlockUtils.getInBoundingBox(bottom_corner_1, bottom_corner_2)) {
			if (BlockUtils.solid(b)) return true;
		}

		return false;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void setMoving(PlayerMoveEvent event) {
		Location from = event.getFrom(), to = event.getTo();

		if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
			return;
		}

		LAST_MOVE.put(event.getPlayer(), System.currentTimeMillis());
	}

	public boolean isGrounded(Entity ent, Location loc) {
		AxisAlignedBB box = ((CraftEntity) ent).getHandle().getBoundingBox();
		Location bottom_corner_1 = new Location(ent.getWorld(), box.a, loc.getY() - 0.1, box.c);
		Location bottom_corner_2 = new Location(ent.getWorld(), box.d, loc.getY() - 0.1, box.f);

		for (Block b : BlockUtils.getInBoundingBox(bottom_corner_1, bottom_corner_2)) {
			if (BlockUtils.solid(b)) return true;
		}

		return false;
	}
}

package me.elb1to.watermc.hub.impl;

import com.google.common.collect.Lists;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.Setter;
import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.user.HubPlayer;
import me.elb1to.watermc.hub.user.PlayerState;
import me.elb1to.watermc.hub.utils.CC;
import me.elb1to.watermc.hub.utils.config.ConfigCursor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Elb1to
 * Project: Hub [WaterMC]
 * Date: 01/02/2021 @ 10:03
 */
@Getter
public class Queue {

    private final Hub plugin = Hub.getInstance();

    private final String server;
    private final String bungeeName;
    private final String restrictionBypass;
    private final String bypassPermission;
    private final int defaultPriority;
    @Setter private int limit;

    private List<UUID> uuids;

    @Setter private boolean paused;

    public Queue(String name) {
        ConfigCursor config = new ConfigCursor(this.plugin.getQueuesConfig(), "QUEUE." + name);

        this.server = config.getString("SERVER");
        this.bungeeName = config.getString("BUNGEE-SERVER");
        this.restrictionBypass = config.getString("BYPASS-RESTRICTION-PERMISSION");
        this.bypassPermission = config.getString("BYPASS-QUEUE-PERMISSION");
        this.defaultPriority = config.getInt("DEFAULT-PRIORITY");
        this.limit = config.getInt("LIMIT");

        this.uuids = Lists.newArrayList();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!paused && !getPlayers().isEmpty()) {
                    getPlayers().forEach(player -> {
                        HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
                        if (getPlayers().get(0) == player && hubPlayer != null) {
                            send(player);
                            remove(player);
                        } else {
                            Hub.getInstance().getMessagesConfig().getConfiguration().getStringList("QUEUE.QUEUEING-INFO").forEach(s -> {
                                s = s.replace("<QUEUE-NAME>", server);
                                s = s.replace("<QUEUE-BUNGEE-NAME>", bungeeName);
                                s = s.replace("<QUEUE-POSITION>", String.valueOf(getPlayers().indexOf(player) + 1));
                                s = s.replace("<QUEUE-SIZE>", String.valueOf(uuids.size()));
                                player.sendMessage(CC.translate(s));
                            });
                        }
                    });
                }
            }
        }.runTaskTimer(Hub.getInstance(), 20L, config.getInt("SEND-EVERY") * 20L);
    }

    public List<Player> getPlayers() {
        return uuids.stream().map(Bukkit::getPlayer).collect(Collectors.toList());
    }

    public void add(Player player) {
        if (player.hasPermission(bypassPermission) || getPriority(player) == 0) {
            send(player);
            remove(player);
            return;
        }

        if (uuids.size() >= limit && !player.hasPermission(restrictionBypass)) {
            player.sendMessage(CC.translate(this.plugin.getMessagesConfig().getConfiguration().getString("QUEUE.FULL").replace("<QUEUE-NAME>", server).replace("<QUEUE-BUNGEE-NAME>", bungeeName)));
            return;
        }

        uuids.add(player.getUniqueId());

        reload();

        HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
        hubPlayer.setState(PlayerState.QUEUE);
        player.sendMessage(CC.translate(this.plugin.getMessagesConfig().getConfiguration().getString("QUEUE.JOINED").replace("<QUEUE-NAME>", server).replace("<QUEUE-BUNGEE-NAME>", bungeeName)));
    }

    public void remove(Player player) {
        uuids.remove(player.getUniqueId());
        reload();

        HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
        hubPlayer.setState(PlayerState.LOBBY);
        player.sendMessage(CC.translate(this.plugin.getMessagesConfig().getConfiguration().getString("QUEUE.LEFT").replace("<QUEUE-NAME>", server).replace("<QUEUE-BUNGEE-NAME>", bungeeName)));
    }

    private void send(Player player) {
        HubPlayer hubPlayer = HubPlayer.getByUuid(player.getUniqueId());
        hubPlayer.setState(PlayerState.LOBBY);
        player.sendMessage(CC.translate(this.plugin.getMessagesConfig().getConfiguration().getString("QUEUE.BEING-SENT").replace("<QUEUE-NAME>", server).replace("<QUEUE-BUNGEE-NAME>", bungeeName)));

        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ConnectOther");
        output.writeUTF(player.getName());
        output.writeUTF(bungeeName);
        player.sendPluginMessage(this.plugin, "BungeeCord", output.toByteArray());
    }

    private void reload() {
        getPlayers().forEach(player -> getPlayers().forEach(other -> {
            int pos = uuids.indexOf(other.getUniqueId());
            if (player != other && getPriority(player) < getPriority(other)) {
                Collections.swap(uuids, pos, uuids.size() - 1);
            }
        }));
    }

    public int getPosition(Player player) {
        return (!isQueueing(player) ? 0 : uuids.indexOf(player.getUniqueId()) + 1);
    }

    public int getPriority(Player player) {
        AtomicInteger priority = new AtomicInteger(defaultPriority);

        player.getEffectivePermissions().forEach(attachment -> {
            if (attachment.getPermission() != null && attachment.getPermission().startsWith("queue.priority.")) {
                try {
                    priority.set(Integer.parseInt(attachment.getPermission().replace("queue.priority.", "")));
                } catch (Exception ignored) {

                }
            }
        });

        return priority.get();
    }

    public boolean isQueueing(Player player) {
        return uuids.contains(player.getUniqueId());
    }
}

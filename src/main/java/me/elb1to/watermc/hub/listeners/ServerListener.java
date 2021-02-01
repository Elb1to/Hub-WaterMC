package me.elb1to.watermc.hub.listeners;

import me.elb1to.watermc.hub.Hub;
import me.elb1to.watermc.hub.Lang;
import me.elb1to.watermc.hub.utils.config.ConfigCursor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import static org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.EGG;
import static org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 11/09/2020 @ 16:03
 */
public class ServerListener implements Listener {

    private final ConfigCursor config = new ConfigCursor(Hub.getInstance().getSettingsConfig(), "SETTINGS.SERVER");

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(!config.getBoolean("ALLOW-WEATHER-CHANGE"));
    }

    @EventHandler
    public void onThunder(ThunderChangeEvent event) {
        event.setCancelled(!config.getBoolean("ALLOW-THUNDERS-SPAWN"));
    }

    @EventHandler
    public void onSlimeSpawm(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Slime) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMobsSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Monster)) {
            return;
        }
        if (event.getSpawnReason() == SPAWNER_EGG || event.getSpawnReason() == EGG) {
            return;
        }
        if (Lang.ALLOW_MOBS_SPAWN.getBoolean()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onAnimalSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Animals)) {
            return;
        }
        if (event.getSpawnReason() == SPAWNER_EGG || event.getSpawnReason() == EGG) {
            return;
        }
        if (Lang.ALLOW_ANIMALS_SPAWN.getBoolean()) {
            return;
        }

        event.setCancelled(true);
    }
}

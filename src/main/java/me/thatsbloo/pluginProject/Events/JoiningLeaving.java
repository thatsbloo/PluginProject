package me.thatsbloo.pluginProject.Events;

import me.thatsbloo.pluginProject.PluginProject;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class JoiningLeaving implements Listener {

    private final JavaPlugin plugin;

    public JoiningLeaving(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        event.setJoinMessage("§6Welcome, §r" + p.getDisplayName() + "§6!");

        if (!loadPlayerData(p)) {
            PluginProject.log.info("Initializing data instead.");
            PersistentDataContainer data = p.getPersistentDataContainer();
            data.set(PluginProject.keyGold, PersistentDataType.INTEGER, 0);
            data.set(PluginProject.keyBounty, PersistentDataType.INTEGER, 0);
            data.set(PluginProject.keyKillstreak, PersistentDataType.INTEGER, 0);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        event.setQuitMessage("§6Bye, §r" + p.getDisplayName() + "§6!");

        savePlayerData(p);
    }

    public void savePlayerData(Player player) {
        File dataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
        String uuid = player.getUniqueId().toString();
        PersistentDataContainer pdc = player.getPersistentDataContainer();

        int gold = pdc.getOrDefault(PluginProject.keyGold, PersistentDataType.INTEGER, 0);
        int bounty = pdc.getOrDefault(PluginProject.keyBounty, PersistentDataType.INTEGER, 0);
        int killstreak = pdc.getOrDefault(PluginProject.keyKillstreak, PersistentDataType.INTEGER, 0);

        config.set(uuid + ".gold", gold);
        config.set(uuid + ".bounty", bounty);
        config.set(uuid + ".killstreak", killstreak);

        try {
            config.save(dataFile);
            PluginProject.log.info("Saved Player data.");
        } catch (IOException e) {
            e.printStackTrace();
            PluginProject.log.warning("Failed to save player data. {gold: " + gold + ", bounty: " + bounty + "}" );
        }
    }

    public boolean loadPlayerData(Player player) {
        File dataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
        String uuid = player.getUniqueId().toString();

        if (config.contains(uuid)) {
            PersistentDataContainer pdc = player.getPersistentDataContainer();
            pdc.set(PluginProject.keyGold, PersistentDataType.INTEGER, config.getInt(uuid + ".gold", 0));
            pdc.set(PluginProject.keyBounty, PersistentDataType.INTEGER, config.getInt(uuid + ".bounty", 0));
            pdc.set(PluginProject.keyKillstreak, PersistentDataType.INTEGER, config.getInt(uuid + ".killstreak", 0));

            PluginProject.log.info("Loaded player data.");
            return true;
        } else {
            PluginProject.log.info("Failed to load player data.");
            return false;
        }
    }
}

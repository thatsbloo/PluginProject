package me.thatsbloo.pluginProject.Events;

import jdk.jfr.FlightRecorder;
import me.thatsbloo.pluginProject.PluginProject;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Killing implements Listener {

    private final PluginProject plugin;

    public Killing(PluginProject plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();

        Player killer = victim.getKiller();

        if (killer != null) {
            int[] goldvals = calculateGoldGained(victim, killer);
            killer.sendTitle("§4[§r☠§4] " + ChatColor.GRAY + victim.getDisplayName(), ChatColor.GOLD + "+" +  goldvals[0] + ChatColor.GRAY + " (" + goldvals[1] + " from streak)", 5, 20, 5);
            killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.8f);
            PersistentDataContainer pdc = killer.getPersistentDataContainer();
            int currgold = pdc.get(PluginProject.keyGold, PersistentDataType.INTEGER);
            pdc.set(PluginProject.keyGold, PersistentDataType.INTEGER, currgold + goldvals[0]);
            int currks = pdc.get(PluginProject.keyKillstreak, PersistentDataType.INTEGER);
            pdc.set(PluginProject.keyKillstreak, PersistentDataType.INTEGER, currks + goldvals[0]);
        }
        victim.getPersistentDataContainer().set(PluginProject.keyKillstreak, PersistentDataType.INTEGER, 0);
    }

    public int[] calculateGoldGained(Player victim, Player killer) {
        int goldperkill = plugin.getGoldPerKill();
        double basemult = plugin.getKillStreakMultiplier();
        int killstreak = killer.getPersistentDataContainer().getOrDefault(PluginProject.keyKillstreak, PersistentDataType.INTEGER, 0);
        double ksmult = 1 + (basemult * killstreak);

        int goldGained = (int) (goldperkill * ksmult);
        return new int[]{goldGained, goldGained - goldperkill};
    }

}

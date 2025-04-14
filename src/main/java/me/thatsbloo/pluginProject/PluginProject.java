package me.thatsbloo.pluginProject;

import me.thatsbloo.pluginProject.Commands.*;
import me.thatsbloo.pluginProject.Completers.*;
import me.thatsbloo.pluginProject.Events.*;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.N;

import java.util.logging.Logger;

public final class PluginProject extends JavaPlugin {

    public static Logger log;

    public static NamespacedKey keyGold;
    public static NamespacedKey keyBounty;
    public static NamespacedKey keyKillstreak;

    private int goldPerKill;
    private double killStreakMultiplier;
    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();

        //Create config file and default settings if not exist
        config.addDefault("gold_per_kill", 10);
        config.addDefault("killstreak_gold_multiplier", 0.1);
        config.options().copyDefaults(true);
        this.saveConfig();

        //Add the values to variables for easier access
        loadConfigSettings();

        log = getLogger();

        keyGold = new NamespacedKey(this, "player_gold");
        keyBounty = new NamespacedKey(this, "player_bounty");
        keyKillstreak = new NamespacedKey(this, "player_killstreak");

        //Register Events
        getServer().getPluginManager().registerEvents(new JoiningLeaving(this), this);
        getServer().getPluginManager().registerEvents(new Killing(this), this);

        //Register Commands
        this.getCommand("getgold").setExecutor(new getGold());
        this.getCommand("getbounty").setExecutor(new getBounty());
        this.getCommand("getkillstreak").setExecutor(new getKillstreak());
        this.getCommand("setgold").setExecutor(new setGold());
        this.getCommand("setbounty").setExecutor(new setBounty());

        //Register Tab Completers
        this.getCommand("setgold").setTabCompleter(new setGoldCompleter());
        this.getCommand("setbounty").setTabCompleter(new setBountyCompleter());
        this.getCommand("getgold").setTabCompleter(new getGoldCompleter());
        this.getCommand("getbounty").setTabCompleter(new getBountyCompleter());
        this.getCommand("getkillstreak").setTabCompleter(new getKillstreakCompleter());

        System.out.println("Yerrrr I'm awakee");
    }

    private void loadConfigSettings() {
        goldPerKill = getConfig().getInt("gold_per_kill");
        killStreakMultiplier = getConfig().getDouble("killstreak_gold_multiplier");
    }

    public int getGoldPerKill() {
        return goldPerKill;
    }

    public double getKillStreakMultiplier() {
        return killStreakMultiplier;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("byebye");
    }




}

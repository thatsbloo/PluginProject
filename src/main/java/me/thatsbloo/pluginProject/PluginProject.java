package me.thatsbloo.pluginProject;

import me.thatsbloo.pluginProject.Commands.*;
import me.thatsbloo.pluginProject.Completers.*;
import me.thatsbloo.pluginProject.Events.JoiningLeaving;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginProject extends JavaPlugin {

    public static NamespacedKey keyGold;
    public static NamespacedKey keyBounty;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        keyGold = new NamespacedKey(this, "player_gold");
        keyBounty = new NamespacedKey(this, "player_bounty");

        //Register Events
        getServer().getPluginManager().registerEvents(new JoiningLeaving(this), this);

        //Register Commands
        this.getCommand("getgold").setExecutor(new getGold());
        this.getCommand("getbounty").setExecutor(new getBounty());
        this.getCommand("setgold").setExecutor(new setGold());
        this.getCommand("setbounty").setExecutor(new setBounty());

        //Register Tab Completers
        this.getCommand("setgold").setTabCompleter(new setGoldCompleter());
        this.getCommand("setbounty").setTabCompleter(new setBountyCompleter());

        System.out.println("Yerrrr I'm awakee");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("byebye");
    }




}

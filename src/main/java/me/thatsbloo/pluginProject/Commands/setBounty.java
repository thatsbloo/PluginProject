package me.thatsbloo.pluginProject.Commands;

import me.thatsbloo.pluginProject.PluginProject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class setBounty implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length > 2 || args.length == 0) {
            return false;
        }
        String valueStr = args[0];

        int value;
        try {
            value = Integer.parseInt(valueStr);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "The value must be a valid number.");
            return false;
        }

        if (args.length == 1) {
            Player p = (Player) sender;

            PersistentDataContainer pdc = p.getPersistentDataContainer();
            pdc.set(PluginProject.keyBounty, PersistentDataType.INTEGER, value);
            p.sendMessage("§rBounty value set to: §l§4" + value);
        } else if (args.length == 2) {
            Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                sender.sendMessage(ChatColor.RED + "Player not found or not online.");
                return true;
            }
            PersistentDataContainer pdc = p.getPersistentDataContainer();
            pdc.set(PluginProject.keyBounty, PersistentDataType.INTEGER, value);
            p.sendMessage("§rBounty value of " + p.getDisplayName() + " set to: §l§4" + value);
        }


        return true;
    }
}


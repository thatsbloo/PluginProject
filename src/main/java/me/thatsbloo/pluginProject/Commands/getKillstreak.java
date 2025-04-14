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

public class getKillstreak implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                PersistentDataContainer pdc = p.getPersistentDataContainer();
                int gold = pdc.getOrDefault(PluginProject.keyKillstreak, PersistentDataType.INTEGER, 0);

                p.sendMessage("§rYour current killstreak is: §l§6" + gold);
            } else {
                sender.sendMessage("This command can only be used by players, if you wish to check out someone's killstreak do /getkillstreak <player>.");
            }
        } else if (args.length == 1) {
            Player p = Bukkit.getPlayer(args[0]);

            if (p == null) {
                sender.sendMessage(ChatColor.RED + "Player not found or not online.");
                return true;
            }

            PersistentDataContainer pdc = p.getPersistentDataContainer();
            int gold = pdc.getOrDefault(PluginProject.keyKillstreak, PersistentDataType.INTEGER, 0);

            p.sendMessage(p.getDisplayName() + "§r's current killstreak is: §l§6" + gold);

        }

        return true;
    }
}

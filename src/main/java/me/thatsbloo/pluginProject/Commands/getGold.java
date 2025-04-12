package me.thatsbloo.pluginProject.Commands;

import me.thatsbloo.pluginProject.PluginProject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class getGold implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            PersistentDataContainer pdc = p.getPersistentDataContainer();
            int gold = pdc.getOrDefault(PluginProject.keyGold, PersistentDataType.INTEGER, 0);

            p.sendMessage("§rYour current gold is: §l§6" + gold);
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}

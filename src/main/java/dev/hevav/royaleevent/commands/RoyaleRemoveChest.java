package dev.hevav.royaleevent.commands;

import dev.hevav.royaleevent.helpers.RoyaleHelper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoyaleRemoveChest implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Location location = ((Player) commandSender).getLocation();
            RoyaleHelper.removeChest(commandSender.getServer(), location);
            commandSender.sendMessage("Removed the chest from "+location.toString());
        }
        return true;
    }
}

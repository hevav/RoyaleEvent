package dev.hevav.royaleevent.commands;

import dev.hevav.royaleevent.helpers.RoyaleHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RoyaleStart implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            RoyaleHelper.initRoyale(commandSender.getServer(), ((Player) commandSender).getWorld());
            return true;
        }
        return false;
    }
}

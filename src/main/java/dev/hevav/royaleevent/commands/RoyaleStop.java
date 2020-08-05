package dev.hevav.royaleevent.commands;

import dev.hevav.royaleevent.helpers.RoyaleHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RoyaleStop implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        RoyaleHelper.stopRoyale(commandSender.getServer());
        return true;
    }
}

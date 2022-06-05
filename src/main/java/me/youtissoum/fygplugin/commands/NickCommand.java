package me.youtissoum.fygplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player p) {

            if(args.length == 1) {

                p.setDisplayName(args[0]);
                p.setPlayerListName(args[0]);

            } else return false;

        } else return false;

        return true;
    }
}

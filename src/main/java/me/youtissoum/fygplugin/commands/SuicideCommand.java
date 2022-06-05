package me.youtissoum.fygplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            p.setHealth(0);
            Bukkit.broadcastMessage(p.getDisplayName() + " decided to end their life");
        }

        return true;
    }
}

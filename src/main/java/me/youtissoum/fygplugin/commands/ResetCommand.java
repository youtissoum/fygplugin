package me.youtissoum.fygplugin.commands;

import me.youtissoum.fygplugin.Fygplugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            Bukkit.broadcastMessage(p.getDisplayName() + " decided to reset themselves");
            Fygplugin.getResetLocation().put(p, p.getLocation());
            p.setHealth(0);
        }

        return true;
    }
}

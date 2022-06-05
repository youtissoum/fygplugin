package me.youtissoum.fygplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class WitherSkeletonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            p.getWorld().spawnEntity(p.getLocation(), EntityType.WITHER_SKELETON);
        } else return false;

        return true;
    }
}

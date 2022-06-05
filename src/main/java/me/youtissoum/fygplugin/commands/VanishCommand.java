package me.youtissoum.fygplugin.commands;

import me.youtissoum.fygplugin.Fygplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    private Fygplugin plugin;

    public VanishCommand(Fygplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("on")) {

                    if(!Fygplugin.getVanishList().contains(p)) Fygplugin.getVanishList().add(p);

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendMessage(ChatColor.YELLOW + p.getDisplayName() + " left the game");

                        player.hidePlayer(plugin, p);
                    });

                } else if(args[0].equalsIgnoreCase("off")) {
                    if(Fygplugin.getVanishList().contains(p)) Fygplugin.getVanishList().remove(p);

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.sendMessage(ChatColor.YELLOW + p.getDisplayName() + " joined the game");

                        player.showPlayer(plugin, p);
                    });
                } else return false;
            } else return false;
        } else return false;

        return true;
    }
}

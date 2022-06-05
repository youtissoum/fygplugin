/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin.commands;

import me.youtissoum.fygplugin.Fygplugin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class MuteCommand implements CommandExecutor {
    private Fygplugin plugin;
    public MuteCommand(Fygplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 2) {
            return false;
        }

        System.out.println(args[0]);
        System.out.println(args[1]);

        try {
            Player target = Bukkit.getPlayer(args[0]);
            int muteValue = (args[1].equalsIgnoreCase("on")) ? 1 : 0;

            target.getPersistentDataContainer().set(new NamespacedKey(plugin, "muted"), PersistentDataType.INTEGER, muteValue);

            sender.sendMessage("Target was " + (muteValue == 0 ? "un" : "") + "muted successfully");
            target.sendMessage("You have been " + (muteValue == 0 ? "un" : "") + "muted by " + sender.getName());
            return true;
        } catch(Exception e) {
            sender.sendMessage("Invalid player : " + e.getMessage());
            return true;
        }
    }
}

/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin.commands;

import me.youtissoum.fygplugin.Files.LocksStorage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class LockCommand implements CommandExecutor {
    private Plugin plugin;
    public LockCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            Block targetBlock = p.getTargetBlock(null, 5);

            if(targetBlock.getState() instanceof Container) {
                if(args.length == 1 && args[0].equalsIgnoreCase("disable")) {
                    if(LocksStorage.get().getString("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ()) != null && LocksStorage.get().getString("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ()).equalsIgnoreCase(p.getUniqueId().toString())) {
                        LocksStorage.get().set("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ(), null);
                        LocksStorage.save();
                        p.sendMessage("Lock has been removed!");
                    } else p.sendMessage("You don't have permission to open this lock it is of ownership of : " + LocksStorage.get().getString("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ()));
                } else {
                    if (!LocksStorage.get().contains("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ())) {
                        LocksStorage.get().set("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ(), p.getUniqueId().toString());
                        LocksStorage.save();
                        p.sendMessage("Block has been locked! (You can do \"lockChest disable\" to unlock it)");
                    } else p.sendMessage("This container is already locked!");
                }
            } else p.sendMessage(targetBlock.getType().name() + " is not lockable");
        }

        return true;
    }
}

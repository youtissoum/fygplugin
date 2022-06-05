/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin.commands;

import me.youtissoum.fygplugin.Fygplugin;
import me.youtissoum.fygplugin.tasks.PlayerDisguiseManagerTask;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DisguiseCommand implements CommandExecutor {
    private Plugin plugin;
    public DisguiseCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("enable")) {
                    if (!Fygplugin.getDisguises().containsKey(p)) {
                        p.setInvisible(true);
                        p.setInvulnerable(true);

                        Entity disguise = p.getWorld().spawnEntity(p.getLocation(), EntityType.valueOf(args[1]));

                        ServerGamePacketListenerImpl listener = ((CraftPlayer) p).getHandle().connection;
                        listener.send(new ClientboundRemoveEntitiesPacket(disguise.getEntityId()));

                        Fygplugin.getDisguises().put(p, disguise);

                        PlayerDisguiseManagerTask playerDisguiseManagerTask = new PlayerDisguiseManagerTask(p, disguise);
                        playerDisguiseManagerTask.runTaskTimer(plugin, 0L, 1L);
                    } else p.sendMessage("You already have a disguise on");
                } else if(args[0].equalsIgnoreCase("disable")) {
                    if(Fygplugin.getDisguises().containsKey(p)) {
                        p.setInvisible(false);
                        p.setInvulnerable(false);

                        Entity disguise = Fygplugin.getDisguises().get(p);
                        disguise.remove();

                        Fygplugin.getDisguises().remove(p);
                    } else p.sendMessage("You don't even have a disguise on you dumbass");
                } else return false;
            } else return false;
        }

        return true;
    }
}

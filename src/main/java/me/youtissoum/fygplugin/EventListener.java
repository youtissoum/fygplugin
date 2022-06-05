/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin;

import me.youtissoum.fygplugin.Files.LocksStorage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class EventListener implements Listener {
    private Plugin plugin;
    public EventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        for (Player player : Fygplugin.getVanishList()) p.hidePlayer(plugin, player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(Fygplugin.getVoteKicks().containsKey(p)) Fygplugin.getVoteKicks().remove(p);

        Fygplugin.getVoteKicks().forEach((player, voters) -> {
            if(voters.contains(p)) voters.remove(p);
        });
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();

        if(data.get(new NamespacedKey(plugin, "isTeleportingToHome"), PersistentDataType.INTEGER) != null && data.get(new NamespacedKey(plugin, "isTeleportingToHome"), PersistentDataType.INTEGER) == 1) {
            data.set(new NamespacedKey(plugin, "isTeleportingToHome"), PersistentDataType.INTEGER, 0);
            p.sendMessage("Teleportation has been canceled due to you moving!");
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        if(Fygplugin.getResetLocation().containsKey(p)) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                p.teleport(Fygplugin.getResetLocation().get(p));
                Fygplugin.getResetLocation().remove(p);
            }, 1L);

        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block targetBlock = e.getClickedBlock();

        if(e.getHand() == EquipmentSlot.HAND) {
            if(targetBlock != null && targetBlock.getType() != Material.AIR && targetBlock.getState() instanceof Container) {
                if(LocksStorage.get().getString("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ()) != null && !LocksStorage.get().getString("world" + Objects.requireNonNull(targetBlock.getLocation().getWorld()).toString() + "x" + targetBlock.getLocation().getBlockX() + "y" + targetBlock.getLocation().getBlockY() + "z" + targetBlock.getLocation().getBlockZ()).equalsIgnoreCase(p.getUniqueId().toString())) {
                    e.setCancelled(true);

                    TextComponent no = new TextComponent("This Block has been locked");
                    no.setColor(ChatColor.RED);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, no);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if(Fygplugin.getDisguises().containsKey(p)) {
            p.setInvisible(false);
            p.setInvulnerable(false);

            Entity disguise = Fygplugin.getDisguises().get(p);
            disguise.remove();

            Fygplugin.getDisguises().remove(p);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();

        if(data.has(new NamespacedKey(plugin, "muted"), PersistentDataType.INTEGER)) {
            if(data.get(new NamespacedKey(plugin, "muted"), PersistentDataType.INTEGER) == 1) {
                e.setCancelled(true);
                p.sendMessage("§cERROR : You have been muted by an administrator");
            }
        }
    }
}

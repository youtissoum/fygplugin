package me.youtissoum.fygplugin.commands;

import me.youtissoum.fygplugin.Fygplugin;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class HomeCommand implements CommandExecutor {
    private Fygplugin plugin;
    public HomeCommand(Fygplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = ((Player) sender);
            PersistentDataContainer data = p.getPersistentDataContainer();

            if(label.equalsIgnoreCase("sethome")) {
                data.set(new NamespacedKey(plugin, "homeWorld"), PersistentDataType.STRING, p.getWorld().getName());
                data.set(new NamespacedKey(plugin, "homeX"), PersistentDataType.DOUBLE, p.getLocation().getX());
                data.set(new NamespacedKey(plugin, "homeY"), PersistentDataType.DOUBLE, p.getLocation().getY());
                data.set(new NamespacedKey(plugin, "homeZ"), PersistentDataType.DOUBLE, p.getLocation().getZ());

                p.sendMessage("Home has been set");
            }else if(label.equalsIgnoreCase("home")) {
                if(data.has(new NamespacedKey(plugin, "homeX"), PersistentDataType.DOUBLE)) {
                    p.sendMessage("You will be teleported in 5 seconds, do not move");
                    data.set(new NamespacedKey(plugin, "isTeleportingToHome"), PersistentDataType.INTEGER, 1);

                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            World homeWorld = Bukkit.getServer().getWorld(data.get(new NamespacedKey(plugin, "homeWorld"), PersistentDataType.STRING));
                            Double homeX = data.get(new NamespacedKey(plugin, "homeX"), PersistentDataType.DOUBLE);
                            Double homeY = data.get(new NamespacedKey(plugin, "homeY"), PersistentDataType.DOUBLE);
                            Double homeZ = data.get(new NamespacedKey(plugin, "homeZ"), PersistentDataType.DOUBLE);

                            if(data.get(new NamespacedKey(plugin, "isTeleportingToHome"), PersistentDataType.INTEGER) == 1) {
                                data.set(new NamespacedKey(plugin, "isTeleportingToHome"), PersistentDataType.INTEGER, 0);
                                p.teleport(new Location(homeWorld, homeX, homeY, homeZ));
                            }
                        }
                    }, 100L);
                } else p.sendMessage(ChatColor.RED + "You must set a home before teleporting to it");
            }
        } else return false;

        return true;
    }
}

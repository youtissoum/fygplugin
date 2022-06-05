package me.youtissoum.fygplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DurabilityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            ItemStack item = p.getInventory().getItemInMainHand();

            if(item != null) {
                ItemMeta meta = item.getItemMeta();

                if(meta instanceof Damageable damageable) {
                    damageable.setDamage(0);
                    item.setItemMeta(damageable);
                } else p.sendMessage("Y you holdin' air");
            } else p.sendMessage("You must be holding an item");
        } else return false;

        return true;
    }
}

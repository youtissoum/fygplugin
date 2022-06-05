package me.youtissoum.fygplugin.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            Location loc = p.getLocation();
            Double x = loc.getX();
            Double y = loc.getY();
            Double z = loc.getZ();

            TextComponent copy = new TextComponent("[COPY]");
            copy.setColor(ChatColor.AQUA);
            copy.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, roundTwoDigits(x) + " " + roundTwoDigits(y) + " " + roundTwoDigits(z)));

            TextComponent share = new TextComponent("[SHARE]");
            share.setColor(ChatColor.RED);
            share.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "My current location is x: " + roundTwoDigits(x) + " y: " + roundTwoDigits(y) + " z: " + roundTwoDigits(z)));
            share.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§4Warning: This will share your current location to everyone in the server !").create()));

            p.sendMessage("Your current coords are x: " + Math.round(x) + " y: " + Math.round(y) + " z: " + Math.round(z));
            p.spigot().sendMessage(copy, share);
        } else return false;

        return true;
    }

    private double roundTwoDigits(double value) {
        return (Math.round(value * 100d) / 100d);
    }
}

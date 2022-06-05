package me.youtissoum.fygplugin.commands;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class NoRainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = ((Player) sender); // Bukkit
            CraftPlayer craftPlayer = (CraftPlayer) p; // CraftBukkit
            ServerPlayer serverPlayer = craftPlayer.getHandle(); //NMS

            ServerGamePacketListenerImpl listener = serverPlayer.connection;

            ClientboundGameEventPacket packet = new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0F);
            listener.send(packet);

            serverPlayer.sendMessage(Component.nullToEmpty("Rain has been removed"), serverPlayer.getUUID());
        } else {
            return false;
        }

        return true;
    }
}

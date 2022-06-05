package me.youtissoum.fygplugin.commands;

import me.youtissoum.fygplugin.Fygplugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VoteKickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if(target != null) {
                    if(!Fygplugin.getVoteKicks().containsKey(target)) Fygplugin.getVoteKicks().put(target, new ArrayList<Player>());

                    if(!Fygplugin.getVoteKicks().get(target).contains(p)) {
                        Fygplugin.getVoteKicks().get(target).add(p);
                        Bukkit.broadcastMessage(p.getDisplayName() + " has voted to kick " + target.getDisplayName() + " (" + Fygplugin.getVoteKicks().get(target).size() + "/" + Bukkit.getOnlinePlayers().size() / 2 + ")");
                        if(Fygplugin.getVoteKicks().get(target).size() >= Bukkit.getOnlinePlayers().size() / 2) {
                            target.kickPlayer("You have been kicked by the server due to people voting to kick you");
                            Bukkit.broadcastMessage(target.getDisplayName() + " has been kicked from the server");
                        }
                    }
                } else p.sendMessage("You need to give me a valid player lol");
            } else return false;
        } else return false;

        return true;
    }
}

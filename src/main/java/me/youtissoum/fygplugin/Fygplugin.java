/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin;

import github.scarsz.discordsrv.DiscordSRV;
import me.youtissoum.Updater;
import me.youtissoum.fygplugin.Files.LocksStorage;
import me.youtissoum.fygplugin.commands.*;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Level;

public final class Fygplugin extends JavaPlugin {

    private static Map<Player, Location> resetLocation = new HashMap<>();
    private static Map<Player, ArrayList<Player>> voteKicks = new HashMap<>();
    private static ArrayList<Player> vanishList = new ArrayList<>();
    private static Map<Player, Entity> disguises = new HashMap<>();

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        LocksStorage.setup();
        LocksStorage.get().options().setHeader(Collections.singletonList("WARNING: This is important storage for a command, do not mess with this unless you KNOW what you are doing"));
        LocksStorage.get().options().copyDefaults(true);
        LocksStorage.save();

        Updater updater = new Updater(this, this.getFile(), "youtissoum", "fygplugin");

        getLogger().info("Plugin started !");
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getCommand("norain").setExecutor(new NoRainCommand());
        getCommand("fygcafebad").setExecutor(new KickCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("votekick").setExecutor(new VoteKickCommand());
        getCommand("restoreitemdur").setExecutor(new DurabilityCommand());
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("spawnwitherskeleton").setExecutor(new WitherSkeletonCommand());
        getCommand("coords").setExecutor(new CoordCommand());
        getCommand("lockChest").setExecutor(new LockCommand(this));
        getCommand("updatefygplugin").setExecutor(new UpdateCommand(updater));
        getCommand("disguise").setExecutor(new DisguiseCommand(this));
        getCommand("mute").setExecutor(new MuteCommand(this));

        getCommand("sethome").setExecutor(new HomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));

        getLogger().log(Level.INFO, getChangelog());
    }

    public String getChangelog() {
        String output = "";

        output += "\nFygplugin " + this.getDescription().getVersion() + " Changelog\n\n";
        output += "------------------------------\n\n";
        output += "+ Changed the updater to use github instead of dropbox\n\n";
        output += "------------------------------\n";

        return output;
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin stopped!");
    }

    public static ArrayList<Player> getVanishList() {
        return vanishList;
    }
    public static Map<Player, ArrayList<Player>> getVoteKicks() {
        return voteKicks;
    }
    public static Map<Player, Location> getResetLocation() {
        return resetLocation;
    }
    public static Map<Player, Entity> getDisguises() {
        return disguises;
    }
}

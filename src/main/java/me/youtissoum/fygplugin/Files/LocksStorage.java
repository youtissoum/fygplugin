/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin.Files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocksStorage {

    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Fygplugin").getDataFolder(), "LockStorage");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("An error occured : " + e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occured : " + e);
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}

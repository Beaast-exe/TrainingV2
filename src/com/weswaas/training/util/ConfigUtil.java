package com.weswaas.training.util;

import com.weswaas.training.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Weswas on 01/12/2016.
 */
public class ConfigUtil {

    public static Location getLocation(String name){

        FileConfiguration config = Main.getInstance().getConfig();

        Location loc;

        int x = config.getInt("locations."+name+".x");
        int y = config.getInt("locations."+name+".y");
        int z = config.getInt("locations."+name+".z");

        loc = new Location(Bukkit.getWorld("world"), (x+0.5), y, (z+0.5));

        return loc;
    }

    public static void registerConfig(){

        FileConfiguration config = Main.getInstance().getConfig();

        config.options().copyDefaults(true);
        Main.getInstance().saveConfig();

        registerLocation("spawn", 0, 100, 0);
        registerLocation("kit", 93, 87, 103);

        Main.getInstance().saveConfig();

    }

    public static void registerLocation(String name, int x, int y, int z){

        FileConfiguration config = Main.getInstance().getConfig();

        config.addDefault("locations."+name+".x", x);
        config.addDefault("locations."+name+".y", y);
        config.addDefault("locations."+name+".z", z);

        Main.getInstance().saveConfig();

    }

}

package com.weswaas.training.util;

import org.bukkit.Location;

/**
 * Created by Weswas on 01/12/2016.
 */
public class LocationUtil {

    public static Location spawnLocation;
    public static Location kitLocation;

    public static Location getSpawnLocation(){
        return spawnLocation;
    }

    public static Location getKitLocation(){
        return kitLocation;
    }

    public static void register(){

        spawnLocation = ConfigUtil.getLocation("spawn");
        kitLocation = ConfigUtil.getLocation("kit");

    }

}

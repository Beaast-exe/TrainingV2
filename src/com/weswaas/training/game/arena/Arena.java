package com.weswaas.training.game.arena;

import org.bukkit.Location;

/**
 * Created by Weswas on 04/12/2016.
 */
public class Arena {

    private String name;
    private String serial;
    private Location spawn1;
    private Location spawn2;
    private Location corner1;
    private Location corner2;
    private boolean playable = true;

    public Arena(String name, String serial, Location spawn1, Location spawn2, Location corner1, Location corner2){
        this.name = name;
        this.serial = serial;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public String getName(){
        return this.name;
    }

    public String getSerial(){
        return this.serial;
    }

    public Location getSpawn1(){
        return this.spawn1;
    }

    public Location getSpawn2(){
        return this.spawn2;
    }

    public void setPlayable(boolean playable){
        this.playable = playable;
    }

    public boolean isPlayable(){
        return playable;
    }

    public Location getCorner1(){
        return this.corner1;
    }

    public Location getCorner2(){
        return this.corner2;
    }

}

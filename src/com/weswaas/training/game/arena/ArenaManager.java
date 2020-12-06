package com.weswaas.training.game.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Weswas on 04/12/2016.
 */
public class ArenaManager {

    private ArrayList<Arena> arenas;

    public ArenaManager(){
        this.arenas = new ArrayList<>();
    }

    public Arena getArena(String name){
        for(Arena a : this.arenas){
            if(a.getName().equalsIgnoreCase(name)){
                return a;
            }
        }
        return null;
    }

    public ArrayList<Arena> getPlayableArenas(){
        ArrayList<Arena> pArenas = new ArrayList<>();
        for(Arena a : this.arenas){
            if(a.isPlayable()){
                pArenas.add(a);
            }
        }
        return pArenas;
    }

    public Arena getRandomPlayableArena(){
        ArrayList<Arena> are = getPlayableArenas();

        if(are == null || are.size() == 0){
            return null;
        }

        Random r = new Random();
        Arena a = are.get(r.nextInt(are.size()));

        return a;

    }

    public void registerArenas(){

        //PLAINS
        Location loc1 = new Location(Bukkit.getWorld("world"), 3.5, 69, -202.5).setDirection(new Vector(5, 0, -5));
        Location loc12 = new Location(Bukkit.getWorld("world"), 58.5, 70, -265.5).setDirection(new Vector(-5, 0, 5));
        Location corner1 = new Location(Bukkit.getWorld("world"), 0, 89, -200);
        Location corner12 = new Location(Bukkit.getWorld("world"), 61, 63, -269);

        Location loc2 = new Location(Bukkit.getWorld("world"), 203.5, 69, -202.5).setDirection(new Vector(5, 0, -5));
        Location loc22 = new Location(Bukkit.getWorld("world"), 258.5, 70, -265.5).setDirection(new Vector(-5, 0, 5));
        Location corner2 = new Location(Bukkit.getWorld("world"), 200, 89, -200);
        Location corner22 = new Location(Bukkit.getWorld("world"), 261, 63, -269);

        Location loc3 = new Location(Bukkit.getWorld("world"), 403.5, 69, -202.5).setDirection(new Vector(5, 0, -5));
        Location loc32 = new Location(Bukkit.getWorld("world"), 458.5, 70, -265.5).setDirection(new Vector(-5, 0, 5));
        Location corner3 = new Location(Bukkit.getWorld("world"), 400, 89, -200);
        Location corner32 = new Location(Bukkit.getWorld("world"), 461, 63, -269);

        //DESERT
        Location loc4 = new Location(Bukkit.getWorld("world"), 3.5, 73, -403.5).setDirection(new Vector(5, 0, -5));
        Location loc42 = new Location(Bukkit.getWorld("world"), 54.5, 70, -462.5).setDirection(new Vector(-5, 0, 5));
        Location corner4 = new Location(Bukkit.getWorld("world"), 0, 88, -401);
        Location corner42 = new Location(Bukkit.getWorld("world"), 61, 62, -470);

        Location loc5 = new Location(Bukkit.getWorld("world"), 203.5, 73, -403.5).setDirection(new Vector(5, 0, -5));
        Location loc52 = new Location(Bukkit.getWorld("world"), 254.5, 70, -462.5).setDirection(new Vector(-5, 0, 5));
        Location corner5 = new Location(Bukkit.getWorld("world"), 200, 88, -401);
        Location corner52 = new Location(Bukkit.getWorld("world"), 261, 62, -470);

        Location loc6 = new Location(Bukkit.getWorld("world"), 403.5, 73, -403.5).setDirection(new Vector(5, 0, -5));
        Location loc62 = new Location(Bukkit.getWorld("world"), 454.5, 70, -462.5).setDirection(new Vector(-5, 0, 5));
        Location corner6 = new Location(Bukkit.getWorld("world"), 400, 88, -401);
        Location corner62 = new Location(Bukkit.getWorld("world"), 461, 62, -470);

        Location loc7 = new Location(Bukkit.getWorld("world"), 603.5, 73, -403.5).setDirection(new Vector(5, 0, -5));
        Location loc72 = new Location(Bukkit.getWorld("world"), 654.5, 70, -462.5).setDirection(new Vector(-5, 0, 5));
        Location corner7 = new Location(Bukkit.getWorld("world"), 600, 88, -401);
        Location corner72 = new Location(Bukkit.getWorld("world"), 661, 62, -470);

        Location loc8 = new Location(Bukkit.getWorld("world"), 803.5, 73, -403.5).setDirection(new Vector(5, 0, -5));
        Location loc82 = new Location(Bukkit.getWorld("world"), 854.5, 70, -462.5).setDirection(new Vector(-5, 0, 5));
        Location corner8 = new Location(Bukkit.getWorld("world"), 800, 88, -401);
        Location corner82 = new Location(Bukkit.getWorld("world"), 861, 62, -470);

        Location loc9 = new Location(Bukkit.getWorld("world"), 1003.5, 73, -403.5).setDirection(new Vector(5, 0, -5));
        Location loc92 = new Location(Bukkit.getWorld("world"), 1054.5, 70, -462.5).setDirection(new Vector(-5, 0, 5));
        Location corner9 = new Location(Bukkit.getWorld("world"), 1000, 88, -401);
        Location corner92 = new Location(Bukkit.getWorld("world"), 1061, 62, -470);

        //GLACE
        Location loc10 = new Location(Bukkit.getWorld("world"), 58.5, 68, -665.5).setDirection(new Vector(5, 0, -5));
        Location loc102 = new Location(Bukkit.getWorld("world"), 3.5, 70, -602.5).setDirection(new Vector(-5, 0, 5));
        Location corner10 = new Location(Bukkit.getWorld("world"), 0, 87, -600);
        Location corner102 = new Location(Bukkit.getWorld("world"), 61, 63, -669);

        Location loc111 = new Location(Bukkit.getWorld("world"), 258.5, 68, -665.5).setDirection(new Vector(5, 0, -5));
        Location loc112 = new Location(Bukkit.getWorld("world"), 203.5, 70, -602.5).setDirection(new Vector(-5, 0, 5));
        Location corner111 = new Location(Bukkit.getWorld("world"), 200, 87, -600);
        Location corner112 = new Location(Bukkit.getWorld("world"), 261, 63, -669);

        Location loc121 = new Location(Bukkit.getWorld("world"), 458.5, 68, -665.5).setDirection(new Vector(5, 0, -5));
        Location loc122 = new Location(Bukkit.getWorld("world"), 403.5, 70, -602.5).setDirection(new Vector(-5, 0, 5));
        Location corner121 = new Location(Bukkit.getWorld("world"), 400, 87, -600);
        Location corner122 = new Location(Bukkit.getWorld("world"), 461, 63, -669);

        Location loc131 = new Location(Bukkit.getWorld("world"), 658.5, 68, -665.5).setDirection(new Vector(5, 0, -5));
        Location loc132 = new Location(Bukkit.getWorld("world"), 603.5, 70, -602.5).setDirection(new Vector(-5, 0, 5));
        Location corner131 = new Location(Bukkit.getWorld("world"), 600, 87, -600);
        Location corner132 = new Location(Bukkit.getWorld("world"), 661, 63, -669);

        Location loc141 = new Location(Bukkit.getWorld("world"), 858.5, 68, -665.5).setDirection(new Vector(5, 0, -5));
        Location loc142 = new Location(Bukkit.getWorld("world"), 803.5, 70, -602.5).setDirection(new Vector(-5, 0, 5));
        Location corner141 = new Location(Bukkit.getWorld("world"), 800, 87, -600);
        Location corner142 = new Location(Bukkit.getWorld("world"), 861, 63, -669);

        //NETHER

        Location loc15 = new Location(Bukkit.getWorld("world"), 30.5, 69, -803.5).setDirection(new Vector(5, 0, -5));
        Location loc152 = new Location(Bukkit.getWorld("world"), 30.5, 69, -865.5).setDirection(new Vector(-5, 0, 5));
        Location corner15 = new Location(Bukkit.getWorld("world"), 0, 87, -800);
        Location corner152 = new Location(Bukkit.getWorld("world"), 61, 63, -869);

        Location loc16 = new Location(Bukkit.getWorld("world"), 230.5, 69, -803.5).setDirection(new Vector(5, 0, -5));
        Location loc162 = new Location(Bukkit.getWorld("world"), 230.5, 69, -865.5).setDirection(new Vector(-5, 0, 5));
        Location corner16 = new Location(Bukkit.getWorld("world"), 200, 87, -800);
        Location corner162 = new Location(Bukkit.getWorld("world"), 261, 63, -869);

        Location loc17 = new Location(Bukkit.getWorld("world"), 430.5, 69, -803.5).setDirection(new Vector(5, 0, -5));
        Location loc172 = new Location(Bukkit.getWorld("world"), 430.5, 69, -865.5).setDirection(new Vector(-5, 0, 5));
        Location corner17 = new Location(Bukkit.getWorld("world"), 400, 87, -800);
        Location corner172 = new Location(Bukkit.getWorld("world"), 461, 63, -869);

        Location loc18 = new Location(Bukkit.getWorld("world"), 630.5, 69, -803.5).setDirection(new Vector(5, 0, -5));
        Location loc182 = new Location(Bukkit.getWorld("world"), 630.5, 69, -865.5).setDirection(new Vector(-5, 0, 5));
        Location corner18 = new Location(Bukkit.getWorld("world"), 600, 87, -800);
        Location corner182 = new Location(Bukkit.getWorld("world"), 661, 63, -869);

        Location loc19 = new Location(Bukkit.getWorld("world"), 830.5, 69, -803.5).setDirection(new Vector(5, 0, -5));
        Location loc192 = new Location(Bukkit.getWorld("world"), 830.5, 69, -865.5).setDirection(new Vector(-5, 0, 5));
        Location corner19 = new Location(Bukkit.getWorld("world"), 800, 87, -800);
        Location corner192 = new Location(Bukkit.getWorld("world"), 861, 63, -869);

        arenas.add(new Arena("Plains", "plain001", loc1, loc12, corner1, corner12));
        arenas.add(new Arena("Plains", "plain002", loc2, loc22, corner2, corner22));
        arenas.add(new Arena("Plains", "plain003", loc3, loc32, corner3, corner32));

        arenas.add(new Arena("Desert", "desert001", loc4, loc42, corner4, corner42));
        arenas.add(new Arena("Desert", "desert002", loc5, loc52, corner5, corner52));
        arenas.add(new Arena("Desert", "desert003", loc6, loc62, corner6, corner62));
        arenas.add(new Arena("Desert", "desert004", loc7, loc72, corner7, corner72));
        arenas.add(new Arena("Desert", "desert005", loc8, loc82, corner8, corner82));
        arenas.add(new Arena("Desert", "desert006", loc9, loc92, corner9, corner92));

        arenas.add(new Arena("Glace", "glace001", loc10, loc102, corner10, corner102));
        arenas.add(new Arena("Glace", "glace002", loc111, loc112, corner111, corner112));
        arenas.add(new Arena("Glace", "glace003", loc121, loc122, corner121, corner122));
        arenas.add(new Arena("Glace", "glace004", loc131, loc132, corner131, corner132));
        arenas.add(new Arena("Glace", "glace005", loc141, loc142, corner141, corner142));

        arenas.add(new Arena("Nether", "nether001", loc16, loc162, corner16, corner162));
        arenas.add(new Arena("Nether", "nether002", loc17, loc172, corner17, corner172));
        arenas.add(new Arena("Nether", "nether003", loc18, loc182, corner18, corner182));
        arenas.add(new Arena("Nether", "nether004", loc19, loc192, corner19, corner192));

    }

}

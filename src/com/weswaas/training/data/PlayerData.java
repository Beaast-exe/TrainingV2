package com.weswaas.training.data;

import com.weswaas.training.Main;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.util.InventoryUtil;
import com.weswaas.training.util.PlayerInv;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Weswas on 25/12/2016.
 */
public class PlayerData {

    private HashMap<Ladder, Integer> ratings;
    private HashMap<Ladder, PlayerInv> kits;
    private int rankeds = 25;
    private Player p;

    public PlayerData(Player p){
        this.p = p;
        this.ratings = new HashMap<>();
        this.kits = new HashMap<>();
        if(p.hasPermission("training.ranked.unlimited")){
            this.rankeds = 69;
        }
    }

    public void setKit(Ladder ladder, PlayerInv inv, boolean write){
        this.kits.put(ladder, inv);
        if(write){
            HashMap<String, Object> map = new HashMap<>();
            map.put(ladder.getName().toLowerCase(), InventoryUtil.playerInventoryToString(inv));
            Main.getInstance().getSQL().update("kits", map, "uuid", p.getUniqueId().toString());
        }
    }

    public int getRating(Ladder ladder){
        return this.ratings.get(ladder);
    }

    public void setRating(Ladder ladder, int rating, boolean write){
        this.ratings.put(ladder, rating);
        if(write){
            HashMap<String, Object> map = new HashMap<>();
            map.put(ladder.getName().toLowerCase(), rating);
            Main.getInstance().getSQL().update("datas", map, "uuid", p.getUniqueId().toString());
        }
    }

    public void saveAll(){

        HashMap<String, Object> map = new HashMap<>();

        for(Ladder ladder : this.ratings.keySet()){
            map.put(ladder.getName().toLowerCase(), this.ratings.get(ladder));
        }

        map.put("rankeds", this.rankeds);
        map.put("name", p.getName());
        map.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        if(map.size() == 0){
            return;
        }

        Main.getInstance().getSQL().update("datas", map, "uuid", p.getUniqueId().toString());

    }

    public boolean canRanked(){
        return this.rankeds > 0;
    }

    public void setRankeds(int rankeds){
        this.rankeds = rankeds;
    }

    public int getRankedsRemain(){
        return this.rankeds;
    }

    public Player getPlayer(){
        return this.p;
    }

    public HashMap<Ladder, PlayerInv> getKits(){
        return this.kits;
    }

    public HashMap<Ladder, Integer> getRatings(){
        return this.ratings;
    }

}

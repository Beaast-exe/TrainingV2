package com.weswaas.training.data;

import com.weswaas.training.Main;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.util.InventoryUtil;
import com.weswaas.training.util.PlayerInv;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Weswas on 25/12/2016.
 */
public class PlayerDataManager implements Listener{

    public HashMap<Player, PlayerData> datas;
    public HashMap<Ladder, HashMap<String, Integer>> allElos = new HashMap<>();

    public PlayerDataManager(){
        this.datas = new HashMap<>();
        for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
            this.datas.put(pls, new PlayerData(pls));
        }
    }

    public void loadPlayerInfos(Player p){
        PlayerData data = this.datas.get(p);

        String uuid = p.getUniqueId().toString();

        Database base = Main.getInstance().getSQL();
        if(base.contains("datas", "uuid", "uuid", uuid)){
            for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
                if(base.contains("datas", ladder.getName().toLowerCase(), "uuid", uuid)){
                    data.setRating(ladder, base.getInteger("datas", ladder.getName().toLowerCase(), "uuid", uuid), false);
                }else{
                    data.setRating(ladder, 1400, false);
                }

            }
            int rankeds = base.getInteger("datas", "rankeds", "uuid", uuid);
            if(rankeds == 0){
                //RANKEDS RESETTING

                if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) != base.getInteger("datas", "day", "uuid", uuid)){
                    data.setRankeds(25);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    base.insert("datas", map);
                }else{
                    data.setRankeds(0);
                }
            }else{
                data.setRankeds(rankeds);
            }
        }else{
            HashMap<String, Object> map = new HashMap<>();
            map.put("uuid", uuid);
            map.put("name", p.getName());
            map.put("rankeds", p.hasPermission("training.ranked.unlimited") ? "69" : "20");
            map.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
                map.put(ladder.getName().toLowerCase(), 1400);
                data.setRating(ladder, 1400, false);
            }

            base.insert("datas", map);
        }

        if(base.contains("kits", "uuid", "uuid", uuid)){
            for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
                if(base.contains("kits", ladder.getName().toLowerCase(), "uuid", uuid)){
                    String s = base.getString("kits", ladder.getName().toLowerCase(), "uuid", uuid);
                    if(!s.equalsIgnoreCase(".") && s != null){
                        data.setKit(ladder, InventoryUtil.playerInventoryFromString(s), false);
                    }else{
                        data.setKit(ladder, null, false);
                    }
                }else{
                    data.setKit(ladder, null, false);
                }
            }
        }else{
            HashMap<String, Object> map = new HashMap<>();
            map.put("uuid", uuid);
            for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
                map.put(ladder.getName().toLowerCase(), ".");
                data.setKit(ladder, null, false);
            }

            base.insert("kits", map);
        }

    }

    public void setKit(Player p, Ladder ladder, PlayerInv inv){
        this.datas.get(p).setKit(ladder, inv, true);
    }

    public int getRating(Player p, Ladder ladder){
        return this.datas.get(p).getRating(ladder);
    }

    public PlayerInv getKit(Player p, Ladder ladder){
        return this.datas.get(p).getKits().get(ladder);
    }

    public void updateRating(Player p, Ladder ladder, int change, boolean isToAdd){
        PlayerData data = this.datas.get(p);
        int rating = data.getRating(ladder);
        if(isToAdd){
            data.setRating(ladder, rating + change, true);
        }else{
            data.setRating(ladder, rating - change, true);
        }

        this.allElos.get(ladder).remove(p.getUniqueId().toString());
        this.allElos.get(ladder).put(p.getUniqueId().toString(), data.getRating(ladder));

    }

    public void decreaseRankeds(Player p){
        PlayerData data = getPlayerData(p);
        int remain = data.getRankedsRemain();
        if(remain > 0){
            data.setRankeds(remain - 1);
        }
    }

    public PlayerData getPlayerData(Player p){
        for(Player pls : this.datas.keySet()){
            if(pls == p){
                return this.datas.get(pls);
            }
        }
        return null;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        this.datas.put(e.getPlayer(), new PlayerData(e.getPlayer()));
        loadPlayerInfos(e.getPlayer());
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e){
        if(!this.datas.containsKey(e.getPlayer())){
            return;
        }
        new BukkitRunnable() {
            public void run() {
                ((PlayerData)PlayerDataManager.this.datas.get((Object)e.getPlayer())).saveAll();
                PlayerDataManager.this.datas.remove((Object)e.getPlayer());
            }
        }.runTaskLater(Main.getInstance(), 5);
    }

    public void cache(){

        Database base = Main.getInstance().getSQL();

        for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
            HashMap<String, Integer> map = base.getAllRatings(ladder);
            allElos.put(ladder, map);
        }

    }

}

package com.weswaas.training.game.match;

import com.weswaas.training.Main;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Weswas on 25/12/2016.
 */
public class Queue implements Listener{

    private Ladder ladder;
    private boolean ranked;
    private ArrayList<Player> queue;
    private HashMap<Player, Player> match;

    public Queue(Ladder ladder, boolean ranked){
        this.ladder = ladder;
        this.ranked = ranked;
        queue = new ArrayList<>();
        match = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public void addToQueue(final Player p){

        this.queue.add(p);
        p.getInventory().clear();
        p.getInventory().setItem(0, new ItemBuilder(Material.PAPER).name("§3Currently in queue for §8» §b" + ladder.getName()).build());
        p.getInventory().setItem(8, new ItemBuilder(Material.REDSTONE).name("§cLeave queue").build());
        p.sendMessage(Main.PREFIX + "You joined §b" + (ranked ? "ranked" : "unranked") + " " + ladder.getName() + " §3queue");
        new BukkitRunnable() {
            public void run() {
                if(!Queue.this.queue.contains(p)){
                    this.cancel();
                    return;
                }

                for(Player pls : Queue.this.queue){
                    if(pls != p){
                        this.cancel();
                        Queue.this.queue.remove(pls);
                        Queue.this.queue.remove(p);
                        Queue.this.match.put(p, pls);
                        startMatch(p, pls);
                        return;
                    }
                }

            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

    private void startMatch(Player p1, Player p2){

        Main.getInstance().getMatchManager().startNewMatch(p1, p2, this.ladder, this.ranked);
        this.match.remove(p1);

    }

    private void removeFromQueue(Player p){
        this.queue.remove(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        if(!isInQueue(e.getPlayer())){
            return;
        }

        this.queue.remove(e.getPlayer());

    }

    @EventHandler
    public void on(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName() &&
                e.getItem().getType() == Material.REDSTONE && e.getItem().getItemMeta().getDisplayName().contains("Leave queue") &&
                this.queue.contains(p)){
            this.queue.remove(p);
            Main.getInstance().getKitManager().getKit("Lobby").give(p);
            p.sendMessage(Main.PREFIX + "You left the queue");
        }
    }

    public boolean isInQueue(Player p){
        return this.queue.contains(p);
    }

    public Ladder getLadder(){
        return this.ladder;
    }

    public ArrayList<Player> getQueue(){
        return this.queue;
    }

    public boolean isRanked(){
        return this.ranked;
    }

}

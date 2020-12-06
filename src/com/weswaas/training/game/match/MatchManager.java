package com.weswaas.training.game.match;

import com.weswaas.training.Main;
import com.weswaas.training.game.arena.Arena;
import com.weswaas.training.game.arena.ArenaManager;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.game.ladder.LadderManager;
import com.weswaas.training.util.InventoryUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/**
 * Created by Weswas on 24/12/2016.
 */
public class MatchManager {

    private ArenaManager arena;
    private LadderManager ladder;

    private ArrayList<Match> currentMatches = new ArrayList<>();
    private ArrayList<Queue> queues = new ArrayList<>();

    public MatchManager(ArenaManager arena, LadderManager ladder){
        this.arena = arena;
        this.ladder = ladder;
        for(Ladder ladders : this.ladder.getLadders()){
            this.queues.add(new Queue(ladders, true));
            this.queues.add(new Queue(ladders, false));
        }
    }

    public void startNewMatch(Player p1, Player p2, Ladder ladder, boolean ranked){

        Arena a = arena.getRandomPlayableArena();
        if(a == null){
            for(Player pls  : new Player[]{p1, p2}){
                pls.sendMessage(Main.PREFIX + "§cOut of arenas, please try again");
                InventoryUtil.setLobbyItems(pls);
            }
            return;
        }

        Match match = new Match(ranked, a, ladder, p1, p2);
        match.start();
        currentMatches.add(match);
        for(Player pls : new Player[]{p1, p2}){
            Main.getInstance().getPlayerManager().setInMatch(pls);
            pls.playSound(pls.getLocation(), Sound.NOTE_PLING, 0.8f, 0.8f);
        }

    }

    public Queue getQueue(Ladder l, boolean ranked){
        for(Queue queue : this.queues){
            if(queue.getLadder() == l && queue.isRanked() == ranked){
                return queue;
            }
        }
        return null;
    }

    public boolean isInQueue(Player p){
        for(Queue queue : this.queues){
            if(queue.getQueue().contains(p)){
                return true;
            }
        }
        return false;
    }

    public Match getMatch(Player p){
        for(Match match : this.currentMatches){
            if(match.getP1() == p || match.getP2() == p){
                return match;
            }
        }
        return null;
    }

    public int getAmountInQueue(Ladder l, boolean ranked){
        return this.getQueue(l, ranked).getQueue().size();
    }

    public ArrayList<Match> getMatches(Ladder ladder, boolean ranked){
        ArrayList<Match> matches = new ArrayList<>();
        for(Match match : this.currentMatches){
            if(match.getLadder() == ladder && match.isRanked() == ranked){
                matches.add(match);
            }
        }
        return matches;
    }

    public void addToQueue(Player p, Ladder ladder, boolean ranked){
        Queue queue = getQueue(ladder, ranked);
        if(queue == null){
            return;
        }
        queue.addToQueue(p);
    }

    public ArrayList<Match> getCurrentMatches(){
        return this.currentMatches;
    }

    public void remove(Match match){
        this.currentMatches.remove(match);
    }

    public boolean isInMatch(Player p){
        if(getMatch(p) != null){
            return true;
        }
        return false;
    }

    public void chunkLoader(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Arena arenas : arena.getPlayableArenas()){
                    arenas.getSpawn1().getChunk().load(true);
                    arenas.getSpawn2().getChunk().load(true);
                }
            }
        }.runTaskTimer(Main.getInstance(), 40, 40);
    }

}

package com.weswaas.training.game.match;

import com.weswaas.api.utils.MessageUtils;
import com.weswaas.training.Main;
import com.weswaas.training.data.PlayerData;
import com.weswaas.training.game.arena.Arena;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.util.LocationUtil;
import com.weswaas.training.util.PlayerInv;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Weswas on 24/12/2016.
 */
public class Match implements Listener{

    private boolean ranked;
    private Arena arena;
    private Ladder ladder;
    private boolean started = false;
    private Player p1;
    private Player p2;
    private long millis;
    private long difference;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Item> groundItems = new ArrayList<>();
    private HashMap<Player, Integer> cooldown = new HashMap<>();
    private HashMap<Player, BukkitRunnable> runnables = new HashMap<>();

    public Match(boolean ranked, Arena arena, Ladder ladder, Player p1, Player p2){
        this.ranked = ranked;
        this.arena = arena;
        this.ladder = ladder;
        this.p1 = p1;
        this.p2 = p2;
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public void start(){
        if(isStarted())return;

        arena.setPlayable(false);

        for(Location loc : new Location[]{arena.getSpawn1(), arena.getSpawn2()}){
            loc.getChunk().load();
        }

        if(!this.p1.isOnline()){
            end(this.p2);
            return;
        }

        if(!this.p2.isOnline()){
            end(this.p1);
            return;
        }

        this.p1.teleport(this.arena.getSpawn1());
        this.p2.teleport(this.arena.getSpawn2());


        this.p1.showPlayer(this.p2);
        this.p2.showPlayer(this.p1);

        for(Player pls : new Player[]{this.p1, this.p2}){
            for(Player all : Bukkit.getServer().getOnlinePlayers()){
                if(all.getName() != this.p1.getName() && all.getName() != this.p2.getName()){
                    pls.hidePlayer(all);
                }
            }
            int rating = Main.getInstance().getPlayerDataManager().getRating((pls == this.p1 ? this.p2 : this.p1), this.ladder);
            pls.sendMessage(Main.PREFIX + "Starting " + (isRanked() ? "ranked" : "unranked") + " match against §b" + (pls == this.p1 ? this.p2.getName() : this.p1.getName()) + " §3with kit §b" + this.ladder.getName() + (isRanked() ? " (Rating: " + rating + ")" : ""));
            kit(pls);
            pls.setHealth(20.0D);
            pls.setLevel(0);
            pls.setFoodLevel(20);
            pls.setSaturation(5.0f);
            pls.setGameMode(GameMode.SURVIVAL);
            if(ranked){
                if(!pls.hasPermission("training.ranked.unlimited")){
                    Main.getInstance().getPlayerDataManager().decreaseRankeds(pls);
                }
            }
        }

        new BukkitRunnable() {
            int i = 5;
            public void run() {

                if(i == 0){
                    Match.this.started = true;
                    for(Player pls : new Player[]{Match.this.p1, Match.this.p2}){
                        if(pls == null){
                            this.cancel();
                            return;
                        }
                        pls.sendMessage(Main.PREFIX + "Match is now starting.");
                        pls.playSound(pls.getLocation(), Sound.NOTE_BASS, 0.8f, 0.8f);
                        millis = System.currentTimeMillis();
                    }
                    this.cancel();
                    return;
                }else{
                    for(Player pls : new Player[]{Match.this.p1, Match.this.p2}){
                        if(pls == null){
                            this.cancel();
                            return;
                        }
                        pls.sendMessage(Main.PREFIX + "Match starting in §b" + i + " §3second" + (i > 1 ? "s" : ""));
                    }
                }

                i--;
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);

    }

    private void kit(Player p){

        p.getInventory().clear();
        PlayerData data = Main.getInstance().getPlayerDataManager().getPlayerData(p);
        PlayerInv kit = data.getKits().get(this.ladder);
        if(kit == null){
            Main.getInstance().getKitManager().getKit(this.ladder.getName()).give(p);
        }else{
            p.getInventory().setArmorContents(kit.getArmorContents());
            p.getInventory().setContents(kit.getContents());
        }

    }

    public void end(Player winner){

        this.started = false;

        Main.getInstance().getMatchManager().remove(this);
        HandlerList.unregisterAll(this);

        for(Player pls : new Player[]{this.p1, this.p2}){
            if(pls != null){
                pls.sendMessage(Main.PREFIX + ChatColor.AQUA + winner.getName() + " §3won the match with §b" + MessageUtils.getHearts(winner.getHealth(), 0.0) + " " + MessageUtils.HEART_WITH_COLOR);
                long now = System.currentTimeMillis();
                difference = (now - millis);
                String timeToShow = "";
                long minutes = TimeUnit.MILLISECONDS.toMinutes(difference);
                long secs = TimeUnit.MILLISECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference));
                if(minutes == 1){
                    timeToShow = String.format("%d minute, %d seconds",
                            minutes, secs);
                }
                else if(minutes == 0){
                    timeToShow = String.format("%d seconds",
                            secs);
                }
                else{
                    timeToShow = String.format("%d minutes, %d seconds",
                            minutes, secs);
                }

                pls.sendMessage(Main.PREFIX + "Match Time: §b" + timeToShow);
            }
        }

        for(Player pls : new Player[]{this.p1, this.p2}){
            Main.getInstance().getKitManager().getKit("Lobby").give(pls);
            pls.teleport(LocationUtil.getSpawnLocation());
            Main.getInstance().getPlayerManager().setInLobby(pls);
            pls.setHealth(20.0D);
            pls.setLevel(0);
            pls.setExp(0.0f);
            pls.setTotalExperience(20);
            pls.setFoodLevel(20);
            pls.setFireTicks(0);
            for(PotionEffect pe : pls.getActivePotionEffects()){
                pls.removePotionEffect(pe.getType());
            }

            for(Player all : Bukkit.getServer().getOnlinePlayers()){
                all.showPlayer(pls);
                if(!Main.getInstance().getSpec().isSpectator(all)){
                    pls.showPlayer(all);
                }
            }
        }

        if(this.ranked){

            Player loser = null;

            double p1 = Main.getInstance().getPlayerDataManager().getRating(this.p1, this.ladder);
            double p2 = Main.getInstance().getPlayerDataManager().getRating(this.p2, this.ladder);
            int change = 0;
            double ep1 = 1.0 / (1.0 + Math.pow(10.0, (p1 - p2) / 400.0));
            double ep2 = 1.0 / (1.0 + Math.pow(10.0, (p2 - p1) / 400.0));

            if(winner == this.p1){
                change = (int) (ep1 * 32.0);
                loser = this.p2;
            }else{
                change = (int) (ep2 * 32.0);
                loser = this.p1;
            }

            change = change > 32 ? 32 : change;
            change = change <= 0 ? 1 : change;

            int winnersRating = Main.getInstance().getPlayerDataManager().getRating(winner, this.ladder);
            int losersRating = Main.getInstance().getPlayerDataManager().getRating(loser, this.ladder);

            for(Player pls : new Player[]{this.p1, this.p2}){
                if(pls != null){
                    pls.sendMessage(Main.PREFIX + "Elo change: §a" + winner.getName() + " (" + (winnersRating+change) + ") (+" + change + ")§3, and §c" + loser.getName() + " (" + (losersRating-change) + ") (-" + change + ")");
                }
            }

            Main.getInstance().getPlayerDataManager().updateRating(winner, this.ladder, change, true);
            Main.getInstance().getPlayerDataManager().updateRating(loser, this.ladder, change, false);

        }

        clear();
        for(Player pls : new Player[]{this.p1, this.p2}){
            sendRawMessages(pls, this.p1);
            sendRawMessages(pls, this.p2);
        }

        new BukkitRunnable() {
            public void run() {
                arena.setPlayable(true);
            }
        }.runTaskLater(Main.getInstance(), 150);

    }

    private void sendRawMessages(Player p, Player opponent){
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + Main.PREFIX + ChatColor.AQUA + "Click to view " + opponent.getName() + "'s Inventory\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/inv " + opponent.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click to see " + opponent.getName() + "'s inventory\"}]}}}"));
        connection.sendPacket(packet);
    }

    public void clear(){

        for(Item item : this.groundItems){
            item.remove();
        }

        Vector max = Vector.getMaximum(arena.getCorner1().toVector(), arena.getCorner2().toVector());
        Vector min = Vector.getMinimum(arena.getCorner1().toVector(), arena.getCorner2().toVector());
        for(int x = min.getBlockX(); x <= max.getBlockX(); x++){
            for(int y = min.getBlockY(); y <= max.getBlockY(); y++){
                for(int z = min.getBlockZ(); z <= max.getBlockZ(); z++){
                    Block block = arena.getSpawn1().getWorld().getBlockAt(x, y, z);

                    for(Entity ent : block.getChunk().getEntities()){
                        if(ent instanceof Item || ent instanceof Arrow){
                            ent.remove();
                        }
                    }

                    if(this.blocks.contains(block)){
                        block.setType(Material.AIR);
                    }
                    else if(isLiquid(block)){
                        block.setType(Material.AIR);
                    }
                    else if(block.getType() == Material.OBSIDIAN || block.getType() == Material.COBBLESTONE){
                        block.setType(Material.AIR);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onRegain(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p == this.p1 || p == this.p2){
                if(e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED){
                    if(!this.ladder.canRegen()){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isLiquid(Block block){
        return block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA;
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent e){
        if(!isMatchPlayer(e.getPlayer())){
            return;
        }

        Block block = e.getBlock();
        if(block.getLocation().add(0, -2, 0).getBlock().getType() == Material.WOOD){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(isMatchPlayer(e.getPlayer())){
            this.groundItems.add(e.getItemDrop());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(isMatchPlayer(e.getPlayer())){
            if(e.getPlayer() == this.p1){
                end(this.p2);
            }else{
                end(this.p1);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(isMatchPlayer(e.getPlayer())){
            if(this.started){
                this.blocks.add(e.getBlock());
            }else{
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();

        if(!isMatchPlayer(p)){
            return;
        }

        if(e.getItem() == null){
            return;
        }

        if(e.getItem().getType() != Material.MUSHROOM_SOUP && e.getItem().getType() != Material.ENDER_PEARL){
            return;
        }

        Ladder current = Main.getInstance().getMatchManager().getMatch(p).getLadder();

        if(e.getItem().getType() == Material.MUSHROOM_SOUP){
            if(current == Main.getInstance().getLadderManager().getLadder("Soup")
                    || current == Main.getInstance().getLadderManager().getLadder("SuperSoup")){

                if(p.getHealth() > 19.9D){
                    return;
                }

                e.getItem().setType(Material.BOWL);
                double now = p.getHealth() + 6.0D;
                if(now > 20.0D){
                    now = 20.0D;
                }
                p.setHealth(now);

            }
        }
        else if(e.getItem().getType() == Material.ENDER_PEARL){
            if(current == Main.getInstance().getLadderManager().getLadder("Debuff")
                    || current == Main.getInstance().getLadderManager().getLadder("NoDebuff")){

                if(!this.started){
                    e.setCancelled(true);
                    return;
                }

                if(cooldown.containsKey(p)){
                    p.sendMessage(Main.PREFIX + "§cYou must wait for " + cooldown.get(p) + " seconds to use this again.");
                    e.setCancelled(true);
                    return;
                }

                cooldown.put(p, 11);
                runnables.put(p, new BukkitRunnable() {
                    @Override
                    public void run() {
                        cooldown.put(p, cooldown.get(p) - 1);
                        if(cooldown.get(p) == 0){
                            cooldown.remove(p);
                            runnables.remove(p);
                            cancel();
                        }
                    }
                });

                runnables.get(p).runTaskTimerAsynchronously(Main.getInstance(), 0, 20);

            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(isMatchPlayer(e.getPlayer())){
            if(this.started){
                if(!this.blocks.contains(e.getBlock())){
                    e.setCancelled(true);
                }else{
                    this.blocks.remove(e.getBlock());
                }
            }else{
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEmpty(PlayerBucketEmptyEvent e){
        if(isMatchPlayer(e.getPlayer())){
            if(this.started){
                Block adj = e.getBlockClicked().getRelative(e.getBlockFace());
                this.blocks.add(adj);
            }else{
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(final PlayerDeathEvent e){
        if(isMatchPlayer(e.getEntity())){
            e.setDeathMessage(null);
            e.getDrops().clear();
            new BukkitRunnable() {
                public void run() {
                    e.getEntity().spigot().respawn();
                    if(e.getEntity() == Match.this.p1){
                        end(Match.this.p2);
                    }else{
                        end(Match.this.p1);
                    }
                }
            }.runTaskLater(Main.getInstance(), 2);
        }
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Arrow){
            if(e.getEntity() instanceof Player){
                Arrow arrow = (Arrow) e.getDamager();
                if(arrow.getShooter() instanceof Player){
                    final Player shooter = (Player)arrow.getShooter();
                    final Player victim = (Player) e.getEntity();

                    if(!this.started){
                        return;
                    }

                    if(isMatchPlayer(shooter) && isMatchPlayer(victim)){
                        if(victim != shooter && victim.getHealth() - e.getFinalDamage() > 0){
                            shooter.sendMessage(ChatColor.AQUA + victim.getName() + " §3is now at §b" + MessageUtils.getHearts(victim.getHealth(), e.getFinalDamage()) + " " + MessageUtils.HEART_WITH_COLOR);
                        }

                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(isMatchPlayer(p)){
                if(!this.started){
                    e.setCancelled(true);
                }else if(e.getFinalDamage() >= ((Player)e.getEntity()).getHealth()){
                    Main.getInstance().getPlayerManager().storeInventory(this.p1, e.getEntity() == this.p1);
                    Main.getInstance().getPlayerManager().storeInventory(this.p2, e.getEntity() == this.p2);
                }
            }
        }
    }

    @EventHandler
    public void onHeadEat(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        if(e.getItem().getType().equals(Material.GOLDEN_APPLE) & e.getItem().hasItemMeta()){
            if(e.getItem().getItemMeta().getDisplayName().equals("Golden Head")){
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(!isMatchPlayer(e.getPlayer())){
            return;
        }

        e.setCancelled(true);
    }

    private boolean isMatchPlayer(Player p){
        return p == this.p1 || p == this.p2;
    }

    public boolean isStarted(){
        return this.started;
    }

    public boolean isRanked(){
        return this.ranked;
    }

    public Arena getArena(){
        return this.arena;
    }

    public Ladder getLadder(){
        return this.ladder;
    }

    public Player getP1(){
        return this.p1;
    }

    public Player getP2(){
        return this.p2;
    }

}

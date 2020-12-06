package com.weswaas.training.manager;

import com.weswaas.training.Main;
import com.weswaas.training.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.util.ArrayList;

/**
 * Created by Weswas on 28/04/2020.
 */
public class SpectatorManager implements Listener{

    private PlayerManager pm;

    private ArrayList<String> spectators;

    public SpectatorManager(){
        spectators = new ArrayList<>();
    }

    public void setPlayerManager(PlayerManager pm){
        this.pm = pm;
    }

    public boolean isSpectator(Player p){
        return this.spectators.contains(p.getName());
    }

    public ArrayList<String> getSpectators(){
        return this.spectators;
    }

    public boolean isSpectator(String name){
        return this.spectators.contains(name);
    }

    public void setSpectator(Player p){
        if(isSpectator(p)){
            this.spectators.remove(p.getName());
        }
        if(!isSpectator(p.getName())){
            this.spectators.add(p.getName());
        }

        p.getInventory().clear();
        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);
        p.setGameMode(GameMode.CREATIVE);
        p.sendMessage(Main.PREFIX + "You are now a spectator.");
        p.teleport(p.getLocation().add(0, 2, 0));
        p.setFlying(true);
        setVanish(true, p);

        p.updateInventory();

    }

    public void removeSpectator(Player p){
        this.spectators.remove(p.getName());
        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(LocationUtil.getSpawnLocation());
        p.getInventory().clear();
        p.setFlying(false);
        p.setHealth(20.0);
        p.setFoodLevel(20);
        setVanish(false, p);
        p.sendMessage(Main.PREFIX + "You are not a spectator anymore.");
        Main.getInstance().getKitManager().getKit("Lobby").give(p);
        p.updateInventory();

    }

    private void setVanish(boolean vanish, Player p){
        if(vanish){
            for(Player pls : Bukkit.getServer().getOnlinePlayers())
                pls.hidePlayer(p);
        }else{
            for(Player pls : Bukkit.getServer().getOnlinePlayers()){
                pls.showPlayer(p);
            }
        }
    }

    public void teleport(Player p, Player target){
        if(isSpectator(p)){
            for(Player pls : Bukkit.getServer().getOnlinePlayers()){
                pls.hidePlayer(p);
            }
            p.teleport(target.getLocation());
        }else{
            p.sendMessage(Main.PREFIX + ChatColor.RED + "You have to be spectator to teleport to someone.");
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(!isSpectator(p)){
            return;
        }

        //A MODIFIER SI AJOUT D'ITEMS POUR SPEC
        //IL FAUDRA RAJOUTER LE FIRE BLOCK BREAK EVENT (DISPO DANS L'UHC)

        e.setCancelled(true);

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(!isSpectator(e.getPlayer())){
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onProjectile(ProjectileLaunchEvent e){
        if(e.getEntity().getShooter() instanceof Player){
            Player p = (Player) e.getEntity().getShooter();
            if(!isSpectator(p)){
                return;
            }
            e.setCancelled(true);
            p.sendMessage(Main.PREFIX + "§cYou should not try to lauch projectiles while being a spectator. A message has been sent to the administrators.");
            Main.getInstance().getServer().getLogger().info(p.getName() + " tried to launch a projectile.");
        }
    }

    @EventHandler
    public void on(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(!isSpectator((Player)e.getEntity())){
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player damager = (Player) e.getDamager();
            if(!isSpectator(damager)){
                return;
            }
            e.setCancelled(true);
            damager.sendMessage(Main.PREFIX + "§cYou should not try to hit other players while being a spectator. A message has been sent to the administrators.");
            Main.getInstance().getServer().getLogger().info(damager.getName() + " tried to hit an entity while being a spectator.");
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(!isSpectator(e.getPlayer())){
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(!isSpectator(e.getPlayer())){
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(!isSpectator((Player)e.getWhoClicked())){
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(!isSpectator(e.getPlayer())){
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e){
        if(!isSpectator(e.getPlayer())){
            return;
        }
        e.setCancelled(true);
        e.getPlayer().sendMessage(Main.PREFIX + "§cYou should not try to hit other players while being a spectator. A message has been sent to the administrators.");
        Main.getInstance().getServer().getLogger().info(e.getPlayer().getName() + " tried to empty a bucket while being a spectator.");
    }

    @EventHandler
    public void onFill(PlayerBucketFillEvent e){
        if(!isSpectator(e.getPlayer())){
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onVehicle(VehicleEnterEvent e){
        if(e.getEntered() instanceof Player){
            Player p = (Player)e.getEntered();
            if(!isSpectator(p)){
                return;
            }
            e.setCancelled(true);
        }
    }































}

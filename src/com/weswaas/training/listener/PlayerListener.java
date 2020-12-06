package com.weswaas.training.listener;

import com.weswaas.training.Main;
import com.weswaas.training.ServerMode;
import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ConfigUtil;
import com.weswaas.training.util.LocationUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Weswas on 23/12/2016.
 */
public class PlayerListener implements Listener{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        e.setJoinMessage(null);

        p.getInventory().clear();

        p.setHealth(20);
        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.setLevel(0);
        p.setGameMode(GameMode.SURVIVAL);
        p.setFlying(false);

        p.teleport(LocationUtil.getSpawnLocation());
        Main.getInstance().getKitManager().getKit("Lobby").give(p);
        Main.getInstance().getPlayerManager().setInLobby(p);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        if(ServerMode.getServerMode() != ServerMode.ONLINE){
            if(!p.hasPermission("training.maintenance")){
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are not allowed to join the server while it is in " + (ServerMode.getServerMode() == ServerMode.MAINTENANCE ? "maintenance" : "offline") + " mode.");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        e.setQuitMessage(null);

    }

    @EventHandler
    public void on(PlayerInteractEvent e){
        Player p = e.getPlayer();

        Kit kit = Kit.playerKits.get(p);
        if(kit == null){
            return;
        }

        if(e.getPlayer().getItemInHand() == null){
            return;
        }

        if(e.getItem() == null){
            return;
        }

        kit.onInteract(e);

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();

        Kit kit = Kit.playerKits.get(p);
        if(kit == null){
            return;
        }

        kit.onDrop(e);

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(e.getPlayer().getLocation().getBlockY() < 60){
            e.getPlayer().teleport(ConfigUtil.getLocation("spawn"));
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        Player p = e.getPlayer();

        Kit kit = Kit.playerKits.get(p);
        if(kit == null){
            return;
        }

        kit.onPickup(e);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player)e.getEntity();

            if(Main.getInstance().getPlayerManager().isInLobby(p) || Main.getInstance().getPlayerManager().isInKit(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player)e.getEntity();
            if(!isInMatch(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(!isInMatch(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(!isInMatch(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(Main.getInstance().getPlayerManager().isInLobby( e.getEntity()) || Main.getInstance().getPlayerManager().isInKit(e.getEntity())){
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onHeadEat(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        if(e.getItem().getType().equals(Material.GOLDEN_APPLE) & e.getItem().hasItemMeta()){
            if(e.getItem().getItemMeta().getDisplayName().contains("Golden Head")){
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
            }
        }
    }

    private boolean isInMatch(Player p){
        return Main.getInstance().getPlayerManager().isInMatch(p);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        e.setCancelled(true);
    }



}

package com.weswaas.training.kit;

import com.weswaas.training.Main;
import com.weswaas.training.data.PlayerData;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.util.LocationUtil;
import com.weswaas.training.util.PlayerInv;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Weswas on 26/12/2016.
 */
public class KitEditor implements Listener{

    private HashMap<Player, Ladder> editing;

    public KitEditor(){
        this.editing = new HashMap<>();
    }

    public void edit(final Player p, Ladder ladder){

        p.teleport(LocationUtil.getKitLocation());
        p.getInventory().clear();
        Main.getInstance().getPlayerManager().setInKit(p);
        for(Player pls : Bukkit.getServer().getOnlinePlayers()){
            pls.hidePlayer(p);
            p.hidePlayer(pls);
        }

        this.editing.put(p, ladder);
        p.sendMessage(Main.PREFIX + "Editing §b" + ladder.getName() + " §3kit");

        PlayerData data = Main.getInstance().getPlayerDataManager().getPlayerData(p);
        PlayerInv kit = data.getKits().get(ladder);
        if(kit != null){
            p.getInventory().setArmorContents(kit.getArmorContents());
            p.getInventory().setContents(kit.getContents());
            p.updateInventory();
        }else{
            Main.getInstance().getKitManager().getKit(ladder.getName()).give(p);
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }
        if(e.getClickedBlock() == null){
            return;
        }
        if(e.getClickedBlock().getType() != Material.SIGN
                && e.getClickedBlock().getType() != Material.WALL_SIGN
                && e.getClickedBlock().getType() != Material.SIGN_POST){
            return;
        }
        if(!this.editing.containsKey(p)){
            return;
        }

        Ladder ladder = this.editing.get(p);
        Sign sign = (Sign) e.getClickedBlock().getState();

        if(!sign.getLine(0).equalsIgnoreCase("[KitEditing]")){
            return;
        }

        if(sign.getLine(1).equalsIgnoreCase("Save Kit")){
            if(p.getInventory().getArmorContents() == null || p.getInventory().getContents() == null){
                p.sendMessage(Main.PREFIX + "§cYour armor / items have to be weared!");
                return;
            }
            PlayerData data = Main.getInstance().getPlayerDataManager().getPlayerData(p);
            if(data == null){
                p.sendMessage(Main.PREFIX + "§cInternal error. Please relog");
                return;
            }
            PlayerInv kit = new PlayerInv(p.getInventory().getContents(), p.getInventory().getArmorContents());
            data.setKit(ladder, kit, true);
            p.sendMessage(Main.PREFIX + ChatColor.AQUA + ladder.getName() + " §3kit were successfully saved");
            return;
        }

        else if(sign.getLine(1).equalsIgnoreCase("Load Default")){
            Main.getInstance().getKitManager().getKit(ladder.getName()).give(p);
            p.sendMessage(Main.PREFIX + "Loaded §b" + ladder.getName() + " §3default kit");
            return;
        }

        else if(sign.getLine(1).equalsIgnoreCase("Cancel, and go")){
            Main.getInstance().getPlayerManager().setInLobby(p);
            p.teleport(LocationUtil.getSpawnLocation());
            Main.getInstance().getKitManager().getKit("Lobby").give(p);
            this.editing.remove(p);
            for(Player pls : Bukkit.getServer().getOnlinePlayers()){
                pls.showPlayer(p);
                p.showPlayer(pls);
            }
            p.updateInventory();
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(this.editing.containsKey(e.getPlayer())){
            this.editing.remove(e.getPlayer());
            Main.getInstance().getPlayerManager().setInLobby(e.getPlayer());
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e){
        if(!isEditing(e.getPlayer())){
            return;
        }
        e.setCancelled(true);
        e.getPlayer().updateInventory();
    }

    public boolean isEditing(Player p){
        if(this.editing.containsKey(p)){
            return true;
        }
        return false;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(!isEditing(e.getPlayer())){
            return;
        }

        e.setCancelled(true);
        e.getPlayer().updateInventory();

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if(!isEditing(e.getPlayer())){
            return;
        }

        if(e.getItem() == null){
            return;
        }

        ItemStack item = e.getItem();

        if(item.getType() == Material.ENDER_PEARL || item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.POTION || item.getType() == Material.LAVA_BUCKET || item.getType() == Material.WATER_BUCKET
                || item.getType() == Material.BUCKET){
            e.setCancelled(true);
            e.getPlayer().updateInventory();
        }

    }

    @EventHandler
    public void onProj(ProjectileLaunchEvent e){
        if(e.getEntity() instanceof Arrow){
            if(e.getEntity().getShooter() instanceof Player){
                Player p = (Player) e.getEntity().getShooter();
                if(!isEditing(p)){
                    return;
                }

                e.setCancelled(true);

            }
        }
    }

}

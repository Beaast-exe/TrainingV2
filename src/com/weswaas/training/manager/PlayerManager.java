package com.weswaas.training.manager;

import com.weswaas.api.utils.MessageUtils;
import com.weswaas.training.Main;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Weswas on 01/12/2016.
 */
public class PlayerManager implements Listener{

    private SpectatorManager spec;

    public ArrayList<Player> inLobby;
    public ArrayList<Player> inMatch;
    public ArrayList<Player> inKit;
    public HashMap<String, Inventory> invs = new HashMap<>();

    public PlayerManager(SpectatorManager spec){
        this.inLobby = new ArrayList<>();
        this.inMatch = new ArrayList<>();
        this.inKit = new ArrayList<>();
        this.spec = spec;
    }

    public void setInLobby(Player p){
        this.inLobby.add(p);
        this.inMatch.remove(p);
        this.inKit.remove(p);
        spec.getSpectators().remove(p.getName());
    }

    public void setInMatch(Player p){
        this.inMatch.add(p);
        this.inLobby.remove(p);
        this.inKit.remove(p);
        spec.getSpectators().remove(p.getName());
    }

    public void setInKit(Player p){
        this.inKit.add(p);
        this.inLobby.remove(p);
        this.inMatch.remove(p);
        spec.getSpectators().remove(p.getName());
    }

    public boolean isSpectator(Player p){
        return spec.isSpectator(p);
    }

    public void setSpectator(Player p){
        this.inLobby.remove(p);
        this.inMatch.remove(p);
        this.inKit.remove(p);
        spec.setSpectator(p);
    }

    public void removeSpectator(Player p){
        spec.removeSpectator(p);
        this.setInLobby(p);
    }

    public void storeInventory(final Player p, boolean dead){
        int i;
        Inventory inv = Bukkit.createInventory(null, 54, p.getName());
        PlayerInventory pinv = p.getInventory();
        for(i = 9; i <= 35; ++i){
            inv.setItem(i - 9, pinv.getContents()[i]);
        }
        for(i = 0; i <= 8; ++i){
            inv.setItem(i + 27, pinv.getContents()[i]);
        }
        inv.setItem(36, pinv.getHelmet());
        inv.setItem(37, pinv.getChestplate());
        inv.setItem(38, pinv.getLeggings());
        inv.setItem(39, pinv.getBoots());

        if(dead){
            inv.setItem(48, new ItemBuilder(Material.SKULL_ITEM).name("§cPlayer Died").build());
        }else{
            inv.setItem(48, new ItemBuilder(Material.SPECKLED_MELON).name("§aHealth: " + MessageUtils.getHearts(p.getHealth(), 0.0)).build());
        }

        inv.setItem(50, new ItemBuilder(Material.COOKED_BEEF).name("§aPlayer Food: " + p.getFoodLevel()).build());
        this.invs.put(p.getName(), inv);
        new BukkitRunnable(){
            @Override
            public void run() {
                PlayerManager.this.invs.remove(p.getName());
            }
        }.runTaskLater(Main.getInstance(), 2400);

    }

    @EventHandler
    public void on(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() == null)return;
        if(e.getCurrentItem() == null)return;

        if(this.invs.containsKey(e.getClickedInventory().getName())){
            e.setCancelled(true);
        }

    }

    public boolean isInLobby(Player p){
        return this.inLobby.contains(p);
    }

    public boolean isInMatch(Player p){
        return this.inMatch.contains(p);
    }

    public boolean isInKit(Player p){
        return this.inKit.contains(p);
    }

    private String formatSeconds(int seconds) {
        int minutes = seconds / 60;
        if (minutes == 0) {
            return "" + seconds + " seconds";
        }
        return "" + minutes + " minutes and " + (seconds %= 60) + " seconds";
    }

}

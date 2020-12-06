package com.weswaas.training.kit;

import com.weswaas.training.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Weswas on 23/12/2016.
 */
public abstract class Kit{

    private String name;
    private Map<Integer, ItemStack> armor = new HashMap<>();
    private Map<Integer, ItemStack> items = new HashMap<>();

    public static HashMap<Player, Kit> playerKits = new HashMap<>();

    public Kit(String name){
        this.name = name;
        register();
    }

    public String getName() {
        return this.name;
    }

    public void setHelmet(ItemStack helmet){
        this.armor.put(0, helmet);
    }

    public void setChestplate(ItemStack chestplate){
        this.armor.put(1, chestplate);
    }

    public void setLeggings(ItemStack leggings){
        this.armor.put(2, leggings);
    }

    public void setBoots(ItemStack boots){
        this.armor.put(3, boots);
    }

    public void setItem(int index, ItemStack item){
        this.items.put(index, item);
    }

    public void onInteract(PlayerInteractEvent e){}

    public void onDrop(PlayerDropItemEvent e){}

    public void onPickup(PlayerPickupItemEvent e){}

    public void give(final Player p){

        new BukkitRunnable() {
            public void run() {
                p.getInventory().clear();

                for(PotionEffect pe : p.getActivePotionEffects()){
                    p.removePotionEffect(pe.getType());
                }

                p.getInventory().setHelmet(Kit.this.armor.get(0));
                p.getInventory().setChestplate(Kit.this.armor.get(1));
                p.getInventory().setLeggings(Kit.this.armor.get(2));
                p.getInventory().setBoots(Kit.this.armor.get(3));

                for(Integer i : Kit.this.items.keySet()){
                    p.getInventory().setItem(i, Kit.this.items.get(i));
                }

                playerKits.remove(p);
                playerKits.put(p, Kit.this);

                p.updateInventory();
            }
        }.runTaskLater(Main.getInstance(), 2);
    }

    public abstract void register();

}

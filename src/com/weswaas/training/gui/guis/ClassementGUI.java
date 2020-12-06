package com.weswaas.training.gui.guis;

import com.weswaas.training.Main;
import com.weswaas.training.data.PlayerDataManager;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.gui.GUI;
import com.weswaas.training.util.ValueComparator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by Weswas on 27/12/2016.
 */
public class ClassementGUI extends GUI{

    private HashMap<Ladder, HashMap<Ladder, Integer>> top = new HashMap<>();

    public ClassementGUI(){
        super(null, 9*2, "§6Classement GUI");
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }

    @Override
    public void onOpen(InventoryOpenEvent e) {

    }

    @Override
    public void onDrag(InventoryDragEvent e) {

    }

    public void update(){

        PlayerDataManager data = Main.getInstance().getPlayerDataManager();

        int i = 0;
        for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
            HashMap<String, Integer> map = data.allElos.get(ladder);
            ValueComparator bvc = new ValueComparator(map);
            TreeMap<String, Integer> sorted_map = new TreeMap<>(bvc);
            sorted_map.putAll(map);

            ItemStack item = ladder.getIcon();
            ItemStack is = new ItemStack(ladder.getIcon().getType());
            is.setItemMeta(item.getItemMeta());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(item.getItemMeta().getDisplayName());
            if(item.getType() == Material.POTION){
                is.setDurability(Short.valueOf("16421"));
            }
            ArrayList<String> lore = new ArrayList<>();

            for(int c = 1; c < 11; c++){
                Map.Entry<String, Integer> e = sorted_map.pollFirstEntry();

                if(e == null){
                    continue;
                }

                String name = Bukkit.getOfflinePlayer(UUID.fromString(e.getKey())).getName();
                int score = e.getValue();
                lore.add("§b" + c + " §8- §3" + name + " §8» §b" + score);
            }


            meta.setLore(lore);
            is.setItemMeta(meta);

            setItem(i, is);
            i++;

        }
    }

}

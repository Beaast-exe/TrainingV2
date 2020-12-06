package com.weswaas.training.kit.kits.other;

import com.weswaas.training.Main;
import com.weswaas.training.gui.guis.ClassementGUI;
import com.weswaas.training.gui.guis.MatchGUI;
import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Weswas on 24/12/2016.
 */
public class Lobby extends Kit{

    public Lobby(){
        super("Lobby");
    }

    public void register(){

        setHelmet(null);
        setChestplate(null);
        setLeggings(null);
        setBoots(null);

        setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).name("§3Ranked Match").setUnbreakable(true).build());
        setItem(1, new ItemBuilder(Material.IRON_SWORD).name("§3Unranked Match").setUnbreakable(true).build());
        setItem(7, new ItemBuilder(Material.PAPER).name("§3Classement").setUnbreakable(true).build());
        setItem(8, new ItemBuilder(Material.BOOK).name("§3Kit Editor").setUnbreakable(true).build());

    }

    @Override
    public void onInteract(PlayerInteractEvent e) {

        if(!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }

        if(!e.getItem().hasItemMeta()){
            return;
        }

        if(Main.getInstance().getMatchManager().isInMatch(e.getPlayer())){
            return;
        }

        if(Main.getInstance().getPlayerManager().isInKit(e.getPlayer())){
            return;
        }

        if(e.getItem().getType() == Material.DIAMOND_SWORD){
            new MatchGUI(true, false, null, false).open(e.getPlayer());
        }
        else if(e.getItem().getType() == Material.IRON_SWORD){
            new MatchGUI(false, false, null, false).open(e.getPlayer());
        }
        else if(e.getItem().getType() == Material.BOOK){
            new MatchGUI(false, false, null, true).open(e.getPlayer());
        }
        else if(e.getItem().getType() == Material.PAPER){
            if(!Main.getInstance().getMatchManager().isInQueue(e.getPlayer())){
                new ClassementGUI().open(e.getPlayer());
            }
        }
    }

    @Override
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
}

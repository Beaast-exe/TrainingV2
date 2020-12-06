package com.weswaas.training.listener;

import com.weswaas.training.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

/**
 * Created by Weswas on 23/12/2016.
 */
public class InventoryListener implements Listener{

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player p = (Player)event.getWhoClicked();
        if(p.getInventory() != null){
            GUI gui = GUI.playerGUI.get(p);
            if(gui == null){
                return;
            }
            else{
                GUI.playerGUI.get(p).onClick(event);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        Player p = (Player)event.getWhoClicked();
        if(p.getInventory() != null) {
            GUI gui = GUI.playerGUI.get(p);
            if (gui == null) {
                return;
            }
            GUI.playerGUI.get(p).onDrag(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player p = (Player)event.getPlayer();
        if(p.getInventory() != null){
            if(GUI.playerGUI.get(p) != null){
                GUI.playerGUI.get(p).onClose(event);
            }
            //user.getInventory().close();
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        Player p = (Player)event.getPlayer();
        if(p.getInventory() != null){
            //user.getInventory().open(user.getPlayer());
            if(GUI.playerGUI.get(p) != null){
                GUI.playerGUI.get(p).onOpen(event);
            }
        }
    }

}

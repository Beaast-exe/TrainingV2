package com.weswaas.training.gui.guis;

import com.weswaas.training.Main;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.gui.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Weswas on 23/12/2016.
 */
public class MatchGUI extends GUI{

    private boolean ranked;
    private boolean duel;
    private Player dueled;
    private boolean editor;

    public MatchGUI(boolean ranked, boolean duel, Player dueled, boolean editor) {
        super(null, 9*2, "§6" + (duel ? "Duel" : (editor ? "Editor" : (ranked ? "Ranked" : "Unranked") + " Match")) + " Selector");
        this.ranked = ranked;
        this.duel = duel;
        this.editor = editor;
        if(duel){
            this.dueled = dueled;
        }else{
            this.dueled = null;
        }
    }

    public void update(){

        Player player = getPlayer();

        if(player != null){
            int i = 0;
            for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
                if(duel || editor){
                    ItemStack icon = ladder.getIcon();
                    setItem(i, icon);
                }else{

                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("§3In Queue §8» §b" + Main.getInstance().getMatchManager().getAmountInQueue(ladder, ranked));
                    lore.add("§3In Match §8» §b" + Main.getInstance().getMatchManager().getMatches(ladder, ranked).size());

                    ItemStack item = ladder.getIcon().clone();
                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    setItem(i, item);
                }
                i++;
            }

        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);

        if(e.getClickedInventory() == null){return;}
        if(e.getCurrentItem() == null){return;}
        Player p = (Player)e.getWhoClicked();
        if(p == null)return;

        if(e.getCurrentItem().getType() == Material.AIR){
            return;
        }

        Ladder ladder = Main.getInstance().getLadderManager().getByMaterial(e.getCurrentItem().getType());

        if(duel){
            Main.getInstance().getDuelManager().duel(p, dueled, ladder);
        }else if(editor){
            Main.getInstance().getKitEditor().edit(p, ladder);
        }else{
            if(ranked){
                if(!Main.getInstance().getPlayerDataManager().getPlayerData(p).canRanked()){
                    p.sendMessage(Main.PREFIX + "§cYou reached your ranked matches limit for today!");
                }else{
                    Main.getInstance().getMatchManager().addToQueue(p, ladder, true);
                }
            }else{
                Main.getInstance().getMatchManager().addToQueue(p, ladder, false);
            }
        }

        getPlayer().closeInventory();
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        GUI.playerGUI.remove(getPlayer());
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {

    }

    @Override
    public void onDrag(InventoryDragEvent e) {

    }

}

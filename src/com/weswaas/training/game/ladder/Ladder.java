package com.weswaas.training.game.ladder;

import com.weswaas.training.kit.Kit;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 01/12/2016.
 */
public class Ladder {

    private String name;
    private ItemStack icon;
    private Kit kit;
    private boolean regen;
    private boolean playable = true;

    public Ladder(String name, ItemStack icon, Kit kit, boolean regen){
        this.name = name;
        this.icon = icon;
        this.kit = kit;
        this.regen = regen;
    }

    public String getName(){
        return this.name;
    }

    public Kit getDefaultKit(){
        return this.kit;
    }

    public ItemStack getIcon(){
        return this.icon;
    }

    public boolean canRegen(){
        return this.regen;
    }

    public boolean isPlayable(){
        return this.playable;
    }

    public void setPlayable(boolean playable){
        this.playable = playable;
    }

    public void setKit(Kit kit){
        this.kit = kit;

    }

}

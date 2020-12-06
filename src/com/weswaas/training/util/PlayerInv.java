package com.weswaas.training.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by Weswas on 01/12/2016.
 */
public class PlayerInv {

    private ItemStack[] contents;
    private ItemStack[] armorContents;

    public PlayerInv() {
    }

    public PlayerInv(ItemStack[] contents, ItemStack[] armorContents) {
        this.contents = contents;
        this.armorContents = armorContents;
    }

    public static PlayerInv fromPlayerInventory(PlayerInventory inv) {
        return new PlayerInv(inv.getContents(), inv.getArmorContents());
    }

    public ItemStack[] getContents() {
        return this.contents;
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public ItemStack[] getArmorContents() {
        return this.armorContents;
    }

    public void setArmorContents(ItemStack[] armorContents) {
        this.armorContents = armorContents;
    }

}

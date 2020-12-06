package com.weswaas.training.kit.kits.other;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 26/12/2016.
 */
public class SG extends Kit{

    public SG(){
        super("SG");
    }

    public void register(){

        setHelmet(new ItemStack(Material.IRON_HELMET));
        setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

        setItem(0, new ItemStack(Material.STONE_SWORD));
        setItem(1, new ItemStack(Material.GOLDEN_APPLE));
        setItem(2, new ItemStack(Material.GOLDEN_CARROT, 2));
        setItem(3, new ItemBuilder(Material.FLINT_AND_STEEL).durability(7).build());
        setItem(4, new ItemStack(Material.COOKED_BEEF, 3));
        setItem(5, new ItemStack(Material.BREAD, 1));
        setItem(6, new ItemStack(Material.BOW));
        setItem(9, new ItemStack(Material.ARROW, 16));

    }

}

package com.weswaas.training.kit.kits.soup;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 26/12/2016.
 */
public class SuperSoup extends Kit{

    public SuperSoup(){
        super("SuperSoup");
    }

    public void register(){

        setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build());
        setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).build());
        setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).build());
        setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build());

        setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 4).setUnbreakable(true).build());
        setItem(1, new ItemBuilder(Material.GOLDEN_APPLE).amount(12).build());
        setItem(2, new ItemBuilder(Material.FISHING_ROD).setUnbreakable(true).build());
        setItem(3, new ItemBuilder(Material.IRON_SWORD).setUnbreakable(true).enchantment(Enchantment.FIRE_ASPECT, 1).build());

        for(int i = 4; i < 32; i++){
            setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
        }

        for(int i = 32; i < 36; i++){
            setItem(i, new ItemStack(Material.POTION, 1, (short)8226));
        }

    }

}

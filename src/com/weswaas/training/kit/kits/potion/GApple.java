package com.weswaas.training.kit.kits.potion;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 26/12/2016.
 */
public class GApple extends Kit{

    public GApple(){
        super("GApple");
    }

    public void register(){

        setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());

        setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 3).enchantment(Enchantment.FIRE_ASPECT, 2).setUnbreakable(true).build());
        setItem(1, new ItemStack(Material.GOLDEN_APPLE, 64, (short)1));

        setItem(2, new ItemStack(Material.POTION, 1, (short)8217));
        setItem(3, new ItemStack(Material.POTION, 1, (short)8211));

        setItem(29, new ItemStack(Material.POTION, 1, (short)8217));
        setItem(30, new ItemStack(Material.POTION, 1, (short)8211));

        setItem(4, new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setItem(5, new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setItem(6, new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setItem(7, new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());

    }

}

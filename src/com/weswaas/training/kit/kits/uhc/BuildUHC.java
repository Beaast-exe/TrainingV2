package com.weswaas.training.kit.kits.uhc;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 23/12/2016.
 */
public class BuildUHC extends Kit{

    public BuildUHC(){
        super("BuildUHC");
    }

    public void register(){

        setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_PROJECTILE, 2).setUnbreakable(true).build());
        setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build());
        setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build());
        setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_PROJECTILE, 2).setUnbreakable(true).build());

        setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 3).setUnbreakable(true).build());
        setItem(1, new ItemBuilder(Material.GOLDEN_APPLE).amount(3).name(ChatColor.GOLD + "Golden Head").build());
        setItem(2, new ItemBuilder(Material.FISHING_ROD).setUnbreakable(true).build());
        setItem(3, new ItemStack(Material.LAVA_BUCKET));
        setItem(4, new ItemBuilder(Material.DIAMOND_AXE).setUnbreakable(true).build());
        setItem(5, new ItemStack(Material.GOLDEN_APPLE, 6));
        setItem(6, new ItemBuilder(Material.BOW).enchantment(Enchantment.ARROW_DAMAGE, 2).build());
        setItem(7, new ItemStack(Material.WOOD, 64));
        setItem(8, new ItemStack(Material.WATER_BUCKET));
        setItem(9, new ItemStack(Material.ARROW, 20));
        setItem(10, new ItemStack(Material.WOOD, 64));
        setItem(11, new ItemStack(Material.LAVA_BUCKET));
        setItem(12, new ItemStack(Material.WATER_BUCKET));

    }

}

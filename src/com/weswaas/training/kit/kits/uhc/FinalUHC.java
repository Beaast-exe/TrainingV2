package com.weswaas.training.kit.kits.uhc;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 26/12/2016.
 */
public class FinalUHC extends Kit{

    public FinalUHC(){
        super("FinalUHC");
    }

    public void register(){

        setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).durability(155).build());
        setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).durability(320).build());
        setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).durability(280).build());
        setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).durability(220).build());

        setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 4).setUnbreakable(true).build());
        setItem(1, new ItemBuilder(Material.GOLDEN_APPLE).amount(7).name(ChatColor.GOLD + "Golden Head").build());
        setItem(2, new ItemBuilder(Material.FISHING_ROD).setUnbreakable(true).build());
        setItem(3, new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.FIRE_ASPECT, 1).setUnbreakable(true).build());
        setItem(30, new ItemBuilder(Material.DIAMOND_AXE).setUnbreakable(true).build());
        setItem(5, new ItemStack(Material.GOLDEN_APPLE, 20));
        setItem(7, new ItemStack(Material.WOOD, 64));
        setItem(25, new ItemStack(Material.WOOD, 64));
        setItem(34, new ItemStack(Material.WOOD, 64));
        setItem(28, new ItemStack(Material.COOKED_BEEF, 64));
        setItem(8, new ItemStack(Material.WATER_BUCKET));
        setItem(35, new ItemStack(Material.WATER_BUCKET));
        setItem(26, new ItemStack(Material.WATER_BUCKET));
        setItem(17, new ItemStack(Material.WATER_BUCKET));
        setItem(9, new ItemStack(Material.ARROW, 32));
        setItem(4, new ItemStack(Material.LAVA_BUCKET));
        setItem(31, new ItemStack(Material.LAVA_BUCKET));
        setItem(22, new ItemStack(Material.LAVA_BUCKET));
        setItem(14, new ItemStack(Material.FLINT_AND_STEEL));

        setItem(9, new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).durability(150).build());
        setItem(10, new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).durability(150).build());
        setItem(11, new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).durability(150).build());
        setItem(12, new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).durability(150).build());

        setItem(18, new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).durability(300).build());
        setItem(19, new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).durability(300).build());
        setItem(20, new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).durability(300).build());
        setItem(21, new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).durability(300).build());

    }

}

package com.weswaas.training.kit.kits.uhc;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 26/12/2016.
 */
public class Archer extends Kit{

    public Archer(){
        super("Archer");
    }

    public void register(){

        setHelmet(new ItemBuilder(Material.LEATHER_HELMET).enchantment(Enchantment.PROTECTION_PROJECTILE, 2).setUnbreakable(true).build());
        setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).enchantment(Enchantment.PROTECTION_PROJECTILE, 2).setUnbreakable(true).build());
        setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).enchantment(Enchantment.PROTECTION_PROJECTILE, 2).setUnbreakable(true).build());
        setBoots(new ItemBuilder(Material.LEATHER_BOOTS).enchantment(Enchantment.PROTECTION_PROJECTILE, 2).setUnbreakable(true).build());

        setItem(0, new ItemBuilder(Material.BOW).enchantment(Enchantment.ARROW_INFINITE, 1).setUnbreakable(true).build());
        setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
        setItem(2, new ItemStack(Material.ENDER_PEARL, 3));
        setItem(9, new ItemStack(Material.ARROW, 1));

    }

}

package com.weswaas.training.kit.kits.potion;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 26/12/2016.
 */
public class NoDebuff extends Kit{

    public NoDebuff(){
        super("NoDebuff");
    }

    public void register(){

        setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());

        setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 3).enchantment(Enchantment.FIRE_ASPECT, 2).setUnbreakable(true).build());
        setItem(1, new ItemStack(Material.COOKED_BEEF, 64));
        setItem(2, new ItemStack(Material.ENDER_PEARL, 16));

        for(int i = 3; i < 31; i++){
            setItem(i, new ItemStack(Material.POTION, 1, (short)16421));
        }

        for(int i = 31; i  < 33; i++){
            setItem(i, new ItemStack(Material.POTION, 1, (short)8259));
        }

        for(int i = 33; i < 36; i++){
            setItem(i, new ItemStack(Material.POTION, 1, (short)8226));
        }

    }

}

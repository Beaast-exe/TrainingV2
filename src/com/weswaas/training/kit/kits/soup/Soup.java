package com.weswaas.training.kit.kits.soup;

import com.weswaas.training.kit.Kit;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Weswas on 26/12/2016.
 */
public class Soup extends Kit{

    public Soup(){
        super("Soup");
    }

    public void register(){

        setHelmet(new ItemBuilder(Material.IRON_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        setBoots(new ItemBuilder(Material.IRON_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());

        setItem(0, new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL, 2).setUnbreakable(true).build());
        setItem(1, new ItemStack(Material.COOKED_BEEF, 64));

        for(int i = 2; i < 32; i++){
            setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
        }

        for(int i = 32; i < 36; i++){
            setItem(i, new ItemStack(Material.POTION, 1, (short)8226));
        }

    }

}

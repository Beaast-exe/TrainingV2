package com.weswaas.training.util;

import com.weswaas.training.Main;
import com.weswaas.training.game.ladder.Ladder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Weswas on 01/12/2016.
 */
public class InventoryUtil {

    public static void setLobbyItems(Player p){

        ItemStack ranked = new ItemBuilder(Material.DIAMOND_SWORD).name(ChatColor.DARK_AQUA + "Ranked match").build();
        ItemStack unranked = new ItemBuilder(Material.IRON_SWORD).name(ChatColor.DARK_AQUA + "Unranked match").build();
        ItemStack kiteditor = new ItemBuilder(Material.BOOK).name(ChatColor.DARK_AQUA + "Kit Editor").build();

        p.getInventory().setItem(0, ranked);
        p.getInventory().setItem(1, unranked);
        p.getInventory().setItem(8, kiteditor);
        p.updateInventory();

    }

    public static Inventory getMatchGUI(boolean ranked){
        Inventory inv = Bukkit.createInventory(null, 18, ChatColor.DARK_AQUA + "Play " + (ranked ? "a ranked" : "an unranked") + " match");

        int slot = 0;
        for(Ladder l : Main.getInstance().getLadderManager().getLadders()){
            inv.setItem(slot, l.getIcon());
            slot++;
        }

        return inv;
    }

    public static PlayerInv getKit(String name){

        FileConfiguration config = Main.getInstance().getConfig();
        String s = config.getString("kits.default."+name.toLowerCase());
        return playerInventoryFromString(s);

    }

    public static PlayerInv playerInventoryFromString(String in) {
        PlayerInv inv = new PlayerInv();
        String[] data = in.split("\\|");
        ItemStack[] armor = new ItemStack[data[0].split(";").length];
        for (int i = 0; i < data[0].split(";").length; ++i) {
            armor[i] = InventoryUtil.itemStackFromString(data[0].split(";")[i]);
        }
        inv.setArmorContents(armor);
        ItemStack[] contents = new ItemStack[data[1].split(";").length];
        for (String s : data[1].split(";")) {
            int slot = Integer.parseInt(s.split("#")[0]);
            contents[slot] = s.split("#").length == 1 ? null : InventoryUtil.itemStackFromString(s.split("#")[1]);
        }
        inv.setContents(contents);
        return inv;
    }

    @SuppressWarnings({ "deprecation", "unused" })
    public static ItemStack itemStackFromString(String in) {
        String[] data;
        ItemStack item = null;
        ItemMeta meta = null;
        block16 : for (String itemInfo : data = in.split(":")) {
            String[] itemAttribute = itemInfo.split("@");
            switch (itemAttribute[0]) {
                case "t": {
                    item = new ItemStack(Material.getMaterial((int)Integer.valueOf(itemAttribute[1])));
                    meta = item.getItemMeta();
                    continue block16;
                }
                case "d": {
                    if (item == null) continue block16;
                    item.setDurability(Short.valueOf(itemAttribute[1]).shortValue());
                    continue block16;
                }
                case "a": {
                    if (item == null) continue block16;
                    item.setAmount(Integer.valueOf(itemAttribute[1]).intValue());
                    continue block16;
                }
                case "e": {
                    if (item == null) continue block16;
                    item.addEnchantment(Enchantment.getById((int)Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]).intValue());
                    continue block16;
                }
                case "dn": {
                    if (meta == null) continue block16;
                    meta.setDisplayName(itemAttribute[1]);
                    continue block16;
                }
                case "l": {
                    itemAttribute[1] = itemAttribute[1].replace("[", "");
                    itemAttribute[1] = itemAttribute[1].replace("]", "");
                    List<String> lore = Arrays.asList(itemAttribute[1].split(","));
                    for (int x = 0; x < lore.size(); ++x) {
                        String s = lore.get(x);
                        if (s == null || s.toCharArray().length == 0) continue;
                        if (s.charAt(0) == ' ') {
                            s = s.replaceFirst(" ", "");
                        }
                        lore.set(x, s);
                    }
                    if (meta == null) continue block16;
                    meta.setLore(lore);
                }
            }
        }
        if (meta != null && (meta.hasDisplayName() || meta.hasLore())) {
            item.setItemMeta(meta);
        }
        return item;
    }

    public static String playerInventoryToString(PlayerInventory inv) {
        StringBuilder builder = new StringBuilder();
        ItemStack[] armor = inv.getArmorContents();
        builder.append(InventoryUtil.itemStackToString(armor[0])).append(";");
        builder.append(InventoryUtil.itemStackToString(armor[1])).append(";");
        builder.append(InventoryUtil.itemStackToString(armor[2])).append(";");
        builder.append(InventoryUtil.itemStackToString(armor[3]));
        builder.append("|");
        for (int i = 0; i < inv.getContents().length; ++i) {
            builder.append(i).append("#").append(InventoryUtil.itemStackToString(inv.getContents()[i])).append(i == inv.getContents().length - 1 ? "" : ";");
        }
        return builder.toString();
    }

    public static String playerInventoryToString(PlayerInv inv) {
        StringBuilder builder = new StringBuilder();
        ItemStack[] armor = inv.getArmorContents();
        builder.append(InventoryUtil.itemStackToString(armor[0])).append(";");
        builder.append(InventoryUtil.itemStackToString(armor[1])).append(";");
        builder.append(InventoryUtil.itemStackToString(armor[2])).append(";");
        builder.append(InventoryUtil.itemStackToString(armor[3]));
        builder.append("|");
        for (int i = 0; i < inv.getContents().length; ++i) {
            builder.append(i).append("#").append(InventoryUtil.itemStackToString(inv.getContents()[i])).append(i == inv.getContents().length - 1 ? "" : ";");
        }
        return builder.toString();
    }

    @SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
    public static String itemStackToString(ItemStack item) {
        StringBuilder builder = new StringBuilder();
        if (item != null) {
            Map isEnch;
            String isType = String.valueOf(item.getType().getId());
            builder.append("t@").append(isType);
            if (item.getDurability() != 0) {
                String isDurability = String.valueOf(item.getDurability());
                builder.append(":d@").append(isDurability);
            }
            if (item.getAmount() != 1) {
                String isAmount = String.valueOf(item.getAmount());
                builder.append(":a@").append(isAmount);
            }
            if ((isEnch = item.getEnchantments()).size() > 0) {
                Set<Map.Entry> entries = isEnch.entrySet();
                for (Map.Entry ench : entries) {
                    builder.append(":e@").append(((Enchantment)ench.getKey()).getId()).append("@").append(ench.getValue());
                }
            }
            if (item.hasItemMeta()) {
                ItemMeta imeta = item.getItemMeta();
                if (imeta.hasDisplayName()) {
                    builder.append(":dn@").append(imeta.getDisplayName());
                }
                if (imeta.hasLore()) {
                    builder.append(":l@").append(imeta.getLore());
                }
            }
        }
        return builder.toString();
    }

}

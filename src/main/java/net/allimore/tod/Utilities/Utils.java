package net.allimore.tod.Utilities;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static String USE_SUFFIX = " uses remaining.";

    public static boolean ItemsMatch(ItemStack item, Material targetMaterial, String targetName){
        if(item.getType() != targetMaterial) { return false; }
        if(! item.getItemMeta().getDisplayName().equals(targetName)) { return false; }
        return true;
    }

    public static ItemStack ConstructItemStack(String name, Material material, ArrayList<String> lore){
        return ConstructItemStack(name, material, lore, 1);
    }

    public static ItemStack ConstructItemStack(String name, Material material, ArrayList<String> lore, int quantity){
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();

        meta.setLore(lore);
        meta.setDisplayName(name);

        item.setItemMeta(meta);

        return item;
    }

    public static String ConstructUseString(int uses){
        return ChatColor.DARK_PURPLE + Integer.toString(uses) + USE_SUFFIX;
    }

    public static int ReadUsesFromLore(ItemStack item, int line){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        return ReadUsesFromString(lore.get(line));
    }

    public static int ReadUsesFromString(String string){
        char[] charArray = string.toCharArray();
        int firstSpaceIndex = 0;

        for(int i = 0; i < charArray.length; i++){
            if(Character.isSpaceChar(charArray[i])){
                firstSpaceIndex = i;
                break;
            }
        }

        return Integer.parseInt(string.substring(2, firstSpaceIndex));
    }

    public static boolean ReadBooleanFromLine(ItemStack item, String prefix, int line){
       List<String> lore = item.getLore();
       String lineString = lore.get(line);
       String subString = lineString.substring(prefix.length());
       return Boolean.valueOf(subString);
    }

    public static void SetBooleanOnLine(ItemStack item, String prefix, int line, boolean bool){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = item.getLore();
        lore.set(line, prefix + Boolean.toString(bool));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static ItemStack UpdateUseLine(ItemStack item, int line, int uses){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(line, ConstructUseString(uses));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static boolean CanSpilt(ItemStack item){
        return item.getAmount() > 1;
    }

    public static ItemStack SplitOffCharmStackMain(Player player, ItemStack templateItem, CharmUseInfo useInfo){
        ItemStack item = player.getInventory().getItemInMainHand();
        item.setAmount(item.getAmount() - 1);
        player.getInventory().setItemInMainHand(item);

        UpdateUseLine(templateItem, useInfo.line, useInfo.uses);
        player.getInventory().addItem(templateItem);
        return templateItem;
    }

    public static ItemStack SplitOffCharmStackOff(Player player, ItemStack templateItem, CharmUseInfo useInfo){
        ItemStack item = player.getInventory().getItemInOffHand();
        item.setAmount(item.getAmount() - 1);
        player.getInventory().setItemInOffHand(item);

        UpdateUseLine(templateItem, useInfo.line, useInfo.uses);
        player.getInventory().addItem(templateItem);
        return templateItem;
    }

    public static void ConsumeFromMainHand(Player player){
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getAmount() > 1){
            item.setAmount(item.getAmount() - 1);
        }else{
            player.getInventory().setItemInMainHand(null);
        }
    }

    public static void ConsumeFromOffHand(Player player){
        ItemStack item = player.getInventory().getItemInOffHand();

        if(item.getAmount() > 1){
            item.setAmount(item.getAmount() - 1);
        }else{
            player.getInventory().setItemInOffHand(null);
        }
    }

    public static boolean TryChance(double chance){
        return Math.random() > 1 - chance;
    }
}

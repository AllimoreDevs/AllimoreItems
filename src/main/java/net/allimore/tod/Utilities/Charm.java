package net.allimore.tod.Utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Charm {
    public String NAME;
    public Material MATERIAL;

    public Charm(String NAME, Material MATERIAL){
        this.NAME = NAME;
        this.MATERIAL = MATERIAL;
    }

    public boolean ItemMatch(ItemStack item){
        if(item.getType() != MATERIAL) { return false; }
        if(! item.getItemMeta().getDisplayName().equals(NAME)) { return false; }
        return true;
    }

    public boolean ItemMatchMainHand(Player player){
        if(player.getInventory().getItemInMainHand() == null) { return false; }
        return ItemMatch(player.getInventory().getItemInMainHand());
    }

    public boolean ItemMatchOffHand(Player player){
        if(player.getInventory().getItemInOffHand() == null) { return false; }
        return ItemMatch(player.getInventory().getItemInOffHand());
    }
}

package net.allimore.tod.Utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Charm {
    public String NAME;
    public Material MATERIAL;

    public static ArrayList<Action> GENERIC_ACTIONS = new ArrayList<Action>(){{ add(Action.RIGHT_CLICK_AIR); add(Action.RIGHT_CLICK_BLOCK); }};

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

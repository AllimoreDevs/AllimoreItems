package net.allimore.tod;

import net.allimore.tod.Utilities.Utils;
import net.allimore.tod.items.ToolAurrynBreaker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class CraftListener implements Listener {

    @EventHandler
    public void OnPrepEnchant(PrepareItemEnchantEvent event){
        if(Utils.ItemsMatch(event.getItem(), ToolAurrynBreaker.MATERIAL, ToolAurrynBreaker.NAME)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnPrepareAnvil(PrepareAnvilEvent event){
        AnvilInventory inventory = event.getInventory();
        ItemStack[] items = inventory.getContents();
        for (ItemStack item : items){
            if(item == null) { continue; }
            if(Utils.ItemsMatch(item, ToolAurrynBreaker.MATERIAL, ToolAurrynBreaker.NAME)){
                event.setResult(null);
            }
        }
    }
}

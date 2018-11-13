package net.allimore.tod;

import net.allimore.tod.Utilities.Utils;
import net.allimore.tod.items.ToolAllimoreBreaker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event){
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if(item != null){
            if(Utils.ItemsMatch(item, ToolAllimoreBreaker.MATERIAL, ToolAllimoreBreaker.NAME)) { ToolAllimoreBreaker.Run(event); }
        }
    }
}

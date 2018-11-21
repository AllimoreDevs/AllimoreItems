package net.allimore.tod;

import net.allimore.tod.Utilities.Interfaces.ITriggerBlockBreak;
import net.allimore.tod.Utilities.Triggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BlockListener implements Listener {

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event){
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if(item != null){
            ArrayList<ITriggerBlockBreak> triggers = Triggers.GetBlockBreakTriggers();
            for(ITriggerBlockBreak trigger : triggers){
                if(trigger.GetCharm().ItemMatch(item)){
                    trigger.RunTrigger(event);
                }
            }
        }
    }
}

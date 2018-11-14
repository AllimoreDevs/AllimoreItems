package net.allimore.tod;

import net.allimore.tod.Utilities.Interfaces.ITriggerConsume;
import net.allimore.tod.Utilities.Triggers;
import net.allimore.tod.Utilities.Utils;
import net.allimore.tod.items.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class ConsumeListener implements Listener {

    private JavaPlugin plugin;

    public ConsumeListener(JavaPlugin plugin){ this.plugin = plugin; }

    @EventHandler
    public void OnPlayerConsume (PlayerItemConsumeEvent event){
        if( HandleSpectral(event) ){ return; }

        ArrayList<ITriggerConsume> triggers = Triggers.GetConsumeTriggers();
        for(ITriggerConsume trigger : triggers){
            if(trigger.GetCharm().ItemMatch(event.getItem())){
                trigger.RunTrigger(event);
                if(event.isCancelled()) { return; }
            }
        }
    }

    private boolean HandleSpectral(PlayerItemConsumeEvent event){
        if( CharmIncorporeal.SPECTRAL_PLAYERS.contains(event.getPlayer()) ){
            event.setCancelled(true);
            event.getPlayer().sendMessage(CharmIncorporeal.CANT_INTERACT);
            return true;
        }
        return false;
    }
}

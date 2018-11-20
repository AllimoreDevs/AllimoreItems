package net.allimore.tod;

import net.allimore.tod.Utilities.Interfaces.ITriggerEntityInteract;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import net.allimore.tod.Utilities.Triggers;
import net.allimore.tod.items.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class InteractListener implements Listener {

    private JavaPlugin plugin;

    public InteractListener(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event){
        if(CharmIncorporeal.HandleInteractCancel(event)) return;

        ArrayList<ITriggerInteract> triggers = Triggers.GetInteractTriggers();
        for(ITriggerInteract trigger : triggers){
            if(trigger.GetAction().contains(event.getAction())){
                if(trigger.GetCharm().ItemMatch(event.getItem())){
                    trigger.RunTrigger(event);
                    return;
                }
            }
        }

        if(event.hasItem()){
            if(ScrollTownPortal.IsScroll(event.getItem(), event)) { ScrollTownPortal.RunScroll(event); return;  }
            if(CharmTownPortal.IsCharm(event.getItem(), event)) { CharmTownPortal.RunCharm(event); return;  }
        }
    }

    @EventHandler
    public void OnPlayerInteractEntity(PlayerInteractEntityEvent event){
        ArrayList<ITriggerEntityInteract> triggers = Triggers.GetEntityInteractTriggers();
        for(ITriggerEntityInteract trigger : triggers){
            trigger.RunTrigger(event);
        }
    }
}

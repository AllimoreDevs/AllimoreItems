package net.allimore.tod;

import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamage;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamageEntity;
import net.allimore.tod.Utilities.Triggers;
import net.allimore.tod.items.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class DamageListener implements Listener {

    private JavaPlugin plugin;

    public DamageListener(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void OnEntityDamage(EntityDamageEvent event){
        ArrayList<ITriggerRecieveDamage> triggers = Triggers.GetRecieveDamageTriggers();
        for(ITriggerRecieveDamage trigger : triggers){
            trigger.RunTrigger(event);
            if(event.isCancelled()) { return; }
        }
    }

    @EventHandler
    public void OnEntityDamageEntity(EntityDamageByEntityEvent event){

        ArrayList<ITriggerRecieveDamageEntity> triggers = Triggers.GetRecieveDamageEntityTriggers();
        for(ITriggerRecieveDamageEntity trigger : triggers){
            trigger.RunTrigger(event);
            if(event.isCancelled()){
                return;
            }
        }
    }
}

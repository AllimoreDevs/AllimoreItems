package net.allimore.tod;

import net.allimore.tod.items.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageListener implements Listener {

    private JavaPlugin plugin;

    public DamageListener(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void OnEntityDamage(EntityDamageEvent event){
        if( !(event.getEntity() instanceof Player) ){ return; }
        Player player = (Player)event.getEntity();

        if ( HandleSpectral(event, player)){ return; }
        if( HandleImmunity(event, player)) { return; }


        if(CharmZephyr.HasCharm(player) && (player.getHealth() - event.getFinalDamage() <= 0) ) { CharmZephyr.RunCharm(event); }
        if(CharmResurection.HasCharm(player) && (player.getHealth() - event.getFinalDamage() <= 0) ) { CharmResurection.RunCharm(event); }
    }

    @EventHandler
    public void OnEntityDamageEntity(EntityDamageByEntityEvent event){
        if( CharmIncorporeal.SPECTRAL_PLAYERS.contains(event.getDamager())){
            event.setCancelled(true);
            return;
        }
    }

    private boolean HandleSpectral(EntityDamageEvent event, Player player){
        if( CharmIncorporeal.SPECTRAL_PLAYERS.contains(player) ){
            event.setCancelled(true);
            return true;
        }
        return false;
    }

    private boolean HandleImmunity (EntityDamageEvent event, Player player) {
        if (CharmPyroCloak.FIRE_IMMUNE_PLAYERS.contains(player)){
            if(CharmPyroCloak.ImuneTo(event.getCause())){
                event.setCancelled(true);
                return true;
            }
        }

        if (FoodMundusExtract.POISON_IMMUNE_PLAYERS.contains(player)){
            if(FoodMundusExtract.IsImmune(event.getCause())){
                event.setCancelled(true);
                return true;
            }
        }

        return false;
    }
}

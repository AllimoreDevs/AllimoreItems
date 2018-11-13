package net.allimore.tod;

import net.allimore.tod.Utilities.Utils;
import net.allimore.tod.items.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ConsumeListener implements Listener {

    private JavaPlugin plugin;

    public ConsumeListener(JavaPlugin plugin){ this.plugin = plugin; }

    @EventHandler
    public void OnPlayerConsume (PlayerItemConsumeEvent event){
        if( HandleSpectral(event) ){ return; }

        if(Utils.ItemsMatch(event.getItem(), PotionElixirOfLife.MATERIAL, PotionElixirOfLife.NAME)){
            PotionElixirOfLife.RunPotion(event);
            return;
        }
        if(Utils.ItemsMatch(event.getItem(), PotionPhilterOfLife.MATERIAL, PotionPhilterOfLife.NAME)){
            PotionPhilterOfLife.RunPotion(event);
            return;
        }
        if(Utils.ItemsMatch(event.getItem(), FoodSilverFruit.MATERIAL, FoodSilverFruit.NAME)){
            FoodSilverFruit.RunFood(event);
            return;
        }
        if(Utils.ItemsMatch(event.getItem(), FoodSilverJuice.MATERIAL, FoodSilverJuice.NAME)){
            FoodSilverJuice.Run(event);
            return;
        }
        if(Utils.ItemsMatch(event.getItem(), FoodMundusRoots.MATERIAL, FoodMundusRoots.NAME)) {
            FoodMundusRoots.Run(event);
            return;
        }
        if(Utils.ItemsMatch(event.getItem(), FoodMundusExtract.MATERIAL, FoodMundusExtract.NAME)) {
            FoodMundusExtract.Run(event);
            return;
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

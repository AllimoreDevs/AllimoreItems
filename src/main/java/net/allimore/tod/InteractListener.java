package net.allimore.tod;

import net.allimore.tod.Utilities.Utils;
import net.allimore.tod.items.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class InteractListener implements Listener {

    private JavaPlugin plugin;

    public InteractListener(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event){
        if( CharmIncorporeal.SPECTRAL_PLAYERS.contains(event.getPlayer()) ){
            event.setCancelled(true);
            event.getPlayer().sendMessage(CharmIncorporeal.CANT_INTERACT);
            return;
        }

        if(event.hasItem()){
            if(Utils.ItemsMatch(event.getItem(), CharmSun.MATERIAL, CharmSun.NAME)){ CharmSun.RunCharm(event); return; }
            if(Utils.ItemsMatch(event.getItem(), CharmClearSky.MATERIAL, CharmClearSky.NAME)) { CharmClearSky.RunCharm(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), CharmRain.MATERIAL, CharmRain.NAME)) { CharmRain.RunCharm(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), CharmMagicMirror.MATERIAL, CharmMagicMirror.NAME)) { CharmMagicMirror.RunCharm(event); return;  }
            if(ScrollTownPortal.IsScroll(event.getItem(), event)) { ScrollTownPortal.RunScroll(event); return;  }
            if(CharmTownPortal.IsCharm(event.getItem(), event)) { CharmTownPortal.RunCharm(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), ScrollHome.MATERIAL, ScrollHome.NAME)) { ScrollHome.RunScroll(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), CharmReturn.MATERIAL, CharmReturn.NAME)) { CharmReturn.RunCharm(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), ScrollWarp.MATERIAL, ScrollWarp.NAME)) { ScrollWarp.Run(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), CharmWarp.MATERIAL, CharmWarp.NAME)) { CharmWarp.Run(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), CharmIncorporeal.MATERIAL, CharmIncorporeal.NAME)) { CharmIncorporeal.Run(event); return;  }
            if(Utils.ItemsMatch(event.getItem(), CharmPyroCloak.MATERIAL, CharmPyroCloak.NAME)) { CharmPyroCloak.Run(event); return; }
            if(Utils.ItemsMatch(event.getItem(), CharmValkyrie.MATERIAL, CharmValkyrie.NAME)) { CharmValkyrie.Run(event.getPlayer()); }
        }
    }

    @EventHandler
    public void OnPlayerInteractEntity(PlayerInteractEntityEvent event){
        if(CharmTame.HoldingCharm(event.getPlayer())) { CharmTame.RunCharm(event); }
    }
}

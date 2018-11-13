package net.allimore.tod;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.event.PlayerChangePlotEvent;
import net.allimore.tod.items.CharmValkyrie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownyListener implements Listener {

    @EventHandler
    public void OnPlayerChangePlot(PlayerChangePlotEvent event){
        Player player = event.getPlayer();

        if( CharmValkyrie.FLYING_PLAYERS.contains(player)){
            if(! CharmValkyrie.IsInOwnTown(player, event.getTo())){
                // Remove flying
                CharmValkyrie.ToggleFlight(player);
            }
        }
    }
}

package net.allimore.tod.Utilities.Interfaces;

import net.allimore.tod.Utilities.Charm;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ITriggerInteract {
        void RunTrigger(PlayerInteractEvent event);
        Charm GetCharm();
        Action GetAction();
}

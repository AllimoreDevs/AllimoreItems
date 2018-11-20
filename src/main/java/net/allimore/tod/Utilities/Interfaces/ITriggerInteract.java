package net.allimore.tod.Utilities.Interfaces;

import net.allimore.tod.Utilities.Charm;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public interface ITriggerInteract {
        void RunTrigger(PlayerInteractEvent event);
        Charm GetCharm();
        ArrayList<Action> GetAction();
}

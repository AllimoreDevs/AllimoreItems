package net.allimore.tod.Utilities.Interfaces;

import net.allimore.tod.Utilities.Charm;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface ITriggerConsume {
    void RunTrigger(PlayerItemConsumeEvent event);
    Charm GetCharm();
}

package net.allimore.tod.Utilities.Interfaces;

import net.allimore.tod.Utilities.Charm;
import net.allimore.tod.Utilities.Triggers;
import org.bukkit.event.block.BlockBreakEvent;

public interface ITriggerBlockBreak {
    void RunTrigger(BlockBreakEvent event);
    Charm GetCharm();
}

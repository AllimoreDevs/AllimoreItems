package net.allimore.tod.Utilities.Interfaces;

import net.allimore.tod.Utilities.Triggers;
import org.bukkit.event.entity.EntityDamageEvent;

public interface ITriggerRecieveDamage {
    void RunTrigger(EntityDamageEvent event);
}

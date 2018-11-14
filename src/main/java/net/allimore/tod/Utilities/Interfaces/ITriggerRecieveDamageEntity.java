package net.allimore.tod.Utilities.Interfaces;

import net.allimore.tod.Utilities.Triggers;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface ITriggerRecieveDamageEntity {
    void RunTrigger(EntityDamageByEntityEvent event);
}

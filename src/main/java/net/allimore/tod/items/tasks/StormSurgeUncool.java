package net.allimore.tod.items.tasks;

import net.allimore.tod.Utilities.Utils;
import net.allimore.tod.items.ToolStormSurge;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class StormSurgeUncool extends BukkitRunnable {
    ItemStack item;
    int line;
    String prefix;

    public StormSurgeUncool(ItemStack item, int line, String prefix){
        this.item = item;
        this.line = line;
        this.prefix = prefix;
    }

    @Override
    public void run() {
        Utils.SetBooleanOnLine(item, prefix, line, false);
        ToolStormSurge.CoolDownTasks.remove(this);
    }
}

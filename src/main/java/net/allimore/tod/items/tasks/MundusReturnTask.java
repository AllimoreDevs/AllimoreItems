package net.allimore.tod.items.tasks;

import net.allimore.tod.items.FoodMundusExtract;
import org.bukkit.scheduler.BukkitRunnable;

public class MundusReturnTask extends BukkitRunnable {
    private String playerName;

    public MundusReturnTask(String playerName){
        this.playerName = playerName;
    }

    @Override
    public void run() {
        FoodMundusExtract.EndImmune(playerName);
        this.cancel();
    }
}

package net.allimore.tod.items.tasks;

import net.allimore.tod.items.CharmIncorporeal;
import org.bukkit.scheduler.BukkitRunnable;

public class IncorpReturnTask extends BukkitRunnable {
    private String playerName;

    public IncorpReturnTask(String playerName){
        this.playerName = playerName;
    }

    @Override
    public void run() {
        CharmIncorporeal.EndSpectral(playerName);
        this.cancel();
    }
}

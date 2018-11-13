package net.allimore.tod.items.tasks;

import net.allimore.tod.items.CharmIncorporeal;
import net.allimore.tod.items.CharmPyroCloak;
import org.bukkit.scheduler.BukkitRunnable;

public class PyroReturnTask extends BukkitRunnable {
    private String playerName;

    public PyroReturnTask(String playerName){
        this.playerName = playerName;
    }

    @Override
    public void run() {
        CharmPyroCloak.End(playerName);
        this.cancel();
    }
}
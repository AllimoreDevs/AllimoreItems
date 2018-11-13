package net.allimore.tod.Utilities;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundInfo {
    public Sound sound;
    public float volume;
    public float pitch;

    public SoundInfo(Sound sound, float volume, float pitch){
        this.sound  = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void PlaySound(Player player, Location location){
        player.playSound(location, sound, volume, pitch);
    }

    public void PlaySound(Player player){
        PlaySound(player, player.getLocation());
    }
}

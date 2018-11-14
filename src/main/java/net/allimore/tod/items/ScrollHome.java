package net.allimore.tod.items;

import net.allimore.tod.AllimoreItems;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ScrollHome extends Charm implements ITriggerInteract {
    public static String NAME = ChatColor.GOLD + "Scroll of Home";
    public static Material MATERIAL = Material.PAPER;

    public static String LORE1 = CharmLang.LORE_COLOR + "Using this scroll will";
    public static String LORE2 = CharmLang.LORE_COLOR + "teleport you home.";

    public static String USE_STRING = CharmLang.GENERIC_CHARM_TELEPORT;
    public static String CONSUME_STRING = CharmLang.GENERIC_SCRROLL_CONSUME;
    private static String FAIL_STRING = CharmLang.GENERIC_FAIL_NEED_HOME;

    public static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    public static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public ScrollHome(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
    }

    public static ItemStack CreateHomeScroll(){
        ArrayList<String> lore = new ArrayList<String>() {{ add(LORE1); add(LORE2); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    private static boolean UseScroll(Player player) {
        try {
            Location home = AllimoreItems.ESSENTIALS.getUser(player).getHome("home");
            player.teleport(home);
            player.sendMessage(USE_STRING);
            player.sendMessage(CONSUME_STRING);
            USE_SOUND.PlaySound(player);
            return true;
        } catch (Exception e) {
            FAIL_SOUND.PlaySound(player);
            player.sendMessage(FAIL_STRING);
            return false;
        }
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        event.setCancelled(true);

        Player player = event.getPlayer();

        if( UseScroll(player) ){
            Utils.ConsumeFromMainHand(player);
        }
    }

    @Override
    public Charm GetCharm() {
        return this;
    }

    @Override
    public Action GetAction() {
        return Action.RIGHT_CLICK_AIR;
    }
}

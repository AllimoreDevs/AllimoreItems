package net.allimore.tod.items;

import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerEntityInteract;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CharmTame extends Charm implements ITriggerEntityInteract {
    public static String NAME = ChatColor.GOLD + "Charm of Taming";
    public static Material MATERIAL = Material.EMERALD;

    private static String LORE_STRING = ChatColor.DARK_PURPLE + "Instantly tames any untamed horse.";

    private static String USE_STRING = ChatColor.GREEN + "Energies streams away from the charm and swirls around the horse. It seems docile now.";
    private static String FAIL_STRING_NOT_HORSE = ChatColor.RED + "I need to right click a horse!";
    private static String FAIL_STRING_TAMED = ChatColor.RED + "This Horse is already tamed!";
    private static String FAIL_STRING_AGE = ChatColor.RED + "This Horse isn't old enough!";
    private static String FAIL_STRING_DEAD = ChatColor.RED + "It's dead Jim.";
    private static String CONSUME_STRING = ChatColor.RED + "The Charm crumbles in your hand.";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public CharmTame(){
        super(NAME, MATERIAL);
        Triggers.RegisterEntityInteractTrigger(this);
    }

    public static ItemStack CreateTameCharm(){
        ArrayList<String> lore = new ArrayList<String>() {{ add(LORE_STRING); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static boolean HoldingCharm(Player player){
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();

        return Utils.ItemsMatch(mainHandItem, MATERIAL, NAME);
    }

    private static boolean IsHorse(PlayerInteractEntityEvent event, Player player){
        if(!(event.getRightClicked() instanceof Horse) ){
            player.sendMessage(FAIL_STRING_NOT_HORSE);
            FAIL_SOUND.PlaySound(player);
            return false;
        }
        return true;
    }

    private static boolean CanTame(Horse horse, Player player){
        if(horse.isTamed()) {
            player.sendMessage(FAIL_STRING_TAMED);
            FAIL_SOUND.PlaySound(player);
            return false;}
        if(!horse.isAdult()) {
            player.sendMessage(FAIL_STRING_AGE);
            FAIL_SOUND.PlaySound(player);
            return false;}
        if(horse.isDead()) {
            player.sendMessage(FAIL_STRING_DEAD);
            FAIL_SOUND.PlaySound(player);
            return false; }
        return true;
    }

    @Override
    public void RunTrigger(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand() == null || ! super.ItemMatch(player.getInventory().getItemInMainHand()) ) { return; }
        if(!IsHorse(event, player)) { return;}

        Horse horse = (Horse)event.getRightClicked();
        if(!CanTame(horse, player)) { return; }

        horse.setOwner(player);
        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);

        player.sendMessage(CONSUME_STRING);
        Utils.ConsumeFromMainHand(player);
        event.setCancelled(true);
    }
}

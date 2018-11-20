package net.allimore.tod.items;

import net.allimore.tod.*;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CharmRain extends Charm implements ITriggerInteract {
    public static String NAME = ChatColor.BLUE + "Charm of Rain";
    public static Material MATERIAL = Material.LAPIS_LAZULI;

    private static int STARTING_USES = 5;

    private static String USE_MESSAGE =
            CharmLang.POSITIVE_COLOR + "The winds pickup and a furious storm emerges! %s" + CharmLang.POSITIVE_COLOR + " has changed the weather!";
    private static String FAIL_MESSAGE =
            CharmLang.NEGATIVE_COLOR + "It's already raining! Using this now would be a waste!";
    private static String WEAKEN_STRING = CharmLang.GENERIC_CHARM_WEAKEN;
    private static String BROKEN_STRING = CharmLang.GENERIC_CHARM_CONSUME;

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public CharmRain(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
    }

    public static ItemStack CreateRainCharm(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(Utils.ConstructUseString(STARTING_USES)); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    private static boolean IsNotRaining(Player player){
        if(player.getWorld().hasStorm()){
            player.sendMessage(FAIL_MESSAGE);
            FAIL_SOUND.PlaySound(player);
            return false;
        }
        return true;
    }

    private static void CreateStorm(Player player){
        AllimoreItems.SERVER.broadcastMessage(String.format(USE_MESSAGE, player.getDisplayName()));
        USE_SOUND.PlaySound(player);

        player.getWorld().setStorm(true);
    }

    public static void BreakCharm(Player player, PlayerInteractEvent event){
        // Remove the item from existence
        player.sendMessage(BROKEN_STRING);

        Utils.ConsumeFromMainHand(player);
        event.setCancelled(true);
    }

    public static void UpdateCharmUsesString(Player player, PlayerInteractEvent event, int uses){
        // Update the Item Lore string
        player.sendMessage(WEAKEN_STRING);

        Utils.UpdateUseLine(event.getItem(), 0, uses);
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(! IsNotRaining(player) ) { return; }
        CreateStorm(player);

        // Get Uses remaining
        int uses = Utils.ReadUsesFromLore(event.getItem(), 0);
        uses --;

        if(uses <= 0){
            BreakCharm(player, event);
        }else{
            if(Utils.CanSpilt(event.getItem())){
                CharmUseInfo useInfo = new CharmUseInfo(0, uses);
                Utils.SplitOffCharmStackMain(player, CreateRainCharm(), useInfo);
                player.sendMessage(WEAKEN_STRING);
            } else {
                UpdateCharmUsesString(player, event, uses);
            }
        }
    }

    @Override
    public Charm GetCharm() {
        return this;
    }

    @Override
    public ArrayList<Action> GetAction() {
        return CharmMagicMirror.GENERIC_ACTIONS;
    }
}
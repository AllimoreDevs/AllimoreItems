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

public class CharmSun extends Charm implements ITriggerInteract {
    public static String NAME = ChatColor.YELLOW + "Sun Charm";
    public static Material MATERIAL = Material.CLOCK;

    private static int STARTING_USES = 5;

    private static String USE_STRING = "%s" + CharmLang.POSITIVE_COLOR + " has shifted time to day!";
    private static String FAIL_STRING = CharmLang.NEGATIVE_COLOR + "It's already day, using this now would be a waste!";
    private static String WEAKEN_STRING = CharmLang.GENERIC_CHARM_WEAKEN;
    private static String BROKEN_STRING = CharmLang.GENERIC_CHARM_CONSUME;

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public CharmSun(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
    }

    public static ItemStack CreateSunCharm(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(Utils.ConstructUseString(STARTING_USES)); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static boolean CanUse(Player player){
        if(player.getWorld().getTime() <= 12000)
        {
            player.sendMessage(CharmSun.FAIL_STRING);
            FAIL_SOUND.PlaySound(player);
            return false;
        }
        return true;
    }

    public static void SetTimeToDay(Player player, PlayerInteractEvent event){
        // Notify the Player and change the world time
        AllimoreItems.SERVER.broadcastMessage(String.format(USE_STRING, player.getDisplayName()));

        USE_SOUND.PlaySound(player);

        player.getWorld().setTime(1000);
    }

    public static void BreakCharm(Player player, PlayerInteractEvent event){
        // Remove the item from existence
        player.sendMessage(CharmSun.BROKEN_STRING);

        Utils.ConsumeFromMainHand(player);
        event.setCancelled(true);
    }

    public static void UpdateCharmUsesString(Player player, ItemStack item, int uses){
        // Update the Item Lore string
        player.sendMessage(WEAKEN_STRING);

        Utils.UpdateUseLine(item, 0, uses);
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if( !CanUse(player) ){ return; }

        SetTimeToDay(player, event);

        // Get Uses remaining
        int uses = Utils.ReadUsesFromLore(event.getItem(), 0);
        uses --;

        if(uses <= 0){
            BreakCharm(player, event);
        }else{
            if(Utils.CanSpilt(event.getItem())){
                CharmUseInfo useInfo = new CharmUseInfo(0, uses);
                Utils.SplitOffCharmStackMain(event.getPlayer(), CreateSunCharm(), useInfo);
                player.sendMessage(WEAKEN_STRING);
            }else{
                UpdateCharmUsesString(player, event.getItem(), uses);
            }
        }
    }

    @Override
    public Action GetAction() {
        return Action.RIGHT_CLICK_AIR;
    }

    @Override
    public Charm GetCharm(){
        return this;
    }
}

package net.allimore.tod.items;

import com.earth2me.essentials.commands.WarpNotFoundException;
import net.allimore.tod.AllimoreItems;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import net.ess3.api.InvalidWorldException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CharmWarp extends Charm implements ITriggerInteract {
    public static String NAME = ChatColor.BLUE + "☸ Warp Charm ☸";
    public static Material MATERIAL = Material.QUARTZ;

    private static String LORE = CharmLang.LORE_COLOR + "Magically teleports you to special location.";

    private static int STARTING_USES = 3;

    private static String USE_STRING = CharmLang.GENERIC_CHARM_TELEPORT;
    private static String WEAKEN_STRING = CharmLang.GENERIC_CHARM_WEAKEN;
    private static String CONSUME_STRING = CharmLang.GENERIC_CHARM_CONSUME;
    private static String FAIL_STRING = CharmLang.NEGATIVE_COLOR + "Could not find warp!";

    public static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    public static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public CharmWarp(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
    }

    public static ItemStack Create(String warpName){

        ArrayList<String> lore = new ArrayList<String>(){{
            add(LORE);
            add(CharmLang.LORE_COLOR + warpName);
            add(Utils.ConstructUseString(STARTING_USES)); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    private static Location GetWarpLocation(ItemStack item){
        String warpname = ReadWarpName(item);

        try {
            Location location = AllimoreItems.ESSENTIALS.getWarps().getWarp(warpname);
            return location;
        } catch (WarpNotFoundException e) {
            return null;
        } catch (InvalidWorldException e) {
            return null;
        }
    }

    private static String ReadWarpName(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = (ArrayList<String>)meta.getLore();

        return lore.get(1).substring(2);
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        event.setCancelled(false);

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        Location warpLoc = GetWarpLocation(item);

        if(warpLoc != null){
            player.sendMessage(USE_STRING);
            USE_SOUND.PlaySound(player, warpLoc);
            player.teleport(warpLoc);

            int uses = Utils.ReadUsesFromLore(item, 2);
            uses --;
            if(uses <= 0){
                player.sendMessage(CONSUME_STRING);
                Utils.ConsumeFromMainHand(player);
                return;
            }else{
                player.sendMessage(WEAKEN_STRING);
                if(Utils.CanSpilt(item)){
                    CharmUseInfo info = new CharmUseInfo(2, uses);
                    Utils.SplitOffCharmStackMain(player, Create(ReadWarpName(item)), info);
                    return;
                }else{
                    Utils.UpdateUseLine(item, 2, uses);
                    return;
                }
            }

        }else{
            FAIL_SOUND.PlaySound(player);
            player.sendMessage(FAIL_STRING);
            return;
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

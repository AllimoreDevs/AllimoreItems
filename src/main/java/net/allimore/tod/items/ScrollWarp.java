package net.allimore.tod.items;

import com.earth2me.essentials.commands.WarpNotFoundException;
import net.allimore.tod.AllimoreItems;
import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.CharmSounds;
import net.allimore.tod.Utilities.SoundInfo;
import net.allimore.tod.Utilities.Utils;
import net.ess3.api.InvalidWorldException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ScrollWarp {
    public static String NAME = ChatColor.BLUE + "☸ Warp Scroll ☸";
    public static Material MATERIAL = Material.PAPER;

    private static String LORE = CharmLang.LORE_COLOR + "Magically teleports you to special location.";

    private static String USE_STRING = CharmLang.GENERIC_CHARM_TELEPORT;
    private static String CONSUME_STRING = CharmLang.GENERIC_SCRROLL_CONSUME;
    private static String FAIL_STRING = CharmLang.NEGATIVE_COLOR + "Could not find warp!";

    public static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    public static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public static ItemStack Create(String warpName){

        ArrayList<String> lore = new ArrayList<String>(){{ add(LORE); add(ChatColor.DARK_PURPLE + warpName); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    private static Location GetWarpLocation(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = (ArrayList<String>)meta.getLore();

        String warpname = lore.get(1).substring(2);

        try {
            Location location = AllimoreItems.ESSENTIALS.getWarps().getWarp(warpname);
            return location;
        } catch (WarpNotFoundException e) {
            return null;
        } catch (InvalidWorldException e) {
            return null;
        }
    }

    public static void Run(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        Location warpLoc = GetWarpLocation(item);

        if(warpLoc != null){
            player.sendMessage(USE_STRING);
            player.sendMessage(CONSUME_STRING);
            USE_SOUND.PlaySound(player, warpLoc);
            player.teleport(warpLoc);
            Utils.ConsumeFromMainHand(player);
            return;
        }else{
            FAIL_SOUND.PlaySound(player);
            player.sendMessage(FAIL_STRING);
            return;
        }
    }
}

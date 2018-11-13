package net.allimore.tod.items;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.CharmSounds;
import net.allimore.tod.Utilities.SoundInfo;
import net.allimore.tod.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Hashtable;

public class ScrollTownPortal {
    public static String NAME = ChatColor.AQUA + "Town Portal Scroll";
    public static Material MATERIAL = Material.PAPER;

    public static String LORE_LINE1 = CharmLang.LORE_COLOR + "This scroll will magically ";
    public static String LORE_LINE2 = CharmLang.LORE_COLOR + "transport you to town.";

    public static String USE_STRING = CharmLang.GENERIC_CHARM_TELEPORT;
    public static String CONSUME_STRING = CharmLang.GENERIC_SCRROLL_CONSUME;

    public static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;


    public static ItemStack CreateScroll(Town town){
        ArrayList<String> lore = new ArrayList<String>() {{ add(LORE_LINE1); add(LORE_LINE2); }};
        return Utils.ConstructItemStack(NAME + " " + town.getName(), MATERIAL, lore);
    }

    public static boolean IsScroll(ItemStack item, PlayerInteractEvent event){
        if(item.getType() != MATERIAL) { return false; }
        if(item.getItemMeta().getDisplayName().length() < 20) { return false; }
        if(! item.getItemMeta().getDisplayName().substring(0,20).equals(NAME)) { return false; }
        return true;
    }

    private static Town GetTown(ItemStack item){
        Hashtable towns = Towny.getPlugin().getTownyUniverse().getTownsMap();
        String townName = item.getItemMeta().getDisplayName();
        String townKey = townName.substring(21).toLowerCase();
        return (Town)towns.get(townKey);
    }

    private static void TeleportPlayer(Player player, Town town) throws TownyException {
        player.sendMessage(USE_STRING);
        player.sendMessage(CONSUME_STRING);
        USE_SOUND.PlaySound(player);

        player.teleport(town.getSpawn());
    }

    public static void RunScroll(PlayerInteractEvent event){
        Town town = GetTown(event.getItem());

        try{
            TeleportPlayer(event.getPlayer(), town);
            Utils.ConsumeFromMainHand(event.getPlayer());
            event.setCancelled(true);

        } catch (TownyException e) {
            e.printStackTrace();
        }
    }
}

package net.allimore.tod.items;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import net.allimore.tod.Utilities.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Hashtable;

public class CharmTownPortal {
    public static String NAME = ChatColor.AQUA + "Town Portal Charm";
    public static Material MATERIAL = Material.QUARTZ;

    private static String LORE_LINE1 = ChatColor.DARK_PURPLE + "This charm will magically ";
    private static String LORE_LINE2 = ChatColor.DARK_PURPLE + "transport you to town.";

    private static int STARTING_USES = 5;

    private static String USE_STRING = CharmLang.GENERIC_CHARM_TELEPORT;
    private static String WEAKEN_STRING = CharmLang.GENERIC_CHARM_WEAKEN;
    private static String CONSUME_STRING = CharmLang.GENERIC_CHARM_CONSUME;

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;

    public static ItemStack CreateCharm(Town town){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE_LINE1);
            add(LORE_LINE2);
            add(Utils.ConstructUseString(STARTING_USES));}};
        return Utils.ConstructItemStack(NAME + " " + town.getName(), MATERIAL, lore);
    }

    public static boolean IsCharm(ItemStack item, PlayerInteractEvent event){
        if(item.getType() != MATERIAL) { return false; }
        if(item.getItemMeta().getDisplayName().length() < 19) { return false; }
        if(! item.getItemMeta().getDisplayName().substring(0,19).equals(NAME)) { return false; }
        return true;
    }

    private static Town GetTown(ItemStack item){
        Hashtable towns = Towny.getPlugin().getTownyUniverse().getTownsMap();
        String townName = item.getItemMeta().getDisplayName();
        String townKey = townName.substring(20).toLowerCase();
        return (Town)towns.get(townKey);
    }

    private static void TeleportPlayer(Player player, Town town) throws TownyException {
        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player, town.getSpawn());

        player.teleport(town.getSpawn());
    }

    public static void RunCharm(PlayerInteractEvent event){
        Town town = GetTown(event.getItem());
        event.setCancelled(true);

        try{
            Player player = event.getPlayer();
            ItemStack item = event.getItem();

            TeleportPlayer(player, town);

            int uses = Utils.ReadUsesFromLore(item, 2);
            uses --;

            if(uses <= 0){
                player.sendMessage(CONSUME_STRING);
                Utils.ConsumeFromMainHand(player);
            }else{
                player.sendMessage(WEAKEN_STRING);

                if(Utils.CanSpilt(item)){
                    CharmUseInfo info = new CharmUseInfo(2, uses);
                    Utils.SplitOffCharmStackMain(player, CreateCharm(GetTown(item)), info);
                }else{
                    Utils.UpdateUseLine(item,2, uses);
                }
            }

        } catch (TownyException e) {
            e.printStackTrace();
        }
    }
}

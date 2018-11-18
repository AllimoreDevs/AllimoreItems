package net.allimore.tod.items;


import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.*;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CharmValkyrie extends Charm implements ITriggerInteract {
    public static String NAME = String.format("%sValkyrie %sStone", ChatColor.AQUA, ChatColor.WHITE);
    public static Material MATERIAL = Material.QUARTZ;

    private static String LORE1 =
            CharmLang.LORE_COLOR + "Tap into the power emanating from this stone";
    private static String LORE2 =
            CharmLang.LORE_COLOR + "to unfurl your wings and fly as the dragons do.";

    private static String USE =
            CharmLang.POSITIVE_COLOR + "Rays of light spiral out of the stone and form into wings of shimmering white light on your back.";
    private static String END =
            CharmLang.NEGATIVE_COLOR + "The light from your wings fades away and the stone returns to normal.";
    private static String FAIL_NO_REGISTRY =
            CharmLang.NEGATIVE_COLOR + "[AllimoreItems: ERROR] Player not in Resident Registry!";
    private static String FAIL_NO_TOWN =
            CharmLang.NEGATIVE_COLOR + "You must have residency in a town to use this item!";
    private static String FAIL_NOT_IN_TOWN_CHUNK =
            CharmLang.NEGATIVE_COLOR + "You cannot use this item in the wilderness, use it in your own town!";
    private static String FAIL_NOT_IN_OWN_TOWN =
            CharmLang.NEGATIVE_COLOR + "You need to use this in your own town!";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public static Hashtable<String, Player> FLYING_PLAYERS = new Hashtable<>();

    public CharmValkyrie(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
    }

    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<String>() {{
           add(LORE1);
           add(LORE2);
        }};

        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static Town GetPlayerTown(Resident resident, Player player){
        try {
            if(! resident.hasTown()) {
                // Has no Town
                player.sendMessage(FAIL_NO_TOWN);
                FAIL_SOUND.PlaySound(player);
                return null;
            }

            return resident.getTown();

        } catch (NotRegisteredException e) {
            // Has no Town
            player.sendMessage(FAIL_NO_TOWN);
            FAIL_SOUND.PlaySound(player);
            return null;
        }
    }

    public static Resident GetResident(Player player){
        TownyUniverse townyUniverse = Towny.getPlugin().getTownyUniverse();
        Hashtable<String, Resident> residents = townyUniverse.getResidentMap();

        if(! (residents.containsKey(player.getName().toLowerCase()) ) ) {
            // Player is not in Resident Registry
            player.sendMessage(FAIL_NO_REGISTRY);
            FAIL_SOUND.PlaySound(player);
            return null;
        }
        return residents.get(player.getName().toLowerCase());
    }

    public static boolean IsInOwnTown(Player player, Town town){
        WorldCoord worldCoord = new WorldCoord(player.getWorld().getName(), Coord.parseCoord(player));
        try {
            TownBlock townBlock = worldCoord.getTownBlock();

            if (! IsOwnTownOrAlliedTown(GetResident(player), player, townBlock.getTown()) ){
                // Not in own Town
                FAIL_SOUND.PlaySound(player);
                player.sendMessage(FAIL_NOT_IN_OWN_TOWN);
                return false;
            }

        } catch (NotRegisteredException e) {
            // Not in a town
            FAIL_SOUND.PlaySound(player);
            player.sendMessage(FAIL_NOT_IN_TOWN_CHUNK);
            return false;
        }
        return true;
    }

    public static boolean IsOwnTownOrAlliedTown(Resident resident, Player player, Town townTo){
        Town residentTown = GetPlayerTown(resident, player);
        if(residentTown == townTo) { return true;}

        try {
            List<Town> towns = residentTown.getNation().getTowns();

            for (Town town : towns){
                if(town == townTo){
                    return true;
                }
            }

        } catch (NotRegisteredException e) {
            player.sendMessage("An Error occurred while trying to get the town you belong to.");
            return false;
        }
        return false;
    }

    public static boolean IsInOwnTown(Player player, WorldCoord coord){
        Resident resident = GetResident(player);
        Town town = GetPlayerTown(resident, player);
        try {
            if( IsOwnTownOrAlliedTown(resident, player, coord.getTownBlock().getTown()) ){
                return true;
            }
        } catch (NotRegisteredException e) {
            player.sendMessage("An Error occurred while trying to get the town you belong to.");
            return false;
        }
        return false;
    }

    public static void ToggleFlight(Player player){
        if(player.getAllowFlight() == false){
            USE_SOUND.PlaySound(player);
            player.sendMessage(USE);

            player.setAllowFlight(true);
            FLYING_PLAYERS.put(player.getName(), player);
            return;
        } else {
            USE_SOUND.PlaySound(player);
            player.sendMessage(END);

            player.setAllowFlight(false);
            FLYING_PLAYERS.remove(player.getName());
            return;
        }
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        event.setCancelled(true);

        Player player = event.getPlayer();
        Resident resident = GetResident(player);
        Town town = GetPlayerTown(resident, player);

        if (! IsInOwnTown(player, town)) { return; }

        ToggleFlight(player);
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

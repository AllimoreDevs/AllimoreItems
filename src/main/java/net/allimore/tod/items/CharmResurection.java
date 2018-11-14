package net.allimore.tod.items;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import net.allimore.tod.*;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Hashtable;

public class CharmResurection extends Charm implements ITriggerRecieveDamage {
    public static String NAME = ChatColor.DARK_RED + "Resurrection Charm";
    public static Material MATERIAL = Material.TOTEM_OF_UNDYING;

    private static String LORE1 = CharmLang.LORE_COLOR + "Saves you from fetal damage and";
    private static String LORE2 = CharmLang.LORE_COLOR + "returns you to a safe place.";

    private static String USE_STRING =
            CharmLang.POSITIVE_COLOR + "A sphere of magical energy manifests around you as reality tears around you.";

    private static String TRANSPORT_HOME_STRING =
            CharmLang.POSITIVE_COLOR + "The world reforms around you. You are safe.";

    private static String CONSUME_STRING =
            CharmLang.NEGATIVE_COLOR + "Your charms bursts into an explosion of light leaving naught a trace behind.";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;

    public CharmResurection(){
        super(NAME, MATERIAL);
        Triggers.RegisterRecieveDamageTrigger(this);
    }

    public static ItemStack CreateCharmResurrection(){
        ArrayList<String> lore = new ArrayList<String>() {{ add(LORE1); add(LORE2); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static boolean HasCharm(Player player){
        return Utils.ItemsMatch(player.getInventory().getItemInOffHand(), MATERIAL, NAME);
    }

    private static Location FetchHome(Player player){
        // Try to get a Player's Essential's Home
        try{
            return AllimoreItems.ESSENTIALS.getUser(player).getHome("home");
        } catch (Exception e) { return null;}
    }

    private static Location FetchTown(Player player){
        // Try to get a Player's Town Spawn
        Towny towny = Towny.getPlugin();
        Hashtable residents = towny.getTownyUniverse().getResidentMap();

        // Check Resident Exits
        if(residents.containsKey(player.getDisplayName())) {
            Resident resident = (Resident) residents.get(player.getDisplayName());

            // Check Resident Belongs to a town
            if (resident.hasTown()) {
                try {
                    // Return the town spawn
                    return resident.getTown().getSpawn();
                } catch (TownyException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private static Location GetSafeSpot(Player player){
        Location home = FetchHome(player);
        if(home != null){
            return home;
        }

        home = FetchTown(player);
        if(home != null) {
            return home;
        }

        return player.getWorld().getSpawnLocation();
    }

    private static void UseCharm(Player player){
        player.sendMessage(USE_STRING);
        player.sendMessage(TRANSPORT_HOME_STRING);
        player.teleport(GetSafeSpot(player));
        USE_SOUND.PlaySound(player);

        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(maxHealth);
    }

    @Override
    public void RunTrigger(EntityDamageEvent event){
        Player player = (Player)event.getEntity();

        if(! super.ItemMatchOffHand(player) || (player.getHealth() - event.getFinalDamage() > 0)) { return; }

        event.setCancelled(true);
        UseCharm(player);

        player.sendMessage(CONSUME_STRING);
        Utils.ConsumeFromOffHand(player);
    }
}

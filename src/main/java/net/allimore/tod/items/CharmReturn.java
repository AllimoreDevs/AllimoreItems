package net.allimore.tod.items;

import net.allimore.tod.*;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CharmReturn extends Charm implements ITriggerInteract {
    public static String NAME = ChatColor.LIGHT_PURPLE + "Charm of Return";
    public static Material MATERIAL = Material.FIREWORK_STAR;

    private static String LORE = CharmLang.LORE_COLOR + "Teleports you to a saved location.";

    private static String USE_STRING = CharmLang.GENERIC_CHARM_TELEPORT;
    private static String SAVE_STRING = CharmLang.POSITIVE_COLOR + "The Charm will remember this place.";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo SAVE_SOUND = CharmSounds.FAIL_SOUND;

    public CharmReturn(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
    }

    public static ItemStack CreateCharm(){
        ArrayList<String> lore = new ArrayList<String>(){{ add(LORE); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    private static void SaveLocation(Player player, ItemStack item){
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(LORE);
        lore.add(CharmLang.LORE_COLOR + "x:" + player.getLocation().getX()); //  Line 1 | +Co-ords start at index 4
        lore.add(CharmLang.LORE_COLOR + "y:" + player.getLocation().getY()); //  Line 2 | +Co-ords start at index 4
        lore.add(CharmLang.LORE_COLOR + "z:" + player.getLocation().getZ()); //  Line 3 | +Co-ords start at index 4
        lore.add(CharmLang.LORE_COLOR + "World:" + player.getWorld().getName()); // Line 4 | World names starts at index 8

        meta.setLore(lore);
        item.setItemMeta(meta);

        player.sendMessage(SAVE_STRING);
        SAVE_SOUND.PlaySound(player);
    }

    private static Location ReadLocation(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = (ArrayList<String>)meta.getLore();

        double x =  Double.parseDouble(lore.get(1).substring(4));
        double y =  Double.parseDouble(lore.get(2).substring(4));
        double z =  Double.parseDouble(lore.get(3).substring(4));
        World world = AllimoreItems.SERVER.getWorld(lore.get(4).substring(8));

        return new Location(world, x, y, z);
    }

    private static boolean LocationSaved(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = (ArrayList<String>)meta.getLore();

        return lore.size() > 1;
    }

    private static void UseCharm(Player player, ItemStack item) {
        Location location = ReadLocation(item);
        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);
        player.teleport(location);
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        event.setCancelled(true);

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(player.isSneaking()){
            SaveLocation(player, item);
            return;
        }

        if(! LocationSaved(item)){
            if(Utils.CanSpilt(item)){
                ItemStack newItem = CreateCharm();
                SaveLocation(player, newItem);
                player.getInventory().addItem(newItem);
                item.setAmount(item.getAmount() - 1);
            }else{
                SaveLocation(player, item);
            }
            return;
        }

        UseCharm(player, item);
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

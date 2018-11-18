package net.allimore.tod.items;

import net.allimore.tod.AllimoreItems;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamage;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamageEntity;
import net.allimore.tod.items.tasks.IncorpReturnTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Hashtable;

public class CharmIncorporeal extends Charm implements ITriggerInteract, ITriggerRecieveDamage, ITriggerRecieveDamageEntity {
    public static String NAME = ChatColor.GRAY + "Incorporeal Pendant.";
    public static Material MATERIAL = Material.QUARTZ;

    private static String LORE1 = CharmLang.LORE_COLOR + "The Pendant emits a bright white light from it/s center.";
    private static String LORE2 = CharmLang.LORE_COLOR + "Walk as the spirits do and become untouchable.";

    private static int STARTING_USES = 3;

    private static String USE_STRING =
            CharmLang.POSITIVE_COLOR + "Ghostly streams of magic rise form your charm as your body becomes spectral.";
    private static String END_STRING =
            CharmLang.NEGATIVE_COLOR + "In a flash of light your body rematerializes!";
    public static String CANT_INTERACT =
            CharmLang.NEGATIVE_COLOR + "Your cannot interact with the world in this form!";
    private static String WEAKEN_STRING =
            CharmLang.NEGATIVE_COLOR + "The light from your Charm dims.";
    private static String CONSUME_STRING =
            CharmLang.NEGATIVE_COLOR + "You cast the Pendant aside as the light within dies.";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo END_SOUND = CharmSounds.USE_SOUND;

    public static Hashtable<String, Player> SPECTRAL_PLAYERS = new Hashtable<String, Player>();
    private static int TICK_DURRATION = 200;

    public CharmIncorporeal(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
        Triggers.RegisterRecieveDamageTrigger(this);
        Triggers.RegisterRecieveDamageEntityTrigger(this);
    }

    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE1);
            add(LORE2);
            add(Utils.ConstructUseString(STARTING_USES)); }};

        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static void EndSpectral(String playerName){
        Player player = SPECTRAL_PLAYERS.get(playerName);
        SPECTRAL_PLAYERS.remove(playerName);

        player.sendMessage(END_STRING);
        END_SOUND.PlaySound(player);
    }

    public static boolean HandleInteractCancel(PlayerInteractEvent event){
        if( CharmIncorporeal.SPECTRAL_PLAYERS.contains(event.getPlayer()) ){
            event.setCancelled(true);
            event.getPlayer().sendMessage(CharmIncorporeal.CANT_INTERACT);
            return true;
        }
        return false;
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        event.setCancelled(true);

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);

        SPECTRAL_PLAYERS.put(player.getDisplayName(), player);
        BukkitTask task = new IncorpReturnTask(player.getDisplayName()).runTaskLater(AllimoreItems.PLUGIN, TICK_DURRATION);

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
                Utils.SplitOffCharmStackMain(player, Create(), info);
            }else{
                Utils.UpdateUseLine(item, 2, uses);
            }
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

    @Override
    public void RunTrigger(EntityDamageEvent event) {
        if(! (event.getEntity() instanceof Player) ) { return; }
        if( SPECTRAL_PLAYERS.contains((Player)event.getEntity()) ){
            event.setCancelled(true);
        }
    }

    @Override
    public void RunTrigger(EntityDamageByEntityEvent event) {
        if( CharmIncorporeal.SPECTRAL_PLAYERS.contains(event.getDamager())){
            event.setCancelled(true);
        }
    }
}

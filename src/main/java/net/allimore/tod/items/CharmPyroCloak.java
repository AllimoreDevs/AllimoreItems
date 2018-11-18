package net.allimore.tod.items;

import net.allimore.tod.AllimoreItems;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamage;
import net.allimore.tod.items.tasks.PyroReturnTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Hashtable;

public class CharmPyroCloak extends Charm implements ITriggerInteract, ITriggerRecieveDamage {
    public static String NAME = ChatColor.DARK_RED + "Pyromancers Cloak";
    public static Material MATERIAL = Material.BLAZE_POWDER;

    private static String LORE1 = CharmLang.LORE_COLOR + "The very shape and form of the cloak";
    private static String LORE2 = CharmLang.LORE_COLOR + "seems to be composed purely of fire itself.";
    private static String LORE3 = CharmLang.LORE_COLOR + "Using it should protect you from any outside";
    private static String LORE4 = CharmLang.LORE_COLOR + "flame or heat.";

    private static int STARTING_USES = 3;

    private static String USE_STRING =
            CharmLang.POSITIVE_COLOR + "You wrap the cloak around yourself and feel it's warmth radiating into you.";
    private static String END_STRING =
            CharmLang.NEGATIVE_COLOR + "The Cloak reforms into it's charm state.";
    private static String WEAKEN_STRING =
            CharmLang.NEGATIVE_COLOR + "The fire composing the cloak seems to die down a little.";
    private static String CONSUME_STRING =
            CharmLang.NEGATIVE_COLOR + "The Cloak loses all form and disperses into smoke around you.";
    private static String FAIL_STRING =
            CharmLang.NEGATIVE_COLOR + "You are already using the cloak!";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo END_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public static Hashtable<String, Player> FIRE_IMMUNE_PLAYERS = new Hashtable<>();
    private static int TICK_DURRATION = 1200;

    public CharmPyroCloak(){
        super(NAME, MATERIAL);
        Triggers.RegisterInteractTrigger(this);
        Triggers.RegisterRecieveDamageTrigger(this);
    }

    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<String>(){{
            add(LORE1);
            add(LORE2);
            add(LORE3);
            add(LORE4);
            add(Utils.ConstructUseString(STARTING_USES));
        }};

        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static void End(String playerName){
        Player player = FIRE_IMMUNE_PLAYERS.get(playerName);
        FIRE_IMMUNE_PLAYERS.remove(playerName);
        END_SOUND.PlaySound(player);
        player.sendMessage(END_STRING);
    }

    public static boolean ImuneTo(EntityDamageEvent.DamageCause cause){
        switch(cause){
            case FIRE:
            case LAVA:
            case FIRE_TICK:
            case HOT_FLOOR:
            case DRAGON_BREATH:
                return true;
        }

        return false;
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(FIRE_IMMUNE_PLAYERS.contains(player)) {
            player.sendMessage(FAIL_STRING);
            FAIL_SOUND.PlaySound(player);
            return;
        }

        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);
        FIRE_IMMUNE_PLAYERS.put(player.getDisplayName(), player);
        BukkitTask task = new PyroReturnTask(player.getDisplayName()).runTaskLater(AllimoreItems.PLUGIN, TICK_DURRATION);

        int uses = Utils.ReadUsesFromLore(item, 4);
        uses --;

        if(uses <= 0){
            player.sendMessage(CONSUME_STRING);
            Utils.ConsumeFromMainHand(player);
        }else{
            player.sendMessage(WEAKEN_STRING);

            if(Utils.CanSpilt(item)){
                CharmUseInfo info = new CharmUseInfo(4, uses);
                Utils.SplitOffCharmStackMain(player, Create(), info);
            }else{
                Utils.UpdateUseLine(item,4, uses);
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
        if (CharmPyroCloak.FIRE_IMMUNE_PLAYERS.contains( (Player)event.getEntity() )){
            if(CharmPyroCloak.ImuneTo(event.getCause())){
                event.setCancelled(true);
            }
        }
    }
}

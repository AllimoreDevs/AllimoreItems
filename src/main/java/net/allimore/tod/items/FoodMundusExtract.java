package net.allimore.tod.items;

import net.allimore.tod.AllimoreItems;
import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerConsume;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamage;
import net.allimore.tod.items.tasks.MundusReturnTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Hashtable;

public class FoodMundusExtract extends Charm implements ITriggerRecieveDamage, ITriggerConsume {
    public static String NAME = ChatColor.GOLD + "Mundus Root Extract";
    public static Material MATERIAL = Material.POTION;
    private static short SUB_ID = 8195;

    private static String LORE1 = CharmLang.LORE_COLOR + "Distilled extract from the famed Mundus Root.";
    private static String LORE2 = CharmLang.LORE_COLOR + "Drink this and your body will suffer no toxin.";

    private static int STARTING_USES = 3;

    private static String USE_STRING =
            CharmLang.POSITIVE_COLOR + "The liquid is absolutely foul and every way. At least it seems to be working.";
    private static String END_STRING =
            CharmLang.NEGATIVE_COLOR + "The Mundus extract seems to have worn off now.";
    private static String FAIL_STRING =
            CharmLang.NEGATIVE_COLOR + "You are already under the effects of Mundus Extract, drinking more now would be waste!";
    private static String WEAKEN_STRING =
            CharmLang.NEGATIVE_COLOR +  "There seems to be some left over.";
    private static String CONSUME_STRING =
            CharmLang.NEGATIVE_COLOR + "After downing the last of disgusting extract you cast the bottle aside";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo END_SOUND = CharmSounds.USE_SOUND;
    private static SoundInfo FAIL_SOUND = CharmSounds.FAIL_SOUND;

    public static Hashtable<String, Player> POISON_IMMUNE_PLAYERS = new Hashtable<>();
    private static int TICK_DURRATION = 1200;

    public FoodMundusExtract(){
        super(NAME, MATERIAL);
        Triggers.RegisterConsumeTrigger(this);
        Triggers.RegisterRecieveDamageTrigger(this);
    }

    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE1);
            add(LORE2);
            add(Utils.ConstructUseString(STARTING_USES));
        }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static Boolean IsImmune(EntityDamageEvent.DamageCause cause){
        switch(cause){
            case POISON:
            case WITHER:
            case MAGIC:
                return true;
        }

        return false;
    }

    public static void EndImmune(String playerName){
        Player player = POISON_IMMUNE_PLAYERS.get(playerName);
        POISON_IMMUNE_PLAYERS.remove(playerName);

        player.sendMessage(END_STRING);
        END_SOUND.PlaySound(player);
    }

    @Override
    public void RunTrigger(PlayerItemConsumeEvent event){
        event.setCancelled(true);
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(POISON_IMMUNE_PLAYERS.contains(player)){
            player.sendMessage(FAIL_STRING);
            FAIL_SOUND.PlaySound(player);
            return;
        }

        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);
        POISON_IMMUNE_PLAYERS.put(player.getDisplayName(), player);
        BukkitTask task = new MundusReturnTask(player.getDisplayName()).runTaskLater(AllimoreItems.PLUGIN, TICK_DURRATION);

        int uses = Utils.ReadUsesFromLore(item, 2);
        uses --;
        if(uses <= 0){
            player.sendMessage(CONSUME_STRING);
            Utils.ConsumeFromMainHand(player);
        }else{
            player.sendMessage(WEAKEN_STRING);
            Utils.UpdateUseLine(item, 2, uses);
        }
    }

    @Override
    public Charm GetCharm() {
        return this;
    }

    @Override
    public void RunTrigger(EntityDamageEvent event) {
        if(! (event.getEntity() instanceof Player) ) { return; }
        if (FoodMundusExtract.POISON_IMMUNE_PLAYERS.contains( (Player)event.getEntity() )){
            if(FoodMundusExtract.IsImmune(event.getCause())){
                event.setCancelled(true);
            }
        }
    }
}

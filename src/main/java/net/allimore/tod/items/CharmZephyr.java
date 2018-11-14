package net.allimore.tod.items;

import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CharmZephyr extends Charm implements ITriggerRecieveDamage {
    public static String NAME = ChatColor.AQUA + "Zephyr" + ChatColor.WHITE + "Charm";
    public static Material MATERIAL = Material.LIGHT_GRAY_DYE;

    private static String LORE = CharmLang.LORE_COLOR + "Saves you from Lethal Fall Damage.";

    private static int STARTING_USES = 3;

    private static String USE_STRING = CharmLang.POSITIVE_COLOR + "Gusts of wind shoot out from your charm at the last second breaking your fall.";
    private static String WEAKEN_STRING = CharmLang.NEGATIVE_COLOR + "Your charm feels weaker now.";
    private static String CONSUME_STRING = CharmLang.NEGATIVE_COLOR + "Your charm disperses into a cloud of vapor.";

    private static SoundInfo USE_SOUND = new SoundInfo(Sound.BLOCK_SAND_PLACE, 3, 0.3f);

    public CharmZephyr(){
        super(NAME, MATERIAL);
        Triggers.RegisterRecieveDamageTrigger(this);
    }

    public static ItemStack CreateZephyrCharm(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE);
            add(Utils.ConstructUseString(STARTING_USES)); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static boolean HasCharm(Player player){
        return Utils.ItemsMatch(player.getInventory().getItemInOffHand(), MATERIAL, NAME);
    }

    private static void UseCharm(EntityDamageEvent event, Player player){

        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);
        event.setCancelled(true);
    }

    public static void BreakCharm(Player player){
        // Remove the item from existence
        player.sendMessage(CONSUME_STRING);

        Utils.ConsumeFromOffHand(player);
    }

    @Override
    public void RunTrigger (EntityDamageEvent event){
        if(! (event.getEntity() instanceof Player) ) { return; }
        Player player = (Player)event.getEntity();

        if(! super.ItemMatchOffHand(player) || player.getHealth() - event.getFinalDamage() > 0){
            return;
        }

        ItemStack charm = player.getInventory().getItemInOffHand();

        UseCharm(event, player);

        // Get Uses remaining
        int uses = Utils.ReadUsesFromLore(charm,1);
        uses --;

        if(uses == 0){
            BreakCharm(player);
        }else{
            player.sendMessage(WEAKEN_STRING);

            if(Utils.CanSpilt(charm)){
                CharmUseInfo useInfo = new CharmUseInfo(1, uses);
                Utils.SplitOffCharmStackOff(player, CreateZephyrCharm(), useInfo);
            }else{
                Utils.UpdateUseLine(charm, 1, uses);
            }
        }
    }
}

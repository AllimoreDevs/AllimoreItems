package net.allimore.tod.items;

import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.CharmUseInfo;
import net.allimore.tod.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;

public class FoodMundusRoots {
    public static String NAME = ChatColor.GOLD + "Mundus Roots";
    public static Material MATERIAL = Material.CARROT;

    private static String LORE1 = CharmLang.LORE_COLOR + "This odd looking root is";
    private static String LORE2 = CharmLang.LORE_COLOR + "a renowned cure all.";

    private static int START_USES = 5;

    private static String USE_STRING =
            CharmLang.POSITIVE_COLOR + "The roots are bitter and distasteful. But you feel renewed upon eating them.";
    private static String WEAKEN_STRING =
            CharmLang.NEGATIVE_COLOR + "There seems to be some roots left over.";
    private static String CONSUME_STRING =
            CharmLang.NEGATIVE_COLOR + "You've finished off the last of the Mundus Roots.";

    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE1);
            add(LORE2);
            add(Utils.ConstructUseString(START_USES));
        }};

        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    public static void Run(PlayerItemConsumeEvent event){
        event.setCancelled(true);

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        player.sendMessage(USE_STRING);
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        for (PotionEffect effect:effects) {
            player.removePotionEffect(effect.getType());
        }

        int uses = Utils.ReadUsesFromLore(item,2);
        uses --;

        if(uses <= 0){
            player.sendMessage(CONSUME_STRING);
            Utils.ConsumeFromMainHand(player);
            return;
        } else {
            player.sendMessage((WEAKEN_STRING));
            if(Utils.CanSpilt(item)){
                CharmUseInfo info = new CharmUseInfo(2, uses);
                Utils.SplitOffCharmStackMain(player, Create(), info);
                return;
            }else{
                ItemStack newItem = Utils.UpdateUseLine(item, 2, uses);
                player.getInventory().setItemInMainHand(newItem);
                return;
            }
        }
    }

}

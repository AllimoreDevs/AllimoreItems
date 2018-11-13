package net.allimore.tod.items;

import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class FoodSilverJuice {
    public static String NAME = ChatColor.GRAY + "Silver Fruit Juice";
    public static Material MATERIAL = Material.POTION;
    private static short SUB_ID = 8206;

    private static String LORE1 = CharmLang.LORE_COLOR + "Packed with Super Nutrients(tm)";
    private static String LORE2 = CharmLang.LORE_COLOR + "Drink in Moderation!";

    private static int STARTING_USES = 3;

    private static String USE_STRING =
            CharmLang.POSITIVE_COLOR + "The Fruit juice is delicious and upon drinking it you feel totally sated!";
    private static String WEAKEN_STRING =
            CharmLang.NEGATIVE_COLOR + "There seems to be some juice left over.";
    private static String CONSUME_STRING =
            CharmLang.NEGATIVE_COLOR + "To your disappointment the bottle is now empty.";

    public static ItemStack CreateSilverJuice(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE1);
            add(LORE2);
            add(Utils.ConstructUseString(STARTING_USES)); }};

        ItemStack item = Utils.ConstructItemStack(NAME, MATERIAL, lore);
        ItemMeta meta = item.getItemMeta();
        Damageable damage = (Damageable)meta;
        damage.setDamage(SUB_ID);
        item.setItemMeta(meta);

        return item;
    }

    public static void Run(PlayerItemConsumeEvent event){
        event.setCancelled(true);

        Player player = event.getPlayer();

        player.sendMessage(USE_STRING);
        player.setSaturation(20);
        player.setFoodLevel(20);

        int uses = Utils.ReadUsesFromLore(event.getItem(), 2);
        uses --;
        if(uses <= 0){
            player.sendMessage(CONSUME_STRING);
            Utils.ConsumeFromMainHand(player);
        }else{
            player.sendMessage(WEAKEN_STRING);
            ItemStack newItem = Utils.UpdateUseLine(event.getItem(), 2, uses);
            player.getInventory().setItemInMainHand(newItem);
        }
    }
}

package net.allimore.tod.items;

import net.allimore.tod.Utilities.Charm;
import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.Interfaces.ITriggerConsume;
import net.allimore.tod.Utilities.Triggers;
import net.allimore.tod.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FoodSilverFruit extends Charm implements ITriggerConsume {
    public static String NAME = ChatColor.GRAY + "Silver Fruit";
    public static Material MATERIAL = Material.APPLE;

    private static String LORE = CharmLang.LORE_COLOR + "Packed with Super Nutrients(tm)!";

    private static String USE_STRING = CharmLang.POSITIVE_COLOR + "The Fruit is delicious and upon eating it you feel totally sated!";

    public FoodSilverFruit(){
        super(NAME, MATERIAL);
        Triggers.RegisterConsumeTrigger(this);
    }

    public static ItemStack CreateSilverFruit(){
        ArrayList<String> lore = new ArrayList<String>() {{ add(LORE); }};
        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }

    @Override
    public void RunTrigger(PlayerItemConsumeEvent event){
        event.setCancelled(true);
        Player player = event.getPlayer();

        player.sendMessage(USE_STRING);
        player.setSaturation(20);
        player.setFoodLevel(20);

        Utils.ConsumeFromMainHand(player);
    }

    @Override
    public Charm GetCharm() {
        return this;
    }
}

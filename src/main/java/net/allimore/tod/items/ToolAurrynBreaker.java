package net.allimore.tod.items;

import net.allimore.tod.Utilities.Charm;
import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.Interfaces.ITriggerBlockBreak;
import net.allimore.tod.Utilities.Triggers;
import net.allimore.tod.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class ToolAurrynBreaker extends Charm implements ITriggerBlockBreak {
    public static String NAME = String.format("%sAurryn %sBreaker", ChatColor.BLUE, ChatColor.GREEN);
    public static Material MATERIAL = Material.DIAMOND_PICKAXE;

    private static String LORE1 =
            CharmLang.LORE_COLOR + "This Pickaxe is conceived from the essence of the world itself.";
    private static String LORE2 =
            CharmLang.LORE_COLOR + "With it in hand the world will yield before you.";

    private static Enchantment EFFIENCY = Enchantment.DIG_SPEED;
    private static int EFFIENCY_LEVEL = 5;
    private static Enchantment UNBREAKING = Enchantment.DURABILITY;
    private static int UNBREAKING_LEVEL = 6;

    private static double ACTIVATION_CHANCE = 0.2;

    private static PotionEffect EFFECT = new PotionEffect(PotionEffectType.FAST_DIGGING, 10, 2);

    public ToolAurrynBreaker(){
        super(NAME, MATERIAL);
        Triggers.RegisterBlockBreakTrigger(this);
    }

    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<String>(){{
            add(LORE1);
            add(LORE2);
        }};

        ItemStack stack = Utils.ConstructItemStack(NAME, MATERIAL, lore);
        stack.addEnchantment(EFFIENCY, EFFIENCY_LEVEL);
        stack.addUnsafeEnchantment(UNBREAKING, UNBREAKING_LEVEL);

        return stack;
    }

    @Override
    public void RunTrigger(BlockBreakEvent event){
        double chance = Math.random();

        if(chance > 1 - ACTIVATION_CHANCE){
            event.getPlayer().addPotionEffect(EFFECT);
        }

    }

    @Override
    public Charm GetCharm() {
        return this;
    }
}

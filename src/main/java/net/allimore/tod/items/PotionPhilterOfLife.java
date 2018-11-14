package net.allimore.tod.items;

import net.allimore.tod.Utilities.*;
import net.allimore.tod.Utilities.Interfaces.ITriggerConsume;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PotionPhilterOfLife extends Charm implements ITriggerConsume {
    public static String NAME = ChatColor.DARK_RED + "Greater Elixir of Life";
    public static Material MATERIAL = Material.POTION;
    private static short SUB_ID = 8197;

    private static String LORE1 = CharmLang.LORE_COLOR + "Fully restores your health and provides";
    private static String LORE2 = CharmLang.LORE_COLOR + "four Temporary hearts for one minute.";

    private static int STARTING_USES = 3;

    private static String USE_STRING = CharmLang.POSITIVE_COLOR + "A warm light radiates from you as your body mend before your eyes. You feel stronger than before!";
    private static String WEAKEN_STRING = CharmLang.NEGATIVE_COLOR + "It looks like there is still some potion left over.";
    private static String CONSUME_STRING = CharmLang.NEGATIVE_COLOR + "You cast aside the now devoid bottle and ready yourself!";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;

    public PotionPhilterOfLife(){
        super(NAME, MATERIAL);
        Triggers.RegisterConsumeTrigger(this);
    }

    public static ItemStack CreatePhilter(){
        ItemStack philter = new ItemStack(MATERIAL, 1);
        ItemMeta meta = philter.getItemMeta();
        Damageable damageable = (Damageable)meta;

        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE1);
            add(LORE2);
            add(Utils.ConstructUseString(STARTING_USES)); }};

        meta.setLore(lore);
        damageable.setDamage(SUB_ID);
        meta.setDisplayName(NAME);

        philter.setItemMeta(meta);
        return philter;
    }

    private static void UsePotion(Player player){
        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(maxHealth);

        PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1200, 1);
        player.addPotionEffect(effect);
    }

    @Override
    public void RunTrigger(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack potion = event.getItem();

        UsePotion(player);

        int uses = Utils.ReadUsesFromLore(potion, 2);
        uses --;

        if(uses <= 0){
            player.sendMessage(CONSUME_STRING);
            Utils.ConsumeFromMainHand(player);
        } else {
            player.sendMessage(WEAKEN_STRING);
            ItemStack newItem = Utils.UpdateUseLine(event.getItem(), 2, uses);
            player.getInventory().setItemInMainHand(newItem);
        }

        event.setCancelled(true);
    }

    @Override
    public Charm GetCharm() {
        return this;
    }
}

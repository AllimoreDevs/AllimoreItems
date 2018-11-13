package net.allimore.tod.items;

import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.CharmSounds;
import net.allimore.tod.Utilities.SoundInfo;
import net.allimore.tod.Utilities.Utils;
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

public class PotionElixirOfLife {
    public static String NAME = ChatColor.DARK_RED + "Elixir of Life";
    public static Material MATERIAL = Material.POTION;
    private static short SUB_ID = 8197;

    private static String LORE1 = CharmLang.LORE_COLOR + "Fully restores your health and provides";
    private static String LORE2 = CharmLang.LORE_COLOR + "four Temporary hearts for one minute.";

    private static String USE_STRING =
            CharmLang.POSITIVE_COLOR + "A warm light radiates from you as your body mend before your eyes. You feel stronger than before!";
    private static String CONSUME_STRING =
            CharmLang.NEGATIVE_COLOR + "You cast aside the now devoid bottle and ready yourself!";

    private static SoundInfo USE_SOUND = CharmSounds.USE_SOUND;

    public static ItemStack CreateElixir(){
        ItemStack elixir = new ItemStack(MATERIAL, 1);
        ItemMeta meta = elixir.getItemMeta();
        Damageable damageable = (Damageable)meta;

        ArrayList<String> lore = new ArrayList<String>() {{ add(LORE1); add(LORE2); }};

        meta.setLore(lore);
        damageable.setDamage(SUB_ID);
        meta.setDisplayName(NAME);

        elixir.setItemMeta(meta);
        return elixir;
    }

    public static void RunPotion(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack potion = event.getItem();

        player.sendMessage(USE_STRING);
        USE_SOUND.PlaySound(player);
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(maxHealth);

        PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 1200, 1);
        player.addPotionEffect(effect);

        player.sendMessage(CONSUME_STRING);
        Utils.ConsumeFromMainHand(player);
        event.setCancelled(true);
    }
}

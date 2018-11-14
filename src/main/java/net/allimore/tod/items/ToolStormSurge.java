package net.allimore.tod.items;

import net.allimore.tod.AllimoreItems;
import net.allimore.tod.Utilities.Charm;
import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.Interfaces.ITriggerInteract;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamage;
import net.allimore.tod.Utilities.Interfaces.ITriggerRecieveDamageEntity;
import net.allimore.tod.Utilities.Triggers;
import net.allimore.tod.Utilities.Utils;
import net.allimore.tod.items.tasks.StormSurgeUncool;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class ToolStormSurge extends Charm implements ITriggerRecieveDamage, ITriggerRecieveDamageEntity, ITriggerInteract {
    public static String NAME = String.format("%s%s<-%s%sO %s%sStorm %s%sSurge %s%sO%s%s->",
            ChatColor.DARK_BLUE, ChatColor.BOLD, ChatColor.AQUA, ChatColor.BOLD, ChatColor.BLUE, ChatColor.BOLD,
            ChatColor.WHITE, ChatColor.BOLD, ChatColor.AQUA, ChatColor.BOLD, ChatColor.BLUE, ChatColor.BOLD);

    public static Material MATERIAL = Material.DIAMOND_SWORD;

    private static String LORE1 =
            CharmLang.LORE_COLOR + "A Legendary weapon once wielded by the Aurrn Arch-Battlemage.";
    private static String LORE2 =
            CharmLang.LORE_COLOR + "Legend says it contains the heart of the raging storm in";
    private static String LORE3 =
            CharmLang.LORE_COLOR + "which it was forged. Given the immense energy emanating";
    private static String LORE4 =
            CharmLang.LORE_COLOR + "from the blade, it seems there may be some truth to those";
    private static String LORE5 =
            CharmLang.LORE_COLOR + "old tales.";

    private static String cooldownPrefix = CharmLang.LORE_COLOR + "Cooldown: ";

    private static Enchantment damageEnchant = Enchantment.DAMAGE_ALL;
    private static int damageEnchantLevel = 5;

    private static double ACTIVATION_CHANCE = 0.4;
    private static double RANGE = 15;
    private static int FORCE = 1;

    public static ArrayList<BukkitRunnable> CoolDownTasks = new ArrayList<>();

    public ToolStormSurge(){
        super(NAME, MATERIAL);
        Triggers.RegisterRecieveDamageTrigger(this);
        Triggers.RegisterRecieveDamageEntityTrigger(this);
        Triggers.RegisterInteractTrigger(this);
    }

    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<>();
        lore.add(LORE1);
        lore.add(LORE2);
        lore.add(LORE3);
        lore.add(LORE4);
        lore.add(LORE5);
        lore.add(cooldownPrefix + "false");

        ItemStack item = Utils.ConstructItemStack(NAME, MATERIAL, lore);
        item.addEnchantment(damageEnchant, damageEnchantLevel);
        return item;
    }

    @Override
    public void RunTrigger(EntityDamageEvent event) {
        if(! (event.getEntity() instanceof Player) ) { return; }
        Player player = (Player)event.getEntity();
        if(! super.ItemMatchMainHand(player)){ return; }

        if(event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING){
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public void RunTrigger(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player){
            Player player = (Player)event.getDamager();
            if(! (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) ) { return; }
            if(! super.ItemMatchMainHand(player)) { return; }

            ThrownBack(event.getEntity(), player);
            StrikeEntity(event.getEntity(), player);
            return;

        } else if (event.getEntity() instanceof  Player){
            Player player = (Player)event.getEntity();
            if(! super.ItemMatchMainHand(player)) { return; }
            if(event.getDamager() == player) { return; }
            if(! (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) ) { return; }

            if(Utils.TryChance(ACTIVATION_CHANCE)){
                StrikeEntity(event.getDamager(), player);
                return;
            }
        }
    }

    @Override
    public void RunTrigger(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if( Utils.ReadBooleanFromLine(event.getItem(), cooldownPrefix, 5) ) { return; }

        Collection<Entity> nearbyEntities =  player.getWorld().getNearbyEntities(player.getLocation(), RANGE, RANGE, RANGE);

        for(Entity entity : nearbyEntities){
            if(entity == null || entity.isDead() || entity instanceof Player ) { continue; }
            entity.getWorld().strikeLightning(entity.getLocation());
        }

        Utils.SetBooleanOnLine(event.getItem(), cooldownPrefix, 5, true);
        BukkitRunnable task = new StormSurgeUncool(event.getItem(), 5, cooldownPrefix);
        task.runTaskLater(AllimoreItems.PLUGIN, 120);
        CoolDownTasks.add(task);
    }

    @Override
    public Charm GetCharm() {
        return this;
    }

    @Override
    public Action GetAction() {
        return Action.RIGHT_CLICK_AIR;
    }

    private static void StrikeEntity(Entity entity, Player player){
        player.getWorld().strikeLightning(entity.getLocation());
    }

    private static void ThrownBack(Entity entity, Player player){
        Vector entityPosition = entity.getLocation().toVector();
        Vector playerPosition = player.getLocation().toVector();

        Vector directionalVector = entityPosition.subtract(playerPosition);
        directionalVector = directionalVector.normalize();
        directionalVector.multiply(FORCE);
        directionalVector.setY(directionalVector.getY() + 0.8);

        entity.setVelocity(directionalVector);
    }
}

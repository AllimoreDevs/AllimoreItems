package net.allimore.tod.Utilities;

import net.allimore.tod.Utilities.Interfaces.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Triggers {
    public enum TRIGGERS {
        LEFT_CLICK,
        RIGHT_CLICK,
        SHIFT_RIGHT_CLICK,
        TAKE_DAMAGE,
        TAKE_DAMAGE_ENTITY,
        DEAL_DAMAGE,
        BREAK_BLOCK,
        PROJECTILE_FIRE,
        PROJECTILE_HIT
    }

    public enum TYPES {
        TOOL,
        SWORD,
        BOW,
        ARMOR
    }

    public static TYPES[] LEFT_CLICK_COMPAT = new TYPES[]{ TYPES.SWORD, TYPES.TOOL, TYPES.BOW };
    public static TYPES[] RIGHT_CLICK_COMPAT = new TYPES[]{ TYPES.SWORD, TYPES.TOOL, TYPES.BOW };
    public static TYPES[] SHIFT_RIGHT_CLICK_COMPAT = new TYPES[]{ TYPES.SWORD, TYPES.TOOL, TYPES.BOW };
    public static TYPES[] TAKE_DAMAGE_COMPAT = new TYPES[]{ TYPES.ARMOR, TYPES.SWORD, TYPES.BOW };
    public static TYPES[] TAKE_DAMAGE_ENTITY_COMPAT = new TYPES[] {TYPES.ARMOR, TYPES.SWORD, TYPES.BOW};
    public static TYPES DEAL_DAMAGE_COMPAT = TYPES.SWORD;
    public static TYPES PROJECTILE_FIRE = TYPES.BOW;
    public static TYPES PROJECTILE_HIT = TYPES.BOW;
    public static TYPES BREAK_BLOCK = TYPES.TOOL;

    public static boolean IsTool(ItemStack item){
        switch (item.getType()){
            case DIAMOND_AXE:
            case DIAMOND_SHOVEL:
            case DIAMOND_PICKAXE:
            case GOLDEN_SHOVEL:
            case GOLDEN_PICKAXE:
            case GOLDEN_AXE:
            case IRON_SHOVEL:
            case IRON_PICKAXE:
            case IRON_AXE:
            case STONE_SHOVEL:
            case STONE_AXE:
            case STONE_PICKAXE:
            case WOODEN_SHOVEL:
            case WOODEN_PICKAXE:
            case WOODEN_AXE:
                return true;
        }
        return false;
    }

    public static boolean IsSword(ItemStack item){
        switch(item.getType()){
            case DIAMOND_SWORD:
            case IRON_SWORD:
            case STONE_SWORD:
            case WOODEN_SWORD:
                return true;
        }
        return false;
    }

    public static boolean IsBow(ItemStack item){
        return item.getType() == Material.BOW;
    }

    public static boolean IsArmor(ItemStack item){
        switch(item.getType()){
            case DIAMOND_BOOTS:
            case DIAMOND_LEGGINGS:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_HELMET:
            case GOLDEN_BOOTS:
            case GOLDEN_LEGGINGS:
            case GOLDEN_CHESTPLATE:
            case GOLDEN_HELMET:
            case IRON_BOOTS:
            case IRON_LEGGINGS:
            case IRON_CHESTPLATE:
            case IRON_HELMET:
            case CHAINMAIL_BOOTS:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_HELMET:
            case LEATHER_BOOTS:
            case LEATHER_LEGGINGS:
            case LEATHER_CHESTPLATE:
            case LEATHER_HELMET:
                return true;
        }
        return false;
    }

    private static ArrayList<ITriggerInteract> Inteact_Triggers = new ArrayList<>();
    private static ArrayList<ITriggerRecieveDamage> Recieve_Damage_Triggers = new ArrayList<>();
    private static ArrayList<ITriggerRecieveDamageEntity> Recieve_Damage_Entity_Triggers = new ArrayList<>();
    private static ArrayList<ITriggerBlockBreak> Block_Break_Triggers = new ArrayList<>();
    private static ArrayList<ITriggerConsume> Consume_Triggers = new ArrayList<>();
    private static ArrayList<ITriggerEntityInteract> Entity_Interact_Triggers = new ArrayList<>();

    public static ArrayList<ITriggerInteract> GetInteractTriggers(){
        return Inteact_Triggers;
    }
    public static ArrayList<ITriggerRecieveDamage> GetRecieveDamageTriggers(){
        return Recieve_Damage_Triggers;
    }
    public static ArrayList<ITriggerRecieveDamageEntity> GetRecieveDamageEntityTriggers(){
        return Recieve_Damage_Entity_Triggers;
    }
    public static ArrayList<ITriggerBlockBreak> GetBlockBreakTriggers(){
        return Block_Break_Triggers;
    }
    public static ArrayList<ITriggerConsume> GetConsumeTriggers(){
        return  Consume_Triggers;
    }
    public static ArrayList<ITriggerEntityInteract> GetEntityInteractTriggers(){
        return Entity_Interact_Triggers;
    }

    public static void RegisterInteractTrigger(ITriggerInteract trigger){
        Inteact_Triggers.add(trigger);
    }

    public static void RegisterRecieveDamageTrigger(ITriggerRecieveDamage trigger){
        Recieve_Damage_Triggers.add(trigger);
    }

    public static void RegisterRecieveDamageEntityTrigger(ITriggerRecieveDamageEntity trigger){
        Recieve_Damage_Entity_Triggers.add(trigger);
    }

    public static void RegisterBlockBreakTrigger(ITriggerBlockBreak trigger){
        Block_Break_Triggers.add(trigger);
    }

    public static void RegisterConsumeTrigger(ITriggerConsume trigger){
        Consume_Triggers.add(trigger);
    }

    public static void RegisterEntityInteractTrigger(ITriggerEntityInteract trigger){
        Entity_Interact_Triggers.add(trigger);
    }
}

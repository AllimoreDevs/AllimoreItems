package net.allimore.tod.Utilities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Triggers {
    public enum TRIGGERS {
        LEFT_CLICK,
        RIGHT_CLICK,
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
}

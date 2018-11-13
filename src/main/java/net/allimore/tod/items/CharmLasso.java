package net.allimore.tod.items;

import net.allimore.tod.Utilities.CharmLang;
import net.allimore.tod.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CharmLasso {
    public static String NAME = ChatColor.YELLOW + "Mystic Lasso";
    public static Material MATERIAL = Material.LEAD;

    private static String LORE1 =
            CharmLang.LORE_COLOR + "A magical artifact capable of picking up storing,";
    private static String LORE2 =
            CharmLang.LORE_COLOR + "and transporting most domestic entities.";

    private static String COW = "Cow";
    private static String PIG = "Pig";
    private static String SHEEP = "Sheep";
    private static String Chicken = "Chicken";
    private static String MOOSHROOM = "Mooshroom";

    private static String YOUNG = "Child";
    private static String ADULT = "Adult";

    private static String GROWN = "Has Wool";
    private static String SHEARED = "Sheared";

    private static String YELLOW = "Yellow";
    private static String RED = "Red";
    private static String BLUE = "Blue";
    private static String WHITE = "White";
    private static String LIGHT_BLUE = "Light Blue";
    private static String MAGENTA = "Magenta";
    private static String PURPLE = "Purple";
    private static String ORANGE = "Orange";
    private static String LIME = "Lime";
    private static String GREEN = "Green";
    private static String LIGHT_GRAY = "Light Gray";
    private static String GRAY = "Gray";
    private static String BROWN = "Brown";
    private static String BLACK = "Black";
    private static String PINK = "Pink";
    private static String CYAN = "Cyan";


    public static ItemStack Create(){
        ArrayList<String> lore = new ArrayList<String>() {{
            add(LORE1);
            add(LORE2);
        }};

        return Utils.ConstructItemStack(NAME, MATERIAL, lore);
    }


}

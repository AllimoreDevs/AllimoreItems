package net.allimore.tod.Utilities;

import org.bukkit.ChatColor;

public class CharmLang {
    public static ChatColor POSITIVE_COLOR = ChatColor.GREEN;
    public static ChatColor NEGATIVE_COLOR = ChatColor.RED;
    public static ChatColor LORE_COLOR = ChatColor.DARK_PURPLE;

    public static String GENERIC_CHARM_TELEPORT = POSITIVE_COLOR + "You step through the gateway as it appears.";
    public static String GENERIC_SCRROLL_CONSUME = NEGATIVE_COLOR + "The Scroll turns to dust in your hands.";
    public static String GENERIC_CHARM_CONSUME = NEGATIVE_COLOR +  "The Charm crumbles in your hands.";
    public static String GENERIC_CHARM_WEAKEN = NEGATIVE_COLOR +  "Your Charm feels weaker now";
    public static String GENERIC_FAIL_NEED_HOME = NEGATIVE_COLOR + "You can't use this without setting a home first!";
}

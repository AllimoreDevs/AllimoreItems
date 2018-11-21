package net.allimore.tod;

import com.earth2me.essentials.Warps;
import com.earth2me.essentials.commands.WarpNotFoundException;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.Town;
import net.allimore.tod.items.*;
import net.allimore.tod.items.ToolAurrynBreaker;
import net.ess3.api.InvalidWorldException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Hashtable;

public class CommandManager implements CommandExecutor
{
    private JavaPlugin plugin;

    public CommandManager (JavaPlugin plugin)
    {
        this.plugin = plugin;

        plugin.getCommand("suncharm").setExecutor(this);
        plugin.getCommand("skycharm").setExecutor(this);
        plugin.getCommand("raincharm").setExecutor(this);
        plugin.getCommand("tamecharm").setExecutor(this);
        plugin.getCommand("zephyrcharm").setExecutor(this);
        plugin.getCommand("elixir").setExecutor(this);
        plugin.getCommand("philter").setExecutor(this);
        plugin.getCommand("magicmirror").setExecutor(this);
        plugin.getCommand("portalscroll").setExecutor(this);
        plugin.getCommand("towncharm").setExecutor(this);
        plugin.getCommand("resurrectioncharm").setExecutor(this);
        plugin.getCommand("homescroll").setExecutor(this);
        plugin.getCommand("silverfruit").setExecutor(this);
        plugin.getCommand("silverjuice").setExecutor(this);
        plugin.getCommand("returncharm").setExecutor(this);
        plugin.getCommand("warpscroll").setExecutor(this);
        plugin.getCommand("warpCharm").setExecutor(this);
        plugin.getCommand("incorporealcharm").setExecutor(this);
        plugin.getCommand("pyrocharm").setExecutor(this);
        plugin.getCommand("mundusRoots").setExecutor(this);
        plugin.getCommand("mundusExtract").setExecutor(this);
        plugin.getCommand("valkyrieCharm").setExecutor(this);
        plugin.getCommand("allimoreBreaker").setExecutor(this);
        plugin.getCommand("stormSurge").setExecutor(this);
    }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("suncharm"))
        {
            GivePlayerItem(sender, CharmSun.CreateSunCharm());
            return true;
        }
        if (command.getName().equalsIgnoreCase("skycharm"))
        {
            GivePlayerItem(sender, CharmClearSky.CreateClearSkiesCharm());
            return true;
        }
        if (command.getName().equalsIgnoreCase("raincharm"))
        {
            GivePlayerItem(sender, CharmRain.CreateRainCharm());
            return true;
        }
        if (command.getName().equalsIgnoreCase("tamecharm"))
        {
            GivePlayerItem(sender, CharmTame.CreateTameCharm());
            return true;
        }
        if (command.getName().equalsIgnoreCase("zephyrcharm")){
            GivePlayerItem(sender, CharmZephyr.CreateZephyrCharm());
            return true;
        }
        if (command.getName().equalsIgnoreCase("resurrectioncharm")){
            GivePlayerItem(sender, CharmResurection.CreateCharmResurrection());
            return true;
        }
        if (command.getName().equalsIgnoreCase("elixir")){
            GivePlayerItem(sender, PotionElixirOfLife.CreateElixir());
            return true;
        }
        if (command.getName().equalsIgnoreCase("philter")){
            GivePlayerItem(sender, PotionPhilterOfLife.CreatePhilter());
            return true;
        }
        if (command.getName().equalsIgnoreCase("magicmirror")){
            GivePlayerItem(sender, CharmMagicMirror.CreateMagicMirror());
            return true;
        }
        if (command.getName().equalsIgnoreCase("portalscroll")){
            RunTownyPortalCommand(sender, args);
            return true;
        }
        if (command.getName().equalsIgnoreCase("towncharm")){
            RunTownyCharmCommand(sender, args);
            return true;
        }
        if (command.getName().equalsIgnoreCase("homescroll")){
            GivePlayerItem(sender, ScrollHome.CreateHomeScroll());
            return true;
        }
        if (command.getName().equalsIgnoreCase("silverfruit")){
            GivePlayerItem(sender, FoodSilverFruit.CreateSilverFruit());
            return true;
        }
        if (command.getName().equalsIgnoreCase("silverjuice")){
            GivePlayerItem(sender, FoodSilverJuice.CreateSilverJuice());
            return true;
        }
        if (command.getName().equalsIgnoreCase("returncharm")){
            GivePlayerItem(sender, CharmReturn.CreateCharm());
            return true;
        }
        if (command.getName().equalsIgnoreCase("warpscroll")){
            RunWarpScrollCommand(sender, args);
            return true;
        }
        if (command.getName().equalsIgnoreCase("warpcharm")){
            RunWarpCharmCommand(sender, args);
            return true;
        }
        if (command.getName().equalsIgnoreCase("incorporealcharm")){
            GivePlayerItem(sender, CharmIncorporeal.Create());
            return true;
        }
        if (command.getName().equalsIgnoreCase("pyrocharm")){
            GivePlayerItem(sender, CharmPyroCloak.Create());
            return true;
        }
        if (command.getName().equalsIgnoreCase("mundusroots")){
            GivePlayerItem(sender, FoodMundusRoots.Create());
            return true;
        }
        if (command.getName().equalsIgnoreCase("mundusextract")){
            GivePlayerItem(sender, FoodMundusExtract.Create());
            return true;
        }
        if (command.getName().equalsIgnoreCase("valkyriecharm")){
            GivePlayerItem(sender, CharmValkyrie.Create());
            return true;
        }
        if (command.getName().equalsIgnoreCase("allimoreBreaker")){
            GivePlayerItem(sender, ToolAurrynBreaker.Create());
            return true;
        }
        if (command.getName().equalsIgnoreCase("stormsurge")){
            GivePlayerItem(sender, ToolAurrynBlade.Create());
            return true;
        }
        return true;
    }

    private boolean IsPlayer(CommandSender sender){
        if(sender instanceof  Player){
            return true;
        }else{
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return false;
        }
    }

    private void GivePlayerItem(CommandSender sender, ItemStack item){
        if (! IsPlayer(sender) ) { return; }

        Player player = (Player) sender;

        player.getInventory().addItem(item);
    }

    private void RunTownyPortalCommand(CommandSender sender, String[] args){
        if(! IsPlayer(sender)) { return; }
        Player player = (Player)sender;

        if(args.length < 1)
        {
            sender.sendMessage(ChatColor.RED + "Not Enough Arguments!: /portalscroll <TownName>");
            return;
        }

        Hashtable townsMap = Towny.getPlugin().getTownyUniverse().getTownsMap();
        if(townsMap.containsKey(args[0].toLowerCase())){
            ItemStack scroll = ScrollTownPortal.CreateScroll((Town)townsMap.get(args[0].toLowerCase()));

            player.getInventory().addItem(scroll);
        }else{
            sender.sendMessage(ChatColor.RED + "Town " + args[0] + "not found!");
        }
    }

    private void RunTownyCharmCommand(CommandSender sender, String[] args){
        if(! IsPlayer(sender)) { return; }
        Player player = (Player)sender;

        if(args.length < 1)
        {
            sender.sendMessage(ChatColor.RED + "Not Enough Arguments!: /portalscroll <TownName>");
            return;
        }

        Hashtable townsMap = Towny.getPlugin().getTownyUniverse().getTownsMap();
        if(townsMap.containsKey(args[0].toLowerCase())){
            ItemStack scroll = CharmTownPortal.CreateCharm((Town)townsMap.get(args[0].toLowerCase()));

            player.getInventory().addItem(scroll);
        }else{
            sender.sendMessage(ChatColor.RED + "Town " + args[0] + "not found!");
        }
    }

    private void RunWarpScrollCommand(CommandSender sender, String[] args){
        if(! IsPlayer(sender)) { return; }
        Player player = (Player)sender;

        if(args.length < 1)
        {
            sender.sendMessage(ChatColor.RED + "Not Enough Arguments!: /warpscroll <WarpName>");
            return;
        }

        Warps warps = AllimoreItems.ESSENTIALS.getWarps();
        try{
            Location loc = warps.getWarp(args[0]);
            GivePlayerItem(sender, ScrollWarp.Create(args[0]));
            return;
        } catch (WarpNotFoundException e) {
            sender.sendMessage(ChatColor.RED + "Could not warp with the given name!");
            return;
        } catch (InvalidWorldException e) {
            sender.sendMessage(ChatColor.RED + "Could not warp with the given name!");
            return;
        }
    }

    private void RunWarpCharmCommand(CommandSender sender, String[] args){
        if(! IsPlayer(sender)) { return; }
        Player player = (Player)sender;

        if(args.length < 1)
        {
            sender.sendMessage(ChatColor.RED + "Not Enough Arguments!: /warpscroll <WarpName>");
            return;
        }

        Warps warps = AllimoreItems.ESSENTIALS.getWarps();
        try{
            Location loc = warps.getWarp(args[0]);
            GivePlayerItem(sender, CharmWarp.Create(args[0]));
            return;
        } catch (WarpNotFoundException e) {
            sender.sendMessage(ChatColor.RED + "Could not warp with the given name!");
            return;
        } catch (InvalidWorldException e) {
            sender.sendMessage(ChatColor.RED + "Could not warp with the given name!");
            return;
        }
    }



}

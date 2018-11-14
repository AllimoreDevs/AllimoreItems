package net.allimore.tod;

import net.allimore.tod.items.*;

public class TriggerInitalizer {

    private static CharmSun sun;
    private static CharmClearSky sky;
    private static CharmRain rain;
    private static CharmReturn returnCharm;
    private static CharmValkyrie valkyrie;
    private static CharmMagicMirror mirror;
    private static CharmPyroCloak pyroCloak;
    private static CharmIncorporeal incorp;
    private static CharmWarp warp;
    private static ScrollWarp scrollWarp;
    private static ScrollHome scrollHome;
    private static CharmZephyr zephyr;
    private static CharmResurection resurection;
    private static FoodMundusRoots mundusRoots;
    private static FoodMundusExtract mundusExtract;
    private static FoodSilverFruit silverFruit;
    private static FoodSilverJuice silverJuice;
    private static PotionElixirOfLife elixirOfLife;
    private static PotionPhilterOfLife philter;
    private static ToolAllimoreBreaker allimoreBreaker;
    private static CharmTame charmTame;
    private static ToolStormSurge stormSurge;

    public static void InitTriggers(){
        sun = new CharmSun();
        sky = new CharmClearSky();
        rain = new CharmRain();
        returnCharm = new CharmReturn();
        valkyrie = new CharmValkyrie();
        mirror = new CharmMagicMirror();
        pyroCloak = new CharmPyroCloak();
        incorp = new CharmIncorporeal();
        warp = new CharmWarp();
        scrollWarp = new ScrollWarp();
        scrollHome = new ScrollHome();
        zephyr = new CharmZephyr();
        resurection = new CharmResurection();
        mundusRoots = new FoodMundusRoots();
        mundusExtract = new FoodMundusExtract();
        silverFruit = new FoodSilverFruit();
        silverJuice = new FoodSilverJuice();
        elixirOfLife = new PotionElixirOfLife();
        philter = new PotionPhilterOfLife();
        allimoreBreaker = new ToolAllimoreBreaker();
        charmTame = new CharmTame();
        stormSurge = new ToolStormSurge();
    }
}

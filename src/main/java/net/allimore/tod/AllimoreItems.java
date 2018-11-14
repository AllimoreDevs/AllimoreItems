package net.allimore.tod;

import com.earth2me.essentials.Essentials;
import net.allimore.tod.items.CharmValkyrie;
import net.allimore.tod.items.ToolStormSurge;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class AllimoreItems extends JavaPlugin {

    private CommandManager cmdManager;
    private InteractListener interactListener;
    private DamageListener damageListener;
    private ConsumeListener consumeListener;
    private TownyListener townyListener;
    private BlockListener blockListner;
    private CraftListener craftListener;

    public static Essentials ESSENTIALS;
    public static Server SERVER;
    public static Plugin PLUGIN;

    @Override
    public void onEnable()
    {
        // Log Plugin Enabled
        getServer().getConsoleSender().sendMessage("[AllimoreItems] Allimore Items enabled!");
        SERVER = getServer();
        PLUGIN = this;

        // Set up Command Handler
        cmdManager = new CommandManager(this);

        // Set up Event Listener
        interactListener = new InteractListener(this);
        damageListener = new DamageListener(this);
        consumeListener = new ConsumeListener(this);
        townyListener = new TownyListener();
        blockListner = new BlockListener();
        craftListener = new CraftListener();
        getServer().getPluginManager().registerEvents(interactListener, this);
        getServer().getPluginManager().registerEvents(damageListener, this);
        getServer().getPluginManager().registerEvents(consumeListener, this);
        getServer().getPluginManager().registerEvents(townyListener, this);
        getServer().getPluginManager().registerEvents(blockListner, this);
        getServer().getPluginManager().registerEvents(craftListener, this);

        ESSENTIALS = (Essentials)getServer().getPluginManager().getPlugin("Essentials");

        TriggerInitalizer.InitTriggers();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage("[AllimoreItems] Allimore Items disabled!");

        CharmValkyrie.FLYING_PLAYERS.forEach((s, player) -> CharmValkyrie.ToggleFlight(player));
        ToolStormSurge.CoolDownTasks.forEach(bukkitRunnable -> bukkitRunnable.run());
    }
}

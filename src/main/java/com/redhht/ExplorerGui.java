package com.redhht;

import com.redhht.commands.HomesCommand;
import com.redhht.commands.TpaMenuCommand;
import com.redhht.gui.TpaGui;
import com.redhht.utils.Configuration;
import com.samjakob.spigui.SpiGUI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ExplorerGui extends JavaPlugin {

    public static SpiGUI spiGUI;
    public static ExplorerGui instance;

    public Configuration mainConfiguration;

    /**
     *
     *
     * @return The instance of plugin charged in the plugin manager.
     */
    public static ExplorerGui getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.mainConfiguration = new Configuration("config.yml");

        spiGUI = new SpiGUI(this);
        this.getLogger().log(Level.INFO, "Se habilito explorergui");
        this.getServer().getPluginManager().registerEvents(new TpaGui(), this);

        this.getCommand("tpamenu").setExecutor(new TpaMenuCommand());
        this.getCommand("homesmenu").setExecutor(new HomesCommand());
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Se desactivo explorergui");
    }
}

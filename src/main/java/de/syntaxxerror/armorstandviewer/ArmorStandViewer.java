package de.syntaxxerror.armorstandviewer;

import de.syntaxxerror.armorstandviewer.listeners.PlayerInteractAtEntityListener;
import de.syntaxxerror.armorstandviewer.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorStandViewer extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = ConfigManager.createSaveAndLoad(this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(configManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package de.syntaxxerror.armorstandviewer;

import de.syntaxxerror.armorstandviewer.listeners.PlayerInteractAtEntityListener;
import de.syntaxxerror.armorstandviewer.utils.ConfigManager;
import de.syntaxxerror.armorstandviewer.utils.Metrics;
import de.syntaxxerror.armorstandviewer.utils.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorStandViewer extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        new UpdateChecker(this, 114195).getVersion(version -> {
            if (!this.getDescription().getVersion().equals(version)) {
                getLogger().warning("There is a new update available.");
            }
        });

        Metrics metrics = new Metrics(this, 114195);
        configManager = ConfigManager.createSaveAndLoad(this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(configManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

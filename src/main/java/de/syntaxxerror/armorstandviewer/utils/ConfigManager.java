package de.syntaxxerror.armorstandviewer.utils;


import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("TryWithIdenticalCatches")
public class ConfigManager {

    private final JavaPlugin plugin;
    private final FileConfiguration settingsConfig = new YamlConfiguration();
    private final FileConfiguration messagesConfig = new YamlConfiguration();
    private final File messagesConfigFile;
    private final File settingsConfigFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        settingsConfigFile = new File(plugin.getDataFolder(), "config.yml");
    }

    public static ConfigManager createSaveAndLoad(JavaPlugin plugin) {
        ConfigManager manager = new ConfigManager(plugin);
        manager.saveDefaults();
        manager.reload();
        return manager;
    }

    private void save() {
        saveMessagesConfig();
        saveSettingsConfig();
    }

    public void reload() {
        reloadMessagesConfig();
        reloadSettingsConfig();
    }

    public void reloadMessagesConfig() {
        try {
            messagesConfig.load(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void reloadSettingsConfig() {
        try {
            settingsConfig.load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaults() {
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        if (!settingsConfigFile.exists()) {
            settingsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }
    }

    public void saveMessagesConfig() {
        try {
            messagesConfig.save(messagesConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSettingsConfig() {
        try {
            settingsConfig.save(settingsConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public FileConfiguration getSettingsConfig() {
        return settingsConfig;
    }
}
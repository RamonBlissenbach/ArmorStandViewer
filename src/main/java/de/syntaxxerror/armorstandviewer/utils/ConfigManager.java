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

    /***
     * Constructor for ConfigManager
     * @param plugin JavaPlugin to load the config files
     */
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        settingsConfigFile = new File(plugin.getDataFolder(), "config.yml");
    }

    /***
     * Creates a new ConfigManager and saves the default config files
     * @param plugin JavaPlugin to load the config files
     * @return ConfigManager
     */
    public static ConfigManager createSaveAndLoad(JavaPlugin plugin) {
        ConfigManager manager = new ConfigManager(plugin);
        manager.saveDefaults();
        manager.reload();
        return manager;
    }

    /***
     * Saves the config files
     */
    private void save() {
        saveMessagesConfig();
        saveSettingsConfig();
    }
    /***
     * Reloads the config files
     */
    public void reload() {
        reloadMessagesConfig();
        reloadSettingsConfig();
    }

    /***
     * Reloads the messages config file
     */
    public void reloadMessagesConfig() {
        try {
            messagesConfig.load(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /***
     * Reloads the settings config file
     */
    public void reloadSettingsConfig() {
        try {
            settingsConfig.load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /***
     * Saves the default config files
     */
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

    /***
     * Saves the messages config file
     */
    public void saveMessagesConfig() {
        try {
            messagesConfig.save(messagesConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Saves the settings config file
     */
    public void saveSettingsConfig() {
        try {
            settingsConfig.save(settingsConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Returns the messages config file
     * @return FileConfiguration
     */
    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    /***
     * Returns the settings config file
     * @return FileConfiguration
     */
    public FileConfiguration getSettingsConfig() {
        return settingsConfig;
    }
}
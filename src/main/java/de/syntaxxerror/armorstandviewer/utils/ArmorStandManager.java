package de.syntaxxerror.armorstandviewer.utils;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorStandManager {

    private ConfigManager configManager;
    private final String inventoryFull;
    private final String guiTitle;
    private final String noItem;
    private final Sound successSound;
    private final Sound errorSound;

    /***
     * Constructor for ArmorStandManager
     * @param configManager ConfigManager to load messages and sounds
     */
    public ArmorStandManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.inventoryFull = configManager.getMessagesConfig().getString("messages.inventory-full").replaceAll("&", "§");
        this.guiTitle = configManager.getMessagesConfig().getString("messages.gui-title").replaceAll("&", "§");
        this.noItem = configManager.getMessagesConfig().getString("messages.no-item").replaceAll("&", "§");

        this.successSound = Sound.valueOf(configManager.getMessagesConfig().getString("sounds.success"));
        this.errorSound = Sound.valueOf(configManager.getMessagesConfig().getString("sounds.error"));
    }

    /***
     * Opens the ArmorStandEditorGui (The main feature of this plugin)
     * @param player Player to open the gui
     * @param armorStand ArmorStand to edit
     */
    public void openArmorStandEditorGui(Player player, ArmorStand armorStand) {
        ItemStack helmet = armorStand.getEquipment().getHelmet();
        ItemStack chestplate = armorStand.getEquipment().getChestplate();
        ItemStack leggings = armorStand.getEquipment().getLeggings();
        ItemStack boots = armorStand.getEquipment().getBoots();
        Gui gui = Gui.gui()
                .title(Component.text(guiTitle))
                .rows(3)
                .create();
        for (int i=0;i<gui.getInventory().getSize();i++) {
            gui.setItem(i, ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).name(Component.text("§7")).asGuiItem());
        }
        gui.setItem(10, ItemBuilder.from(Material.ARMOR_STAND).name(Component.text(guiTitle)).asGuiItem());
        if (helmet.isEmpty()) {
            gui.setItem(12, ItemBuilder.from(Material.LEATHER_HELMET).color(Color.GRAY).name(Component.text(noItem)).asGuiItem());
        } else {
            gui.setItem(12, ItemBuilder.from(helmet).asGuiItem(inventoryClickEvent -> {
                givePlayerArmorStandItem(player, armorStand, "helmet");
            }));
        }
        if (chestplate.isEmpty()) {
            gui.setItem(13, ItemBuilder.from(Material.LEATHER_CHESTPLATE).color(Color.GRAY).name(Component.text(noItem)).asGuiItem());
        } else {
            gui.setItem(13, ItemBuilder.from(chestplate).asGuiItem(inventoryClickEvent -> {
                givePlayerArmorStandItem(player, armorStand, "chestplate");
            }));
        }
        if (leggings.isEmpty()) {
            gui.setItem(14, ItemBuilder.from(Material.LEATHER_LEGGINGS).color(Color.GRAY).name(Component.text(noItem)).asGuiItem());
        } else {
            gui.setItem(14, ItemBuilder.from(leggings).asGuiItem(inventoryClickEvent -> {
                givePlayerArmorStandItem(player, armorStand, "leggings");
            }));
        }
        if (boots.isEmpty()) {
            gui.setItem(15, ItemBuilder.from(Material.LEATHER_BOOTS).color(Color.GRAY).name(Component.text(noItem)).asGuiItem());
        } else {
            gui.setItem(15, ItemBuilder.from(boots).asGuiItem(inventoryClickEvent -> {
                givePlayerArmorStandItem(player, armorStand, "boots");
            }));
        }

        gui.open(player);
    }

    /***
     * Checks if the given material is an armor piece
     * @param material Material to check
     * @return true if the material is an armor piece
     */
    public boolean isArmor(Material material) {
        switch (material) {
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case GOLDEN_HELMET:
            case GOLDEN_CHESTPLATE:
            case GOLDEN_LEGGINGS:
            case GOLDEN_BOOTS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS:
                return true;
            default:
                return false;
        }
    }

    /***
     * Gives the player the selected armor stand item
     * @param player Player to give the item
     * @param armorStand ArmorStand to get the item from
     * @param item Selected item to give to the player
     */
    public void givePlayerArmorStandItem(Player player, ArmorStand armorStand, String item) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getOpenInventory().close();
            player.playSound(player.getLocation(), errorSound, 1, 1);
            player.sendMessage(inventoryFull);
            return;
        }
        player.playSound(player.getLocation(), successSound, 1, 1);
        switch (item) {
            case "helmet":
                player.getInventory().setItem(player.getInventory().firstEmpty(), armorStand.getEquipment().getHelmet());
                break;
            case "chestplate":
                player.getInventory().setItem(player.getInventory().firstEmpty(), armorStand.getEquipment().getChestplate());
                armorStand.getEquipment().setChestplate(new ItemStack(Material.AIR));
                break;
            case "leggings":
                player.getInventory().setItem(player.getInventory().firstEmpty(), armorStand.getEquipment().getLeggings());
                armorStand.getEquipment().setLeggings(new ItemStack(Material.AIR));
                break;
            case "boots":
                player.getInventory().setItem(player.getInventory().firstEmpty(), armorStand.getEquipment().getBoots());
                armorStand.getEquipment().setBoots(new ItemStack(Material.AIR));
                break;
            default:
                break;
        }
        openArmorStandEditorGui(player, armorStand);
    }

}

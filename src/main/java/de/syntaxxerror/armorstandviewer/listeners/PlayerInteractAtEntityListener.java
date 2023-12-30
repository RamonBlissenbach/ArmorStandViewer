package de.syntaxxerror.armorstandviewer.listeners;

import de.syntaxxerror.armorstandviewer.utils.ArmorStandManager;
import de.syntaxxerror.armorstandviewer.utils.ConfigManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractAtEntityListener implements Listener {

    private final ConfigManager configManager;
    private boolean requirePermission;
    private String permission;
    public PlayerInteractAtEntityListener(ConfigManager configManager) {
        this.configManager = configManager;
        requirePermission = configManager.getSettingsConfig().getBoolean("permission.required");
        permission = configManager.getSettingsConfig().getString("permission.permission");
    }


    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)) {
            if (requirePermission) {
                if (event.getPlayer().hasPermission(permission)) {
                    ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
                    ArmorStandManager armorStandManager = new ArmorStandManager(configManager);
                    if (!armorStandManager.isArmor(itemInHand.getType())) {
                        event.setCancelled(true);
                        armorStandManager.openArmorStandEditorGui(event.getPlayer(), (ArmorStand) event.getRightClicked());
                    }
                }
            } else {
                ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
                ArmorStandManager armorStandManager = new ArmorStandManager(configManager);
                if (!armorStandManager.isArmor(itemInHand.getType())) {
                    event.setCancelled(true);
                    armorStandManager.openArmorStandEditorGui(event.getPlayer(), (ArmorStand) event.getRightClicked());
                }
            }
        }
    }

}

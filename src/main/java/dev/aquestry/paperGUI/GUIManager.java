package dev.aquestry.paperGUI;

import dev.aquestry.paperGUI.models.Menu;
import dev.aquestry.paperGUI.useModels.MenuTemplate;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager implements Listener {

    public static Map<UUID, Menu> menuMap = new HashMap<>();

    public GUIManager(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onClick(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof Player target) {
            Player player = event.getPlayer();
            openMenu(PaperGUI.defaultMenu, player, target);
        }
        if(entity instanceof Interaction box) {
            menuMap.forEach((player, menu) -> menu.options.forEach(option -> {
                if(option.isntInteractable()) return;
                if(option.getInteraction().equals(box)) {
                   option.runCommand();
                   menu.remove();
                }
            }));
        }
    }

    @EventHandler
    private void onClick(PrePlayerAttackEntityEvent event) {
        Entity entity = event.getAttacked();
        if(entity instanceof Interaction box) {
            menuMap.forEach((player, menu) -> menu.options.forEach(option -> {
                if(option.isntInteractable()) return;
                if(option.getInteraction().equals(box)) {
                    option.runCommand();
                    menu.remove();
                }
            }));
        }
    }

    private static void openMenu(MenuTemplate menuTemplate, Player player, Player target) {
        if(!menuMap.containsKey(player)) {
            menuMap.put(player.getUniqueId(), menuTemplate.init(player, target));
        } else {
            menuMap.get(player).remove();
            menuMap.remove(player);
            menuMap.put(player.getUniqueId(), menuTemplate.init(player, target));
        }
    }
}
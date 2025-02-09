package dev.aquestry.paperGUI;

import dev.aquestry.paperGUI.models.Menu;
import dev.aquestry.paperGUI.models.Option;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Map;
import java.util.UUID;

public class MenuTicker implements Runnable {
    public MenuTicker(JavaPlugin plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
    }

    public void run() {
        tick();
    }

    public void tick(){
        for(Map.Entry<UUID, Menu> entry : GUIManager.menuMap.entrySet()) {
            Menu menu = entry.getValue();
            Player player = Bukkit.getPlayer(entry.getKey());
            if(player.isOnline()) {
                if(player.getUniqueId().equals(entry.getKey()) && player.getLocation().distance(menu.options.getFirst().getInteraction().getLocation()) > 5) {
                    menu.remove();
                    GUIManager.menuMap.remove(entry.getValue());
                } else {
                    menu.options.forEach(Option::updateScale);
                }
            } else {
                menu.remove();
                GUIManager.menuMap.remove(entry.getValue());
            }
        }
    }
}
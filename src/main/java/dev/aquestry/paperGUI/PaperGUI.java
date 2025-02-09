package dev.aquestry.paperGUI;

import dev.aquestry.paperGUI.useModels.MenuTemplate;
import dev.aquestry.paperGUI.useModels.OptionTemplate;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

public final class PaperGUI extends JavaPlugin {

    Logger logger = LoggerFactory.getLogger("PaperGUI");
    public static MenuTemplate defaultMenu;

    @Override
    public void onEnable() {
        new GUIManager(this);
        logger.info("Loading Menu...");
        loadMenu();
        getCommand("reload-ppgui").setExecutor(new ReloadCommand(this));
    }

    public void loadMenu() {
        saveDefaultConfig();
        ConfigurationSection menuSection = getConfig().getConfigurationSection("menus.default");
        if (menuSection == null) return;
        String title = menuSection.getString("title", "Menu");
        MenuTemplate menu = new MenuTemplate(title);
        List<Map<?, ?>> options = menuSection.getMapList("options");
        for (Map<?, ?> option : options) {
            String name = (String) option.get("name");
            logger.info("Loading option: {}!", name);
            Material material = Material.matchMaterial((String) option.get("material"));
            List<?> posList = (List<?>) option.get("offset");
            if (posList == null || posList.size() != 3) continue;
            float x = ((Number) posList.get(0)).floatValue();
            float y = ((Number) posList.get(1)).floatValue();
            float z = ((Number) posList.get(2)).floatValue();
            Vector3f position = new Vector3f(x, y, z);
            String command = (String) option.get("command");
            menu.addPart(new OptionTemplate(name, material, position, command));
        }
        defaultMenu = menu;
    }
}
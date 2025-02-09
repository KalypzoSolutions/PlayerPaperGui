package dev.aquestry.paperGUI.models;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    public final String name;
    public final Player player;
    public final Entity target;
    public final List<Option> options;

    public Menu(String name, Player player, Entity target, List<Option> options) {
        this.name = name;
        this.player = player;
        this.target = target;
        this.options = new ArrayList<>(options);
    }

    public void remove() {
      for (Option option : options) {
        option.remove();
      }
    }
}
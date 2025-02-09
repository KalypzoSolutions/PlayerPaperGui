package dev.aquestry.paperGUI.useModels;

import dev.aquestry.paperGUI.models.Menu;
import dev.aquestry.paperGUI.models.Option;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class MenuTemplate {

    private final String name;
    private List<OptionTemplate> optionTemplates = new ArrayList<>();

    public MenuTemplate(String name) {
        this.name = name;
    }

    public void addPart(OptionTemplate optionTemplate) {
        optionTemplates.add(optionTemplate);
    }

    public Menu init(Player player, Player target) {
        List<Option> options = new ArrayList<>();
        for(OptionTemplate optionTemplate : optionTemplates) {
            options.add(new Option(optionTemplate.text, player, target, optionTemplate.offset, optionTemplate.material, optionTemplate.command, optionTemplate.interactable));
        }
        options.add(new Option(name.replace("<target>", target.getName()), player, target, new Vector3f(optionTemplates.getLast().offset).add(0, 0.5f, 0), Material.AIR, "", false));
        if(!options.isEmpty()) {
          return new Menu(name, player, target, options);
        }
        return null;
    }
}
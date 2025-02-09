package dev.aquestry.paperGUI.useModels;

import dev.aquestry.paperGUI.models.Menu;
import dev.aquestry.paperGUI.models.Option;
import org.bukkit.entity.Player;
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
            options.add(new Option(optionTemplate.text, player, target, optionTemplate.offset, optionTemplate.material, optionTemplate.command));
        }
        if(!options.isEmpty()) {
          return new Menu(name, player, target, options);
        }
        return null;
    }
}
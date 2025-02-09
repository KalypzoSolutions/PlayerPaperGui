package dev.aquestry.paperGUI;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private PaperGUI plugin;

    public ReloadCommand(PaperGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!commandSender.hasPermission("ppgui.command.reload")) {
            commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You don't have permission to execute this command."));
            return false;
        }
        plugin.reloadConfig();
        PaperGUI.defaultMenu = null;
        plugin.loadMenu();
        commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Reload completed!."));
        return true;
    }
}
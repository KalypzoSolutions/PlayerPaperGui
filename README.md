# PaperGUI

PaperGUI is a Paper plugin that provides an interactive in-game GUI system. It allows players to interact with other players through a floating menu that appears near their avatar. This menu can be fully customized via the `config.yml` file to include various options, commands, and visual elements.

## Features
- **Dynamic Floating Menu**: Displays a GUI near a targeted player.
- **Customizable Options**: Define menu options in `config.yml` with custom commands, item icons, and offsets.
- **Interactive Click System**: Players can interact with menu options by right-clicking.
- **Auto-Cleanup**: Menus and options are automatically removed when the plugin shuts down.

## Installation
1. Download the plugin JAR file.
2. Place it in the `plugins` folder of your Paper server.
3. Restart or reload the server.
4. Modify the `config.yml` file to customize the menu options.

## Configuration (config.yml)
```yaml
menus:
  default:
    title: "<bold>Interact with <target>"
    options:
      - name: "Say hello!"
        material: "RED_DYE"
        offset: [0, 0.5, 0]
        command: "msg <target> hello!"
        interactable: true
      - name: "Ban <target>"
        material: "DIAMOND_SWORD"
        offset: [0, 1.25, 0]
        command: "ban <target>"
        interactable: true
      - name: "Add friend"
        material: "BLACK_DYE"
        offset: [0, 2, 0]
        command: "friend add <target>"
        interactable: true
```

### Explanation:
- `title`: The menu title.
- `options`: A list of interactable options.
    - `name`: The display name (supports MiniMessage format).
    - `material`: The item representing the option.
    - `offset`: The position relative to the target.
    - `command`: The command executed when clicked (`<target>` is replaced with the selected player).
    - `interactable`: Makes the option interactable or not.

## How to Use
1. Right-click on a player to open the menu.
2. Click an option to execute its command (Left and right click).
3. The menu disappears after interaction or when you are more than five blocks away.

## Requirements
- PaperMC 1.21.4

## Commands
**/reload-ppgui**

Description: Reloads the config.

Permission: ppgui.command.reload

## Credits
- **Author**: Aquestry
- **Website**: [https://aquestry.dev](https://aquestry.dev)
package dev.aquestry.paperGUI.useModels;

import org.bukkit.Material;
import org.joml.Vector3f;

public class OptionTemplate {
    public String text;
    public Vector3f offset;
    public Material material;
    public String command;
    public boolean interactable;

    public OptionTemplate(String text, Material material, Vector3f offset, String command, boolean interactable) {
      this.text = text;
      this.material = material;
      this.offset = offset;
      this.command = command;
      this.interactable = interactable;
    }
}
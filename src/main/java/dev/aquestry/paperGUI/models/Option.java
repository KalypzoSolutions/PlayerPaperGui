package dev.aquestry.paperGUI.models;

import dev.aquestry.paperGUI.PaperGUI;
import io.papermc.paper.entity.LookAnchor;
import io.papermc.paper.math.Position;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.Objects;

public class Option implements Listener {

    private final Player player;
    private final Player target;
    private final Material material;
    private final String command;
    private final String infoText;
    private final Vector3f playerLocation;
    private final Vector3f targetLocation;
    private final Location base;

    private TextDisplay text;
    private Interaction collider;
    private ItemDisplay display;
    private JavaPlugin plugin = JavaPlugin.getPlugin(PaperGUI.class);

    public Option(@NotNull String text, @NotNull Player player, @NotNull Player target, @NotNull Vector3f offset, @NotNull Material material, @NotNull String command) {
        this.infoText = text;
        this.player = player;
        this.target = target;
        this.command = command;
        this.material = material;
        playerLocation = new Vector3f((float) player.getX(), (float) player.getY(), (float) player.getZ());
        targetLocation = new Vector3f((float) target.getX(), (float) target.getY(), (float) target.getZ());
        Vector3f temp = calculateNextEastPos(playerLocation, targetLocation).add(offset);
        base = new Location(player.getWorld(), temp.x, temp.y, temp.z);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::updateScale, 0, 1);
        spawnDisplay();
        spawnCollider();
        spawnText();
    }

    public static Vector3f calculateNextEastPos(Vector3f source, Vector3f target) {
        double dx = target.x() - source.x();
        double dz = target.z() - source.z();
        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        return target.add((float) Math.cos(Math.toRadians(yaw)) * 1.5f, 0, (float) Math.sin(Math.toRadians(yaw)) * 1.5f);
    }

    private void spawnText() {
        Vector3f temp = new Vector3f((float) base.getX(), (float) base.getY(), (float) base.getZ());
        Vector3f almost = getTowards(temp, playerLocation, 0.15f);
        text = player.getWorld().spawn(new Location(player.getWorld(), almost.x, almost.y, almost.z).add(0, 0.25f, 0), TextDisplay.class);
        text.setVisibleByDefault(false);
        text.setBillboard(Display.Billboard.FIXED);
        text.lookAt(Position.fine(player.getX(), player.getY(), player.getZ()), LookAnchor.EYES);
        text.setRotation(display.getYaw(), 0);
        text.text(MiniMessage.miniMessage().deserialize(infoText));
        text.setSeeThrough(false);
        text.setDisplayWidth(0.1f);
        text.setDisplayHeight(0.1f);
        text.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        player.showEntity(plugin, text);
    }

    private void spawnDisplay() {
        display = player.getWorld().spawn(base, ItemDisplay.class);
        display.setVisibleByDefault(false);
        display.lookAt(Position.fine(player.getX(), player.getY(), player.getZ()), LookAnchor.EYES);
        display.setRotation(display.getYaw(), 0);
        display.setItemStack(new ItemStack(material));
        player.showEntity(plugin, display);
    }

    private void spawnCollider() {
        collider = player.getWorld().spawn(base.add(0, -0.25f, 0), Interaction.class);
        collider.setVisibleByDefault(false);
        collider.setInteractionWidth(0.5f);
        collider.setInteractionHeight(0.5f);
        player.showEntity(plugin, collider);
    }

    private void updateScale() {
        if (!player.equals(player)) return;
        RayTraceResult rayTraceResult = player.rayTraceEntities(5);
        if (rayTraceResult != null) {
            if (Objects.requireNonNull(rayTraceResult.getHitEntity()).equals(collider)) {
                display.setTransformation(new Transformation(new Vector3f(0, 0, 0), new Quaternionf(), new Vector3f(1f, 1f, 1f), new Quaternionf()));
                return;
            }
        }
        display.setTransformation(new Transformation(new Vector3f(0, 0, 0), new Quaternionf(), new Vector3f(0.75f, 0.75f, 0.75f), new Quaternionf()));
    }

    public Vector3f getTowards(Vector3f source, Vector3f target, double distance) {
        Vector3f direction = new Vector3f(
                target.x() - source.x(),
                target.y() - source.y(),
                target.z() - source.z()
        );
        double length = Math.sqrt(
                direction.x() * direction.x() +
                        direction.y() * direction.y() +
                        direction.z() * direction.z()
        );
        if (length == 0) {
            return source;
        }
        double scale = Math.min(distance / length, 1.0);
        return new Vector3f(
                (float) (source.x() + direction.x() * scale),
                (float) (source.y() + direction.y() * scale),
                (float) (source.z() + direction.z() * scale)
        );
    }

    public Interaction getInteraction() {
        return collider;
    }

    public void runCommand() {
        if(command.equals("<none>")) return;
        player.performCommand(command.replace("<target>", target.getName()));
    }

    public void remove() {
        display.remove();
        collider.remove();
        text.remove();
    }
}
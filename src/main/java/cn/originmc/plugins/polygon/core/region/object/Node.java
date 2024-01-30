package cn.originmc.plugins.polygon.core.region.object;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Node implements ConfigurationSerializable {
    private double x;
    private double z;

    private double height = 0;

    public Node(double x, double z) {
        this.x = x;
        this.z = z;
    }
    public Node(Location location){
        this.x = location.getX();
        this.z = location.getZ();
    }

    public Location getBukkitLocation(World world, double y) {
        return new Location(world, x, y, z);
    }

    public Location getBukkitLocation(World world) {
        return new Location(world, this.getX(), height, getZ());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("x", this.x);
        result.put("z", this.z);
        result.put("height", this.height);
        return result;
    }

    public static Node deserialize(Map<String, Object> map) {

        Node node = new Node((double) map.get("x"), (double) map.get("z"));
        node.setHeight((double) map.get("height"));
        return node;
    }
}

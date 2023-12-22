package cn.originmc.plugins.polygon.core.region.object;

import org.bukkit.Location;
import org.bukkit.World;

public class Node {
    private double x;
    private double z;

    private double height=0;

    public Node(double x, double z) {
        this.x = x;
        this.z = z;
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
}

package cn.originmc.plugins.Polygon.core.region.object;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Node {
    private double x;
    private double z;

    public Node(double x,double z){
        this.x=x;
        this.z=z;
    }
    public Location getBukkitLocation(World world,double y){
        return new Location(world,x,y,z);
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
}

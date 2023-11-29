package cn.originmc.plugins.Polygon.core.region.object;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Region {
    private List<Node> nodes = new ArrayList<>();
    private String world;
    private double maxHeight;
    private double minHeight;

    public Region() {
    }

    public Region(String world, double maxHeight, double minHeight) {
        this.world = world;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
    }

    public boolean isInWorld(Location location) {
        return location.getWorld().getName().equalsIgnoreCase(world);
    }

    public boolean isInHeight(Location location) {
        double y = location.getY();
        return y >= minHeight && y <= maxHeight;
    }

    public boolean isInsideRegion(Location location) {
        double x = location.getX();
        double z = location.getZ();
        int count = 0;
        int numNodes = nodes.size();
        for (int i = 0; i < numNodes; i++) {

            double x1 = nodes.get(i).getX();
            double z1 = nodes.get(i).getZ();
            double x2 = nodes.get((i + 1) % numNodes).getX();
            double z2 = nodes.get((i + 1) % numNodes).getZ();
            if (((z1 <= z && z < z2) || (z2 <= z && z < z1)) &&
                    (x < (x2 - x1) * (z - z1) / (z2 - z1) + x1)) {
                count++;
            }
        }
        return count % 2 != 0;
    }

    public Node calculateCenter() {
        if (nodes.size() < 3) {
            return null;
        }

        double totalX = 0.0;
        double totalZ = 0.0;

        // 计算节点 x 和 z 坐标的总和
        for (Node node : nodes) {
            totalX += node.getX();
            totalZ += node.getZ();
        }

        // 计算平均值作为中心点坐标
        double centerX = totalX / nodes.size();
        double centerZ = totalZ / nodes.size();

        return new Node(centerX, centerZ);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}

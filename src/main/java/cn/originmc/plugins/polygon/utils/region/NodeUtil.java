package cn.originmc.plugins.polygon.utils.region;

import cn.originmc.plugins.polygon.core.region.object.Node;

import java.util.List;

public class NodeUtil {
    public static double getMaxHeight(List<Node> nodes) {
        double max = 0;
        for (Node node : nodes) {
            if (node.getHeight() > max) {
                max = node.getHeight();
            }
        }
        return max;
    }

    public static double getMinHeight(List<Node> nodes) {
        double min = 999999;
        for (Node node : nodes) {
            if (node.getHeight() < min) {
                min = node.getHeight();
            }
        }
        return min;
    }
}

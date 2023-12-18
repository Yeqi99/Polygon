package cn.originmc.plugins.Polygon.core.building.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private Vector center;
    private List<Vector> relativeNodes;
    private List<BlockData> blockDataList;

    public Building(Vector center) {
        this.center = center;
        this.relativeNodes = new ArrayList<>();
        this.blockDataList = new ArrayList<>();
    }

    // 方法来添加方块数据
    public void addBlockData(Vector relativePosition, Material material) {
        blockDataList.add(new BlockData(relativePosition, material));
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }

    public List<Vector> getRelativeNodes() {
        return relativeNodes;
    }

    public void setRelativeNodes(List<Vector> relativeNodes) {
        this.relativeNodes = relativeNodes;
    }

    public List<BlockData> getBlockDataList() {
        return blockDataList;
    }

    public void setBlockDataList(List<BlockData> blockDataList) {
        this.blockDataList = blockDataList;
    }

    // 方法来还原建筑
    public void restoreBuilding(Location centerLocation) {
        Vector centerVector = centerLocation.toVector();

        for (BlockData blockData : blockDataList) {
            Vector relativePosition = blockData.getRelativePosition();
            Material material = blockData.getMaterial();

            // 计算实际的世界坐标
            Vector worldPosition = centerVector.clone().add(relativePosition);
            Location blockLocation = new Location(centerLocation.getWorld(), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

            // 在 Minecraft 世界中放置这个方块
            blockLocation.getBlock().setType(material);
        }
    }

    // BlockData 类来存储每个方块的信息
    public static class BlockData {
        private Vector relativePosition;
        private Material material;

        public BlockData(Vector relativePosition, Material material) {
            this.relativePosition = relativePosition;
            this.material = material;
        }

        public Vector getRelativePosition() {
            return relativePosition;
        }

        public void setRelativePosition(Vector relativePosition) {
            this.relativePosition = relativePosition;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }
    }
}

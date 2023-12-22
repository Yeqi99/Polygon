package cn.originmc.plugins.polygon.core.building.object;

import cn.originmc.plugins.polygon.Polygon;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Iterator;
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
            if (material==Material.AIR){
                continue;
            }
            // 计算实际的世界坐标
            Vector worldPosition = centerVector.clone().add(relativePosition);
            Location blockLocation = new Location(centerLocation.getWorld(), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

            // 在 Minecraft 世界中放置这个方块
            blockLocation.getBlock().setType(material);
        }
    }


    public void restoreBuildingAsync(Location centerLocation, long delay) {
        new BukkitRunnable() {
            final Iterator<BlockData> iterator = blockDataList.iterator();

            @Override
            public void run() {
                while (iterator.hasNext()) {
                    BlockData blockData = iterator.next();
                    Material material = blockData.getMaterial();

                    if (material == Material.AIR) {
                        continue; // 如果是空气，跳过当前循环，立即处理下一个方块
                    }

                    Vector relativePosition = blockData.getRelativePosition();
                    Vector worldPosition = centerLocation.toVector().clone().add(relativePosition);
                    Location blockLocation = new Location(centerLocation.getWorld(), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

                    // 在主线程中放置方块
                    Bukkit.getScheduler().runTask(Polygon.getInstance(), () -> blockLocation.getBlock().setType(material));

                    // 在放置非空气方块后，等待下一次迭代
                    break;
                }

                if (!iterator.hasNext()) {
                    // 如果所有方块都已处理，取消这个任务
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(Polygon.getInstance(), 0L, delay); // 时间间隔转换为游戏刻
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

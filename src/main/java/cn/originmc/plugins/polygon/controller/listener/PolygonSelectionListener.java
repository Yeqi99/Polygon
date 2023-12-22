package cn.originmc.plugins.polygon.controller.listener;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.region.object.Node;
import cn.originmc.plugins.polygon.utils.hook.ProtocolLibHook;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PolygonSelectionListener implements Listener {
    public static Map<Player, List<Node>> selectionMap = new ConcurrentHashMap<>();

    @EventHandler
    public void onSelection(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.GOLDEN_SHOVEL) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (Polygon.getTerritoryManager().hasTerritory(block.getLocation())) {
                Polygon.getSender().sendToPlayer(player, "这里属于别人");
                event.setCancelled(true);
                return;
            }
            Node node = new Node(block.getX(), block.getZ());
            node.setHeight(block.getY());
            ProtocolLibHook.sendFakeBlock(block.getLocation(), Material.GOLD_BLOCK, player);
            if (selectionMap.containsKey(player)) {
                List<Node> nodeList = selectionMap.get(player);
                for (Node node1 : nodeList) {
                    if (node1.getX()==node.getX() && node1.getZ()==node.getZ()){
                        Polygon.getSender().sendToPlayer(player, "这里已经选择过了");
                        event.setCancelled(true);
                        return;
                    }
                }

                nodeList.add(node);
                selectionMap.put(player, nodeList);
                Polygon.getSender().sendToPlayer(player, "&a已经选择第" + nodeList.size() + "个点");
            } else {
                List<Node> nodeList = new ArrayList<>();
                nodeList.add(node);
                selectionMap.put(player, nodeList);
                Polygon.getSender().sendToPlayer(player, "&a已经选择第1个点");
            }
            event.setCancelled(true);
        } else {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if (selectionMap.containsKey(player)) {
                    Block block = event.getClickedBlock();
                    Node node = new Node(block.getX(), block.getZ());
                    node.setHeight(block.getY());
                    List<Node> nodeList = selectionMap.get(player);
                    List<Node> result = new ArrayList<>();
                    for (Node node1 : nodeList) {
                        if (node1.getX()==node.getX() && node1.getZ()==node.getZ()){
                            Polygon.getSender().sendToPlayer(player, "&a已经取消这个点");
                            continue;
                        }
                        result.add(node1);
                    }

                    selectionMap.put(player, result);
                }
                event.setCancelled(true);
                return;
            }
            if (selectionMap.containsKey(player)) {
                List<Node> nodeList = selectionMap.get(player);
                if (nodeList == null || nodeList.isEmpty()) {
                    Polygon.getSender().sendToPlayer(player, "&c你还没有选择区域");
                    event.setCancelled(true);
                    return;
                }

                // 移除最后一个元素
                Node lastNode = nodeList.remove(nodeList.size() - 1);
                Location removeLocation = lastNode.getBukkitLocation(player.getWorld());

                // 用原本的方块类型更新该位置
                ProtocolLibHook.sendFakeBlock(removeLocation, removeLocation.getBlock().getType(), player);

                // 不需要再次放入 selectionMap，因为 nodeList 是引用
                Polygon.getSender().sendToPlayer(player, "&a已经取消上一个点");
            } else {
                Polygon.getSender().sendToPlayer(player, "&c你还没有选择区域");
            }
            event.setCancelled(true);
        }
    }
}

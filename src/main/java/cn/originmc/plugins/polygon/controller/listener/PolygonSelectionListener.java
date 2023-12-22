package cn.originmc.plugins.polygon.controller.listener;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.region.object.Node;
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
        if (!event.hasBlock()) {
            return;
        }
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
            if (selectionMap.containsKey(player)) {
                List<Node> nodeList = selectionMap.get(player);
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
            if (selectionMap.containsKey(player)) {
                List<Node> nodeList = selectionMap.get(player);
                if (nodeList.isEmpty()) {
                    Polygon.getSender().sendToPlayer(player, "&c你还没有选择区域");
                    event.setCancelled(true);
                    return;
                }
                nodeList = nodeList.subList(0, nodeList.size() - 1);
                selectionMap.put(player, nodeList);
                Polygon.getSender().sendToPlayer(player, "&a已经取消上一个点");
            } else {
                Polygon.getSender().sendToPlayer(player, "&c你还没有选择区域");
            }
            event.setCancelled(true);
        }
    }
}

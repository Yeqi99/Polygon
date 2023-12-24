package cn.originmc.plugins.polygon.utils.hook;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ProtocolLibHook {
    public static boolean status = false;
    public static com.comphenix.protocol.ProtocolManager pm;

    public static String getName() {
        return "ProtocolLib";
    }

    public static void hook() {
        status = Hook.hook(getName());
        if (status) {
            pm = com.comphenix.protocol.ProtocolLibrary.getProtocolManager();
        }
    }

    public static void sendFakeBlock(Location loc, Material type, Player player) {
        if (!status) {
            return;
        }
        ProtocolManager protocolManager = pm;
        PacketContainer blockChangePacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);

        blockChangePacket.getBlockPositionModifier().write(0, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        blockChangePacket.getBlockData().write(0, WrappedBlockData.createData(type));

        protocolManager.sendServerPacket(player, blockChangePacket);
    }

    public static void sendTextDisplay(Player player, Location loc, int entityId, String content) {
        ProtocolManager protocolManager = pm;

        // 创建用于生成文本展示实体的数据包
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        PacketContainer packetData = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        // 实体ID - 每个实体都有一个唯一的ID
        packet.getIntegers().write(0, entityId);
        // 实体ID - 每个实体都有一个唯一的ID
        packetData.getIntegers().write(0, entityId);
        // 实体的UUID - 随机生成，用于唯一标识实体
        packet.getUUIDs().write(0, UUID.randomUUID());

        // 设置实体类型为文本展示
        packet.getEntityTypeModifier().write(0, EntityType.TEXT_DISPLAY);

        // 设置实体在世界中的位置（X, Y, Z坐标）
        packet.getDoubles().write(0, loc.getX());
        packet.getDoubles().write(1, loc.getY());
        packet.getDoubles().write(2, loc.getZ());

        // 创建数据观察器，用于管理实体的数据
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer chatSerializer = WrappedDataWatcher.Registry.getChatComponentSerializer();
        watcher.setEntity(player);
        // 设置文本展示实体的数据 - 索引23通常用于文本
        watcher.setObject(0, chatSerializer, createChatComponent(content));
        packetData.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        // 发送数据包
        try {
            protocolManager.sendServerPacket(player, packet);
            protocolManager.sendServerPacket(player, packetData);
        } catch (Exception e) {
            e.printStackTrace(); // 打印错误堆栈信息
        }
    }

    public static TextComponent createChatComponent(String content) {
        // 创建一个新的 TextComponent 实例
        TextComponent textComponent = new TextComponent(content);

        // 可以在这里添加更多的设置，比如颜色、格式等
        // 例如：textComponent.setColor(ChatColor.RED);

        return textComponent;
    }
}

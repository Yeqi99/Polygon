package cn.originmc.plugins.polygon.utils.hook;

import cn.originmc.plugins.polygon.Polygon;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.accessors.FieldAccessor;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.entity.CreatureSpawnEvent;

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

    public static void sendDisplayEntity(Location loc, Player player, int entityId, String customName, boolean isVisible) {
        if (!status) {
            return;
        }
        ProtocolManager protocolManager = pm;

        // 创建 PacketContainer 来发送实体信息
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        // 基础数据设置
        packet.getIntegers().write(0, entityId);
        packet.getUUIDs().write(0, UUID.randomUUID());
        packet.getEntityTypeModifier().write(0, EntityType.TEXT_DISPLAY);
        packet.getDoubles().write(0, loc.getX());
        packet.getDoubles().write(1, loc.getY());
        packet.getDoubles().write(2, loc.getZ());
        Polygon.getSender().sendToAllPlayer(packet.toString());

        Entity entity = player.getWorld().spawnEntity(loc,EntityType.TEXT_DISPLAY);
        TextDisplay textDisplay = (TextDisplay) entity;
        textDisplay.setText("你好哦~");
        entity.remove();
        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

        packet.getDataWatcherModifier().write(0,watcher);
        pm.sendServerPacket(player, packet);
    }

    public static WrappedChatComponent createChatComponent(String text) {
        // 使用 BungeeCord 的 TextComponent，您也可以选择其他方式来构建文本
        TextComponent textComponent = new TextComponent(text);

        // 将 TextComponent 转换为 JSON 格式的字符串
        String json = TextComponent.toLegacyText(textComponent);

        // 创建并返回 WrappedChatComponent
        return WrappedChatComponent.fromJson(json);
    }
}

package cn.originmc.plugins.polygon.utils.text;


import cn.originmc.plugins.polygon.utils.hook.PlaceholderAPIHook;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class Sender {
    private static JavaPlugin plugin;

    /**
     * 构造方法
     *
     * @param plugin 插件实例
     */
    public Sender(JavaPlugin plugin) {
        setPlugin(plugin);
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(JavaPlugin plugin) {
        Sender.plugin = plugin;
    }

    /**
     * 向控制台发送字符串
     *
     * @param inStr 要发送的字符串
     */
    public void sendToLogger(String inStr) {
        getPlugin().getServer().getConsoleSender().sendMessage(Color.toColor(inStr));
    }

    /**
     * 向控制台发送字符串列表
     *
     * @param inList 要发送的字符串列表
     */
    public void sendToLogger(List<String> inList) {
        for (String s : inList) {
            sendToLogger(s);
        }
    }

    /**
     * 给玩家发送字符串
     *
     * @param player 对应玩家
     * @param inStr  要发送的字符串
     */
    public void sendToPlayer(Player player, String inStr) {
        player.sendMessage(Color.toColor(inStr));
    }

    /**
     * 给玩家发送字符串列表
     *
     * @param player 对应玩家
     * @param inList 要发送的字符串列表
     */
    public void sendToPlayer(Player player, List<String> inList) {
        for (String s : inList) {
            player.sendMessage(Color.toColor(s));
        }
    }

    /**
     * 给玩家发送交互文本列表
     *
     * @param player 对应玩家
     * @param bcList 交互文本列表
     */
    public void sendToPlayerBC(Player player, List<BaseComponent[]> bcList) {
        if (bcList == null) {
            return;
        }
        for (BaseComponent[] bc : bcList) {
            player.spigot().sendMessage(bc);
        }
    }

    /**
     * 给玩家发送交互文本
     *
     * @param player 对应玩家
     * @param bc     交互文本
     */
    public void sendToPlayerBC(Player player, BaseComponent[] bc) {
        if (bc == null) {
            return;
        }
        player.spigot().sendMessage(bc);
    }

    public void sendToSender(CommandSender sender, String inStr) {
        sender.sendMessage(Color.toColor(String.valueOf(inStr)));
    }

    public void sendToSender(CommandSender sender, List<String> inList) {
        for (String s : inList) {
            if (s != null) {
                sender.sendMessage(Color.toColor(s));
            }
        }
    }

    public void sendToSender(CommandSender sender, List<String> inList, String prefix) {
        for (String s : inList) {
            if (s != null) {
                sender.sendMessage(Color.toColor(prefix + s));
            }
        }
    }

    public void sendToAllPlayer(String str) {
        Bukkit.broadcastMessage(Color.toColor(str));
    }

    public void sendToAllPlayer(List<String> inList) {
        for (String s : inList) {
            Bukkit.broadcastMessage(Color.toColor(s));
        }
    }

    public void sendToAllTitle(String title, String sub, int fadeIn, int stay, int fadeOut) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendTitle(Color.toColor(title), Color.toColor(sub), fadeIn, stay, fadeOut);
        }
    }


    public void sendPAPIToOnline(String inStr) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String message = PlaceholderAPIHook.getPlaceholder(onlinePlayer, inStr);
            sendToPlayer(onlinePlayer, message);
        }
    }
}

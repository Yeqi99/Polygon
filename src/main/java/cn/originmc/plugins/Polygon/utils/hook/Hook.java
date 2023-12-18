package cn.originmc.plugins.Polygon.utils.hook;


import cn.originmc.plugins.Polygon.Polygon;
import org.bukkit.Bukkit;

public class Hook {

    public static boolean hook(String name) {
        if (Bukkit.getPluginManager().getPlugin(name) != null) {
            Polygon.getSender().sendToLogger(getLog(name, true));
            return true;
        } else {
            Polygon.getSender().sendToLogger(getLog(name, false));
            return false;
        }
    }

    public static String getLog(String name, boolean status) {
        if (status) {
            return "§a[§bMagicPaper§a] §e成功挂钩 §a~ §e插件"
                    .replace("~", name);
        } else {
            return "§a[§bMagicPaper§a] §e未找到 §a~ §e插件"
                    .replace("~", name);
        }
    }

}

package cn.originmc.plugins.polygon.data.yaml.config;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.tools.minecraft.yamlcore.object.YamlManager;

public class LangData {
    public static YamlManager yamlManager;

    public static void load() {
        yamlManager = new YamlManager(Polygon.getInstance(), "lang", true);
    }

    public static String get(String lang, String key, String def) {
        return ((String) yamlManager.get(lang, key, def)).replace("&", "§");
    }

    public static String getServerLang() {
        return Polygon.getInstance().getConfig().getString("lang", "English");
    }

    public static String getPrefix() {
        return get(getServerLang(), "prefix", "§a[§bPolygon§a] ");
    }

    public static String getServerText(String key, String def) {
        return get(getServerLang(), key, def);
    }
}

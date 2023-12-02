package cn.originmc.plugins.Polygon;

import cn.originmc.plugins.Polygon.core.region.manager.TerritoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Polygon extends JavaPlugin {
    private static Polygon instance;
    private static TerritoryManager territoryManager;

    public static Polygon getInstance() {
        return instance;
    }

    public static TerritoryManager getTerritoryManager() {
        return territoryManager;
    }

    @Override
    public void onEnable() {
        instance=this;
        loadOrReload();
    }
    @Override
    public void onDisable() {

    }

    public static void loadOrReload(){
        territoryManager=new TerritoryManager();
    }
}

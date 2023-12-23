package cn.originmc.plugins.polygon;

import cn.originmc.plugins.polygon.controller.command.PolygonCommand;
import cn.originmc.plugins.polygon.controller.command.PolygonTabCompleter;
import cn.originmc.plugins.polygon.controller.listener.PolygonSelectionListener;
import cn.originmc.plugins.polygon.core.building.manager.BuildingManager;
import cn.originmc.plugins.polygon.core.region.manager.TerritoryManager;
import cn.originmc.plugins.polygon.utils.hook.PlaceholderAPIHook;
import cn.originmc.plugins.polygon.utils.hook.ProtocolLibHook;
import cn.originmc.plugins.polygon.utils.text.Sender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Polygon extends JavaPlugin {
    private static Polygon instance;
    private static TerritoryManager territoryManager;
    private static BuildingManager buildingManager;
    private static Sender sender;

    public static Polygon getInstance() {
        return instance;
    }

    public static TerritoryManager getTerritoryManager() {
        return territoryManager;
    }

    public static BuildingManager getBuildingManager() {
        return buildingManager;
    }

    public static Sender getSender() {
        return sender;
    }


    @Override
    public void onEnable() {
        instance = this;
        loadOrReload();
    }

    @Override
    public void onDisable() {

    }

    public static void loadOrReload() {
        saveDefaultRes();
        sender = new Sender(instance);
        getInstance().reloadConfig();
        territoryManager = new TerritoryManager();
        buildingManager = new BuildingManager();
        hook();
        registerListener();
        registerCommand();
    }

    public static void hook() {
        PlaceholderAPIHook.hook();
        ProtocolLibHook.hook();
    }

    public static void registerListener() {
        instance.getServer().getPluginManager().registerEvents(new PolygonSelectionListener(), instance);
    }

    public static void registerCommand() {
        Objects.requireNonNull(instance.getCommand("Polygon")).setExecutor(new PolygonCommand());
        Objects.requireNonNull(instance.getCommand("Polygon")).setTabCompleter(new PolygonTabCompleter());
    }

    public static void saveDefaultRes() {
        instance.saveDefaultConfig();
        instance.saveResource("lang/Chinese.yml", true);
        instance.saveResource("lang/English.yml", true);
    }
}

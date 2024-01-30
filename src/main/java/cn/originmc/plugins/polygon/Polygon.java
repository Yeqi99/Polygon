package cn.originmc.plugins.polygon;


import cn.originmc.plugins.polygon.controller.command.*;

import cn.originmc.plugins.polygon.controller.listener.PolygonSelectionListener;
import cn.originmc.plugins.polygon.core.building.manager.BuildingManager;
import cn.originmc.plugins.polygon.core.building.object.Building;
import cn.originmc.plugins.polygon.core.flag.NormalFlag;
import cn.originmc.plugins.polygon.core.flag.NormalFlags;
import cn.originmc.plugins.polygon.core.player.manager.TerritoryMemberManager;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.manager.TerritoryManager;
import cn.originmc.plugins.polygon.core.region.object.Node;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.data.yaml.core.BuildingData;
import cn.originmc.plugins.polygon.data.yaml.core.TerritoryData;
import cn.originmc.plugins.polygon.utils.hook.PlaceholderAPIHook;
import cn.originmc.plugins.polygon.utils.hook.ProtocolLibHook;
import cn.originmc.plugins.polygon.utils.text.Sender;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
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
        serializationReg();
        loadOrReload();
    }

    @Override
    public void onDisable() {
        territoryManager.saveTerritoryToYaml();
        buildingManager.saveBuildingToYaml();
    }

    public static void loadOrReload() {
        saveDefaultRes();
        sender = new Sender(instance);
        getInstance().reloadConfig();

        territoryManager = new TerritoryManager();
        TerritoryData.load();
        territoryManager.loadTerritoryFromYaml();

        buildingManager = new BuildingManager();
        BuildingData.load();
        buildingManager.loadBuildingFromYaml();

        LangData.load();
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
        Objects.requireNonNull(instance.getCommand("Territory")).setExecutor(new TerritoryCommand());
        Objects.requireNonNull(instance.getCommand("Flag")).setExecutor(new FlagCommand());
        Objects.requireNonNull(instance.getCommand("Building")).setExecutor(new BuildingCommand());
        Objects.requireNonNull(instance.getCommand("Polygon")).setExecutor(new PolygonCommand());
        Objects.requireNonNull(instance.getCommand("Territory")).setTabCompleter(new TerritoryTabCompleter());
        Objects.requireNonNull(instance.getCommand("Flag")).setTabCompleter(new FlagTabCompleter());
        Objects.requireNonNull(instance.getCommand("Building")).setTabCompleter(new BuildingTabCompleter());
        Objects.requireNonNull(instance.getCommand("Polygon")).setTabCompleter(new PolygonTabCompleter());

    }

    public static void saveDefaultRes() {
        instance.saveDefaultConfig();
        instance.saveResource("lang/Chinese.yml", true);
        instance.saveResource("lang/English.yml", true);
    }

    public void serializationReg() {
        ConfigurationSerialization.registerClass(Territory.class);
        ConfigurationSerialization.registerClass(Node.class);
        ConfigurationSerialization.registerClass(NormalFlag.class);
        ConfigurationSerialization.registerClass(NormalFlags.class);
        ConfigurationSerialization.registerClass(TerritoryMember.class);
        ConfigurationSerialization.registerClass(TerritoryMemberManager.class);
        ConfigurationSerialization.registerClass(Building.class);
        ConfigurationSerialization.registerClass(Building.BlockData.class);
    }
}

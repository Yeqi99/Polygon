package cn.originmc.plugins.polygon.data.yaml.core;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.building.object.Building;
import cn.originmc.tools.minecraft.yamlcore.object.YamlManager;

import java.util.HashMap;
import java.util.Map;

public class BuildingData {
    public static YamlManager yamlManager;

    public static void load() {
        yamlManager = new YamlManager(Polygon.getInstance(), "data/building", true);
    }

    public static void save(String id, Building building) {
        if (yamlManager.hasElement(id)) {
            yamlManager.set(id, "content", building);
            yamlManager.save(id);
        } else {
            yamlManager.create(id);
            yamlManager.set(id, "content", building);
            yamlManager.save(id);
        }
    }

    public static Map<String, Building> getBuildingMap() {
        Map<String, Building> buildings = new HashMap<>();
        for (String s : yamlManager.getIdList()) {
            Building building = (Building) yamlManager.getElement(s).getYml().get("content");
            buildings.put(s, building);
        }
        return buildings;
    }
}

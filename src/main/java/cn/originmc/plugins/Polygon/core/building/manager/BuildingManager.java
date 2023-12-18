package cn.originmc.plugins.Polygon.core.building.manager;

import cn.originmc.plugins.Polygon.core.building.object.Building;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BuildingManager {
    Map<String, Building> buildingMap=new ConcurrentHashMap<>();
    public BuildingManager(){

    }

    // 添加建筑到管理器
    public void addBuilding(String name, Building building) {
        buildingMap.put(name, building);
    }

    // 通过名称获取建筑
    public Building getBuilding(String name) {
        return buildingMap.get(name);
    }

    // 检查是否存在某建筑
    public boolean containsBuilding(String name) {
        return buildingMap.containsKey(name);
    }

    // 移除建筑
    public void removeBuilding(String name) {
        buildingMap.remove(name);
    }

    // 获取所有建筑的名称
    public Set<String> getAllBuildingNames() {
        return buildingMap.keySet();
    }


}

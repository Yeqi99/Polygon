package cn.originmc.plugins.polygon.core.building.manager;

import cn.originmc.plugins.polygon.core.building.object.Building;
import cn.originmc.plugins.polygon.data.yaml.core.BuildingData;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BuildingManager {
    public Map<String, Building> buildingMap = new ConcurrentHashMap<>();

    public BuildingManager() {

    }
    public void setBuildingMap(Map<String, Building> buildingMap) {
        this.buildingMap.clear();
        this.buildingMap.putAll(buildingMap);
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
    public void loadBuildingFromYaml(){
        setBuildingMap(BuildingData.getBuildingMap());
    }
    public void saveBuildingToYaml(){
        for (Map.Entry<String, Building> entry : buildingMap.entrySet()) {
            BuildingData.save(entry.getKey(),entry.getValue());
        }
    }
    public void saveBuildingToYaml(String id){
        if (buildingMap.containsKey(id)){
            BuildingData.save(id,buildingMap.get(id));
        }
    }

}

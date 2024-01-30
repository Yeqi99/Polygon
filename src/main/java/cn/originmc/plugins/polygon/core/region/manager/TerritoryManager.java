package cn.originmc.plugins.polygon.core.region.manager;

import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.core.TerritoryData;
import org.bukkit.Location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TerritoryManager {
    public Map<String, Territory> territoryMap = new ConcurrentHashMap<>();

    public TerritoryManager() {

    }

    public boolean hasTerritory(String id) {
        return territoryMap.containsKey(id);
    }

    public void addTerritory(Territory territory) {
        territoryMap.put(territory.getId(), territory);
    }

    public Territory getTerritory(String id) {
        return territoryMap.get(id);
    }

    public void removeTerritory(String id) {
        territoryMap.remove(id);
    }

    public boolean hasTerritory(Location location) {
        for (Territory value : territoryMap.values()) {
            if (value.isInsideRegion(location) && value.isInHeight(location) && value.isInWorld(location)) {
                return true;
            }
        }
        return false;
    }

    public Territory getTerritory(Location location) {
        for (Territory value : territoryMap.values()) {
            if (value.isInsideRegion(location) && value.isInHeight(location) && value.isInWorld(location)) {
                return value;
            }
        }
        return null;
    }

    public void loadTerritoryFromYaml() {
        for (Territory territory : TerritoryData.getTerritoryList()) {
            territoryMap.put(territory.getId(), territory);
        }
    }

    public void saveTerritoryToYaml() {
        for (Territory territory : territoryMap.values()) {
            TerritoryData.save(territory);
        }
    }

    public void saveTerritoryToYaml(String id) {
        if (territoryMap.containsKey(id)) {
            TerritoryData.save(territoryMap.get(id));
        }
    }
}

package cn.originmc.plugins.Polygon.core.region.manager;

import cn.originmc.plugins.Polygon.core.region.object.Territory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TerritoryManager {
    Map<String, Territory> territoryMap=new ConcurrentHashMap<>();
    public TerritoryManager(){

    }
    public boolean hasTerritory(String id){
        return territoryMap.containsKey(id);
    }

    public void addTerritory(Territory territory){
        territoryMap.put(territory.getId(),territory);
    }

    public Territory getTerritory(String id){
        return territoryMap.get(id);
    }

    public void removeTerritory(String id){
        territoryMap.remove(id);
    }
}

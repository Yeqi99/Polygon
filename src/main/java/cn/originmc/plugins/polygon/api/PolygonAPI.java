package cn.originmc.plugins.polygon.api;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.building.manager.BuildingManager;
import cn.originmc.plugins.polygon.core.region.manager.TerritoryManager;

public class PolygonAPI {
    public TerritoryManager getTerritoryManager(){
        return Polygon.getTerritoryManager();
    }

    public BuildingManager getBuildingManager(){
        return Polygon.getBuildingManager();
    }
}

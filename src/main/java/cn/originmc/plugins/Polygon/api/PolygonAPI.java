package cn.originmc.plugins.Polygon.api;

import cn.originmc.plugins.Polygon.Polygon;
import cn.originmc.plugins.Polygon.core.building.manager.BuildingManager;
import cn.originmc.plugins.Polygon.core.region.manager.TerritoryManager;

public class PolygonAPI {
    public TerritoryManager getTerritoryManager(){
        return Polygon.getTerritoryManager();
    }

    public BuildingManager getBuildingManager(){
        return Polygon.getBuildingManager();
    }
}

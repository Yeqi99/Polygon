package cn.originmc.plugins.polygon.data.yaml.core;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.tools.minecraft.yamlcore.object.YamlManager;

import java.util.ArrayList;
import java.util.List;


public class TerritoryData {
    public static YamlManager yamlManager;

    public static void load() {
        yamlManager = new YamlManager(Polygon.getInstance(), "data/territory", true);
    }

    public static void save(Territory territory) {
        if(yamlManager.hasElement(territory.getId())){
            yamlManager.set(territory.getId(), "content",territory);
            yamlManager.save(territory.getId());
        }else {
            yamlManager.create(territory.getId());
            yamlManager.set(territory.getId(), "content",territory);
            yamlManager.save(territory.getId());
        }
    }
    public static List<Territory> getTerritoryList(){
        List<Territory> territories=new ArrayList<>();
        for (String s : yamlManager.getIdList()) {
            Territory territory= (Territory) yamlManager.getElement(s).getYml().get("content");
            territories.add(territory);
        }
        return territories;
    }



}

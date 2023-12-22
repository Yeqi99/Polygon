package cn.originmc.plugins.polygon.core.player.manager;

import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TerritoryMemberManager {
    public Map<String, TerritoryMember> territoryMap=new ConcurrentHashMap<>();
    public TerritoryMemberManager(){

    }
    public boolean hasMember(String id){
        return territoryMap.containsKey(id);
    }

    public void addMember(TerritoryMember territory){
        territoryMap.put(territory.getId(),territory);
    }

    public TerritoryMember getMember(String id){
        return territoryMap.get(id);
    }

    public void removeMember(String id){
        territoryMap.remove(id);
    }
}

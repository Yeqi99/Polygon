package cn.originmc.plugins.polygon.core.player.manager;

import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TerritoryMemberManager implements ConfigurationSerializable {
    public Map<String, TerritoryMember> territoryMap = new ConcurrentHashMap<>();

    public TerritoryMemberManager() {

    }

    public void setTerritoryMap(Map<String, TerritoryMember> territoryMap) {
        this.territoryMap.clear();
        this.territoryMap.putAll(territoryMap);
    }

    public boolean hasMember(String id) {
        return territoryMap.containsKey(id);
    }

    public void addMember(TerritoryMember territory) {
        territoryMap.put(territory.getId(), territory);
    }

    public TerritoryMember getMember(String id) {
        return territoryMap.get(id);
    }

    public void removeMember(String id) {
        territoryMap.remove(id);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("territoryMap", territoryMap);

        return result;
    }

    public static TerritoryMemberManager deserialize(Map<String, Object> map) {
        TerritoryMemberManager territoryMemberManager = new TerritoryMemberManager();
        territoryMemberManager.setTerritoryMap((Map<String, TerritoryMember>) map.get("territoryMap"));

        return territoryMemberManager;
    }
}

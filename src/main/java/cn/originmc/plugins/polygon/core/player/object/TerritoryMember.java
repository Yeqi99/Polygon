package cn.originmc.plugins.polygon.core.player.object;

import cn.originmc.plugins.polygon.core.flag.Flags;
import cn.originmc.plugins.polygon.core.flag.FlagsMaster;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TerritoryMember implements RegionMember, FlagsMaster, ConfigurationSerializable {
    private final String id;
    private Map<String, Flags> flagsMap = new ConcurrentHashMap<>();

    public TerritoryMember(String id) {
        this.id = id;
    }

    @Override
    public Boolean hasFlagsSpace(String flagsSpace) {
        return flagsMap.containsKey(flagsSpace);
    }

    @Override
    public Flags getFlags(String flagsSpace) {
        return flagsMap.get(flagsSpace);
    }

    @Override
    public void addFlags(Flags flags) {
        flagsMap.put(flags.getId(), flags);
    }

    @Override
    public void removeFlags(String flagsSpace) {
        flagsMap.remove(flagsSpace);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Player getPlayer() {
        if (Bukkit.getPlayer(UUID.fromString(this.id)) == null) {
            return null;
        }
        return Bukkit.getPlayer(UUID.fromString(this.id));
    }

    public Map<String, Flags> getFlagsMap() {
        return flagsMap;
    }

    public void setFlagsMap(Map<String, Flags> flagsMap) {
        this.flagsMap.clear();
        this.flagsMap.putAll(flagsMap);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", this.id);
        result.put("flagsMap", flagsMap);
        return result;
    }

    public static TerritoryMember deserialize(Map<String, Object> map) {
        String id = (String) map.get("id");
        TerritoryMember territoryMember = new TerritoryMember(id);
        territoryMember.setFlagsMap((Map<String, Flags>) map.get("flagsMap"));
        return territoryMember;
    }
}

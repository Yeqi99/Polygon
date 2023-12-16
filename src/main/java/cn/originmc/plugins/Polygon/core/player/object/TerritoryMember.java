package cn.originmc.plugins.Polygon.core.player.object;

import cn.originmc.plugins.Polygon.core.flag.Flags;
import cn.originmc.plugins.Polygon.core.flag.FlagsMaster;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TerritoryMember implements RegionMember, FlagsMaster {
    private final String id;
    private final Player player;
    private Map<String, Flags> flagsMap = new ConcurrentHashMap<>();

    public TerritoryMember(String id, Player player) {
        this.id = id;
        this.player = player;
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
    public void addFlags(String flagsSpace, Flags flags) {
        flagsMap.put(flagsSpace, flags);
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
        return this.player;
    }

    public Map<String, Flags> getFlagsMap() {
        return flagsMap;
    }

    public void setFlagsMap(Map<String, Flags> flagsMap) {
        this.flagsMap = flagsMap;
    }
}

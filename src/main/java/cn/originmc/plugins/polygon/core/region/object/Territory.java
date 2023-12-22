package cn.originmc.plugins.polygon.core.region.object;

import cn.originmc.plugins.polygon.core.flag.Flags;
import cn.originmc.plugins.polygon.core.flag.FlagsMaster;
import cn.originmc.plugins.polygon.core.player.manager.TerritoryMemberManager;
import cn.originmc.plugins.polygon.core.player.object.RegionMember;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Territory extends Region implements PlayerRegion, MemberRegion, FlagsMaster {
    private String id;
    private String display;
    private Map<String, Flags> flagsMap = new ConcurrentHashMap<>();
    private final TerritoryMemberManager territoryMemberManager = new TerritoryMemberManager();

    public Territory(String id, String display, String world, double maxHeight, double minHeight) {
        super(world, maxHeight, minHeight);
        this.id = id;
        this.display = display;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getDisplay() {
        return display;
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
    public void setDisplay(String display) {
        this.display = display;
    }

    public Map<String, Flags> getFlagsMap() {
        return flagsMap;
    }

    public void setFlagsMap(Map<String, Flags> flagsMap) {
        this.flagsMap = flagsMap;
    }

    @Override
    public Set<String> getMemberNames() {
        return territoryMemberManager.territoryMap.keySet();
    }

    @Override
    public void addMember(RegionMember regionMember) {
        territoryMemberManager.addMember((TerritoryMember) regionMember);
    }

    @Override
    public Boolean hasMember(String name) {
        return territoryMemberManager.hasMember(name);
    }

    @Override
    public void removeMember(String name) {
        territoryMemberManager.removeMember(name);
    }

    @Override
    public RegionMember getMember(String name) {
        return territoryMemberManager.getMember(name);
    }


    public TerritoryMemberManager getTerritoryMemberManager() {
        return territoryMemberManager;
    }
}

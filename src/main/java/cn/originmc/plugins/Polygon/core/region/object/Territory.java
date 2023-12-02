package cn.originmc.plugins.Polygon.core.region.object;

import cn.originmc.plugins.Polygon.core.player.object.RegionMember;
import cn.originmc.plugins.Polygon.core.region.object.flag.Flags;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Territory extends Region implements PlayerRegion,MemberRegion {
    private String id;
    private String display;
    private Map<String, Flags> flagsMap = new ConcurrentHashMap<>();
    private Map<String, RegionMember> regionMemberMap=new ConcurrentHashMap<>();

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
        return regionMemberMap.keySet();
    }

    @Override
    public void addMember(RegionMember regionMember) {
        regionMemberMap.put(regionMember.getId(),regionMember);
    }

    @Override
    public Boolean hasMember(String name) {
        return regionMemberMap.containsKey(name);
    }

    @Override
    public void removeMember(String name) {
        regionMemberMap.remove(name);
    }

    @Override
    public RegionMember getMember(String name) {
        return regionMemberMap.get(name);
    }

    public Map<String, RegionMember> getRegionMemberMap() {
        return regionMemberMap;
    }

    public void setRegionMemberMap(Map<String, RegionMember> regionMemberMap) {
        this.regionMemberMap = regionMemberMap;
    }
}

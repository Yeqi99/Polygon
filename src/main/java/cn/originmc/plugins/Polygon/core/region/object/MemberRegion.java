package cn.originmc.plugins.Polygon.core.region.object;

import cn.originmc.plugins.Polygon.core.player.object.RegionMember;

import java.util.Set;

public interface MemberRegion {
    Set<String> getMemberNames();
    void addMember(RegionMember regionMember);
    Boolean hasMember(String name);
    void removeMember(String name);
    RegionMember getMember(String name);
}

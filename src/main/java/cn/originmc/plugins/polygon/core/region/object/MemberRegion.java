package cn.originmc.plugins.polygon.core.region.object;

import cn.originmc.plugins.polygon.core.player.object.RegionMember;

import java.util.Set;

public interface MemberRegion {
    Set<String> getMemberNames();
    void addMember(RegionMember regionMember);
    Boolean hasMember(String name);
    void removeMember(String name);
    RegionMember getMember(String name);
}

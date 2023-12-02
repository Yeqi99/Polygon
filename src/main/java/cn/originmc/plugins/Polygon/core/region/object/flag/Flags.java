package cn.originmc.plugins.Polygon.core.region.object.flag;

import java.util.Collection;
import java.util.Set;

public interface Flags {
    String getId();
    Collection<Flag> getFlags();
    Set<String> getFlagNames();
    Flag getFlag(String flagId);
    Boolean hasFlag(String flagId);
    void addFlag(Flag flag);
    void removeFlag(String flagId);
}

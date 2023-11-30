package cn.originmc.plugins.Polygon.core.region.object.flag;

import java.util.List;

public interface Flags {
    String getId();
    List<Flag> getFlags();
    void setFlags(List<Flag> flags);
    Flag getFlag(String flagId);
    Boolean hasFlag(String flagId);
    void addFlag(Flag flag);
    void removeFlag(String flagId);
}

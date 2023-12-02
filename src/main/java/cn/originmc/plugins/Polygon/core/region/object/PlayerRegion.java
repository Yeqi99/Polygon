package cn.originmc.plugins.Polygon.core.region.object;

import cn.originmc.plugins.Polygon.core.region.object.flag.Flags;

public interface PlayerRegion {
    String getId();

    void setId(String id);

    String getDisplay();

    Boolean hasFlagsSpace(String flagsSpace);

    Flags getFlags(String flagsSpace);

    void addFlags(String flagsSpace, Flags flags);

    void removeFlags(String flagsSpace);
}

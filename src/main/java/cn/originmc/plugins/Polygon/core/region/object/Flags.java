package cn.originmc.plugins.Polygon.core.region.object;

import java.util.List;

public interface Flags {
    List<String> getFlagNames();
    Boolean hasFlag(String flagName);
    void addFlag(String flagName, String value);
    void removeFlag(String flagName);
    String getFlagValue(String flagName);
}

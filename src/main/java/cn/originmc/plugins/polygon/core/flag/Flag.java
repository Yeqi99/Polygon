package cn.originmc.plugins.polygon.core.flag;

import java.util.List;

public interface Flag {
    String getId();
    String getValue(String key);
    void addValue(String key,String value);
    void removeValue(String key);
    Boolean hasValue(String key);
    List<String> getValueKeys();
}

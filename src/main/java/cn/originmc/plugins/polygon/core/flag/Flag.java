package cn.originmc.plugins.polygon.core.flag;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface Flag {
    String getId();
    String getValue(String key);
    void addValue(String key,String value);
    void removeValue(String key);
    Boolean hasValue(String key);
    List<String> getValueKeys();
    @NotNull Map<String, Object> serialize();
}

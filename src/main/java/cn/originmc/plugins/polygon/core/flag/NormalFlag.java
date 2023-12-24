package cn.originmc.plugins.polygon.core.flag;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NormalFlag implements Flag, ConfigurationSerializable {
    private String id;
    private Map<String, String> values = new ConcurrentHashMap<>();

    public NormalFlag(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getValue(String key) {
        return values.get(key);
    }

    @Override
    public void addValue(String key, String value) {
        values.put(key, value);
    }

    @Override
    public void removeValue(String key) {
        values.remove(key);
    }

    @Override
    public Boolean hasValue(String key) {
        return values.containsKey(key);
    }

    @Override
    public List<String> getValueKeys() {
        return (List<String>) values.values();
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values.clear();
        this.values.putAll(values);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", this.id);
        result.put("values", values);

        return result;
    }

    public static NormalFlag deserialize(Map<String, Object> map) {
        NormalFlag normalFlag = new NormalFlag((String) map.get("id"));
        normalFlag.setValues((Map<String, String>) map.get("values"));
        return normalFlag;
    }
}

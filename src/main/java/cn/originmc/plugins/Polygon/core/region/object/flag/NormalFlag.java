package cn.originmc.plugins.Polygon.core.region.object.flag;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NormalFlag implements Flag{
    private String id;
    private Map<String,String> values=new ConcurrentHashMap<>();

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
        values.put(key,value);
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
        this.values = values;
    }
}

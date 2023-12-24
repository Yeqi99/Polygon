package cn.originmc.plugins.polygon.core.flag;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NormalFlags implements Flags, ConfigurationSerializable {
    private final Map<String, Flag> flags = new ConcurrentHashMap<>();
    private String id;

    public NormalFlags(String id) {
        this.id = id;
    }

    public void setFlags(Map<String, Flag> flags) {
        this.flags.clear();
        this.flags.putAll(flags);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Collection<Flag> getFlags() {
        return this.flags.values();
    }

    @Override
    public Set<String> getFlagNames() {
        return this.flags.keySet();
    }

    @Override
    public Flag getFlag(String flagId) {
        return flags.get(flagId);
    }

    @Override
    public Boolean hasFlag(String flagId) {
        return flags.containsKey(flagId);
    }

    @Override
    public void addFlag(Flag flag) {
        flags.put(flag.getId(), flag);
    }

    @Override
    public void removeFlag(String flagId) {
        flags.remove(flagId);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", this.id);
        result.put("flags", flags);

        return result;
    }

    public static NormalFlags deserialize(Map<String, Object> map) {
        NormalFlags normalFlags = new NormalFlags((String) map.get("id"));
        normalFlags.setFlags((Map<String, Flag>) map.get("flags"));

        return normalFlags;
    }
}

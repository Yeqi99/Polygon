package cn.originmc.plugins.polygon.core.flag;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NormalFlags implements Flags{
    private final Map<String,Flag> flags=new ConcurrentHashMap<>();
    private String id;

    public NormalFlags(String id) {
        this.id = id;
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
        flags.put(flag.getId(),flag);
    }

    @Override
    public void removeFlag(String flagId) {
        flags.remove(flagId);
    }

    public void setId(String id) {
        this.id = id;
    }
}

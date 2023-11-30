package cn.originmc.plugins.Polygon.core.region.object.flag;

import java.util.ArrayList;
import java.util.List;

public class NormalFlags implements Flags{
    private List<Flag> flags=new ArrayList<>();
    private String id;

    public NormalFlags(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public List<Flag> getFlags() {
        return flags;
    }

    @Override
    public void setFlags(List<Flag> flags) {
        this.flags = flags;
    }

    @Override
    public Flag getFlag(String flagId) {
        for (Flag flag : flags) {
            if (flag.getId().equals(flagId)) {
                return flag;
            }
        }
        return null;
    }

    @Override
    public Boolean hasFlag(String flagId) {
        return null;
    }

    @Override
    public void addFlag(Flag flag) {

    }

    @Override
    public void removeFlag(String flagId) {

    }

    public void setId(String id) {
        this.id = id;
    }
}

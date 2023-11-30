package cn.originmc.plugins.Polygon.core.region.object;

import cn.originmc.plugins.Polygon.core.region.object.flag.Flags;

public class Territory extends Region{
    private String id;
    private String display;
    private Flags flags;

    public Territory(String id,String display,String world,double maxHeight,double minHeight) {
        super(world,maxHeight,minHeight);
        this.id=id;
        this.display=display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }
}

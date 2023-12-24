package cn.originmc.plugins.polygon.core.flag;

public interface FlagsMaster {

    public Boolean hasFlagsSpace(String flagsSpace);


    public Flags getFlags(String flagsSpace);


    public void addFlags( Flags flags);

    public void removeFlags(String flagsSpace);
}

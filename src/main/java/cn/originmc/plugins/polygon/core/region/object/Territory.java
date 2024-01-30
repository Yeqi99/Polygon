package cn.originmc.plugins.polygon.core.region.object;

import cn.originmc.plugins.polygon.core.flag.Flags;
import cn.originmc.plugins.polygon.core.flag.FlagsMaster;
import cn.originmc.plugins.polygon.core.player.manager.TerritoryMemberManager;
import cn.originmc.plugins.polygon.core.player.object.RegionMember;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Territory extends Region implements PlayerRegion, MemberRegion, FlagsMaster, ConfigurationSerializable {
    private String id;
    private String display;
    private Location spawn;
    private Map<String, Flags> flagsMap = new ConcurrentHashMap<>();
    private TerritoryMemberManager territoryMemberManager = new TerritoryMemberManager();

    public Territory(String id, String display, String world, double maxHeight, double minHeight) {
        super(world, maxHeight, minHeight);
        this.id = id;
        this.display = display;
    }

    public void setTerritoryMemberManager(TerritoryMemberManager territoryMemberManager) {
        this.territoryMemberManager = territoryMemberManager;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getDisplay() {
        return display;
    }


    @Override
    public Boolean hasFlagsSpace(String flagsSpace) {
        return flagsMap.containsKey(flagsSpace);
    }

    @Override
    public Flags getFlags(String flagsSpace) {
        return flagsMap.get(flagsSpace);
    }

    @Override
    public void addFlags(Flags flags) {
        flagsMap.put(flags.getId(), flags);
    }

    @Override
    public void removeFlags(String flagsSpace) {
        flagsMap.remove(flagsSpace);
    }

    @Override
    public void setDisplay(String display) {
        this.display = display;
    }

    public Map<String, Flags> getFlagsMap() {
        return flagsMap;
    }

    public void setFlagsMap(Map<String, Flags> flagsMap) {
        this.flagsMap.clear();
        this.flagsMap.putAll(flagsMap);
    }

    @Override
    public Set<String> getMemberNames() {
        return territoryMemberManager.territoryMap.keySet();
    }

    @Override
    public void addMember(RegionMember regionMember) {
        territoryMemberManager.addMember((TerritoryMember) regionMember);
    }

    @Override
    public Boolean hasMember(String name) {
        return territoryMemberManager.hasMember(name);
    }

    @Override
    public void removeMember(String name) {
        territoryMemberManager.removeMember(name);
    }

    @Override
    public RegionMember getMember(String name) {
        return territoryMemberManager.getMember(name);
    }


    public TerritoryMemberManager getTerritoryMemberManager() {
        return territoryMemberManager;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", this.id);
        result.put("display", this.display);
        result.put("world", this.getWorld());
        result.put("maxHeight", this.getMaxHeight());
        result.put("minHeight", this.getMinHeight());
        result.put("nodes", getNodes());
        result.put("flagsMap", this.flagsMap);
        result.put("territoryMemberManager", territoryMemberManager);
        result.put("spawn", getSpawn());
        return result;
    }

    public static Territory deserialize(Map<String, Object> map) {
        String id = map.get("id").toString();
        String display = map.get("display").toString();
        String world = map.get("world").toString();
        double maxHeight = (double) map.get("maxHeight");
        double minHeight = (double) map.get("minHeight");
        Territory territory = new Territory(id, display, world, maxHeight, minHeight);
        territory.setTerritoryMemberManager((TerritoryMemberManager) map.get("territoryMemberManager"));
        territory.addNodes((List<Node>) map.get("nodes"));
        territory.setFlagsMap((Map<String, Flags>) map.get("flagsMap"));
        territory.setSpawn((Location) map.get("spawn"));
        return territory;
    }

    public Location getSpawn() {
        if (spawn==null){
            return calculateCenter().getBukkitLocation(Bukkit.getWorld(getWorld()),getMaxHeight());
        }
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}

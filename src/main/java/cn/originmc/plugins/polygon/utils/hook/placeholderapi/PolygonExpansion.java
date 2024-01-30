package cn.originmc.plugins.polygon.utils.hook.placeholderapi;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.utils.text.Color;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PolygonExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "Polygon";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Yeqi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        String returnStr = "";
        if (identifier.equalsIgnoreCase("display")) {
            Territory territory = Polygon.getTerritoryManager().getTerritory(player.getLocation());
            if (territory == null) {
                returnStr = Polygon.getInstance().getConfig().getString("default-territory-display");
                return Color.toColor(returnStr);
            }
            returnStr = territory.getDisplay();
            return Color.toColor(returnStr);
        } else if (identifier.equalsIgnoreCase("id")) {
            Territory territory = Polygon.getTerritoryManager().getTerritory(player.getLocation());
            if (territory == null) {
                returnStr = Polygon.getInstance().getConfig().getString("default-territory-display");
                return Color.toColor(returnStr);
            }
            returnStr = territory.getId();
            return Color.toColor(returnStr);
        } else if (identifier.equalsIgnoreCase("world")) {
            World world = player.getWorld();
            String worldName = world.getName();
            returnStr = Polygon.getInstance().getConfig().getString("world-mapping." + worldName, worldName);
            return Color.toColor(returnStr);
        } else {
            return returnStr;
        }
    }
}

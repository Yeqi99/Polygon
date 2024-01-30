package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.region.manager.TerritoryManager;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TerritoryTabCompleter implements TabCompleter {

    private final TerritoryManager territoryManager = Polygon.getTerritoryManager();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("create");
            suggestions.add("remove");
            suggestions.add("show");
            suggestions.add("tp");
            suggestions.add("setspawn");
            suggestions.add("tobuilding");
            suggestions.add("me");
            suggestions.add("info");
        } else if (args.length == 2 && "remove".equalsIgnoreCase(args[0])) {
            suggestions.addAll(getExistingTerritoryIds());
        } else if (args.length == 2 && "show".equalsIgnoreCase(args[0])) {
            suggestions.addAll(getExistingTerritoryIds());
        } else if (args.length == 2 && "info".equalsIgnoreCase(args[0])) {
            suggestions.addAll(getExistingTerritoryIds());
        } else if (args.length == 2 && "create".equalsIgnoreCase(args[0])) {
            suggestions.add("id");
        } else if (args.length == 2 && "tp".equalsIgnoreCase(args[0])) {
            suggestions.addAll(getExistingTerritoryIds());
        } else if (args.length == 2 && "tobuilding".equalsIgnoreCase(args[0])) {
            suggestions.addAll(getExistingTerritoryIds());
        } else if (args.length == 3 && "create".equalsIgnoreCase(args[0])) {
            suggestions.add("[display]");
        } else if (args.length == 3 && "tobuilding".equalsIgnoreCase(args[0])) {
            suggestions.add("id");
        } else if (args.length == 3 && "tp".equalsIgnoreCase(args[0])) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                suggestions.add(onlinePlayer.getName());
            }
        } else if (args.length == 4 && "create".equalsIgnoreCase(args[0])) {
            suggestions.add("[maxHeight]");
        } else if (args.length == 5 && "create".equalsIgnoreCase(args[0])) {
            suggestions.add("[minHeight]");
        }

        return suggestions;
    }

    private List<String> getExistingTerritoryIds() {
        List<String> territory = new ArrayList<>();
        for (Territory value : territoryManager.territoryMap.values()) {
            territory.add(value.getId());
        }
        return territory;
    }

}
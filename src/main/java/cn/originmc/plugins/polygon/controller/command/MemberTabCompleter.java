package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.region.manager.TerritoryManager;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MemberTabCompleter implements TabCompleter {
    private final TerritoryManager territoryManager = Polygon.getTerritoryManager();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("add");
            suggestions.add("remove");
        } else if (args.length == 2 && "add".equalsIgnoreCase(args[0])) {
            suggestions.addAll(getExistingTerritoryIds());
        } else if (args.length == 2 && "remove".equalsIgnoreCase(args[0])) {
            suggestions.addAll(getExistingTerritoryIds());
        } else if (args.length == 3 && "add".equalsIgnoreCase(args[0])) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                suggestions.add(onlinePlayer.getName());
            }
        } else if (args.length == 3 && "remove".equalsIgnoreCase(args[0])) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                suggestions.add(onlinePlayer.getName());
            }
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

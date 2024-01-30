package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BuildingTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("place");
        }else if (args.length == 2 && "place".equalsIgnoreCase(args[0])) {
            suggestions.addAll(Polygon.getBuildingManager().getAllBuildingNames());
        }
        return suggestions;
    }
}

package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PolygonTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "help", "create", "remove", "flag", "info", "member", "tp");
        }

        // 对于特定子命令的进一步自动补全
        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "create": {
                    return Collections.singletonList("<id> [displayName] [maxHeight] [minHeight]");
                }

                case "remove": {
                    return new ArrayList<>(Polygon.getTerritoryManager().territoryMap.keySet());
                }

                case "info": {
                    return new ArrayList<>(Polygon.getTerritoryManager().territoryMap.keySet());
                }

                case "tp": {
                    return new ArrayList<>(Polygon.getTerritoryManager().territoryMap.keySet());
                }
                case "member": {
                    List<String> list = new ArrayList<>();
                    list.add("add <ID> <name>");
                    list.add("remove <ID> <name>");
                    return list;
                }
                case "flag": {
                    List<String> list = new ArrayList<>();
                    list.add("add territory <id> <group> <flag> <key> <value>");
                    list.add("add member <name> <id>  <flag> <key> <value>");
                    list.add("remove territory <id> <group> <flag> [key]");
                    list.add("remove member <name> <id> <flag> [key]");
                    return list;
                }
            }
        }
        return new ArrayList<>();
    }
}

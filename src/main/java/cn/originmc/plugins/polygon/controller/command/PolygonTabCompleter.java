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

public class PolygonTabCompleter  implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "help", "create", "info", "building", "place");
        }

        // 对于特定子命令的进一步自动补全
        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "create":
                    // 为 "create" 提供领地名字的建议，这里需要根据实际情况填充或获取数据
                    return Collections.singletonList("<领地名字>");
                case "building":
                    // 为 "building" 提供建筑名字的建议，这里需要根据实际情况填充或获取数据
                    return new ArrayList<>(Polygon.getTerritoryManager().territoryMap.keySet());
                case "place":
                    // 为 "place" 提供建筑名字的建议，这里需要根据实际情况填充或获取数据
                    return new ArrayList<>(Polygon.getBuildingManager().buildingMap.keySet());
                // 可以根据需要为其他子命令添加更多的自动补全
            }
        }
        if (args.length == 3) {
            switch (args[0].toLowerCase()) {
                case "create":
                    // 为 "create" 提供领地名字的建议，这里需要根据实际情况填充或获取数据
                    return Collections.singletonList("<领地显示名>");
                case "building":
                    // 为 "building" 提供建筑名字的建议，这里需要根据实际情况填充或获取数据
                    return Collections.singletonList("<建筑名字>");
            }
        }

        // 默认情况下返回空列表，表示没有可用的自动补全
        return new ArrayList<>();
    }
}

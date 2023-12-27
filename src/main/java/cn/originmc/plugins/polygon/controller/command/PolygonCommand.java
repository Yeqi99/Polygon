package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.command.command.impl.*;
import cn.originmc.plugins.polygon.controller.listener.PolygonSelectionListener;
import cn.originmc.plugins.polygon.core.building.object.Building;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Node;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.region.NodeUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PolygonCommand implements CommandExecutor {
    public PolygonCommand() {
        registerCommand(new HelpCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new CreateCommand());
        registerCommand(new RemoveCommand());
        registerCommand(new MemberAddCommand());
    }

    List<SubCommand> commands = new ArrayList<>();

    public void registerCommand(SubCommand subCommand) {
        commands.add(subCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        for (SubCommand subCommand : commands) {
            int len = subCommand.getPrefix().size() + subCommand.getSize();
            if (len > args.length) {
                continue;
            }
            boolean flag = true;
            for (int i = 0; i < subCommand.getPrefix().size(); i++) {
                String prefix = subCommand.getPrefix().get(i);
                if (!args[i].equalsIgnoreCase(prefix)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (!commandSender.hasPermission(subCommand.getPermission()) && !commandSender.isOp()) {
                    Polygon.getSender().sendToSender(commandSender, LangData.getPrefix() + LangData.getServerText("no-permission", "你没有权限"));
                    return true;
                }
                if (subCommand.needPlayer()) {
                    if (commandSender instanceof Player) {
                        subCommand.execute(args, commandSender);
                    } else {
                        Polygon.getSender().sendToSender(commandSender, LangData.getPrefix() + LangData.getServerText("no-player", "你必须是一个玩家"));
                    }
                } else {
                    subCommand.execute(args, commandSender);
                }
                return true;
            }
        }
        return true;


        if (args.length == 0) {
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            Polygon.loadOrReload();
            Polygon.getSender().sendToSender(commandSender, LangData.getPrefix() + LangData.getServerText("reload", "重载成功"));
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon reload 重载插件");
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon create <领地名字> <领地显示名字> 创建领地");
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon info 查看领地信息");
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon building <建筑名字> <领地名字> 保存建筑");
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon place <建筑名字> 放置建筑");
            return true;
        } else if (args[0].equalsIgnoreCase("create")) {
            if (args.length <= 2) {
                Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon create <领地名字>");
                return true;
            }
            if (Polygon.getTerritoryManager().getTerritory(args[1]) != null) {
                Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e领地已存在");
                return true;
            }
            Player player = (Player) commandSender;
            String id = args[1];
            String displayName = args[2];
            String worldName = player.getWorld().getName();
            List<Node> nodes = PolygonSelectionListener.selectionMap.get(player);
            Territory territory = new Territory(id, displayName, worldName, NodeUtil.getMaxHeight(nodes), NodeUtil.getMinHeight(nodes));
            territory.addMember(new TerritoryMember(player.getName()));

            territory.addNodes(PolygonSelectionListener.selectionMap.get(player));
            Polygon.getTerritoryManager().addTerritory(territory);
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e领地创建成功");
            Polygon.getTerritoryManager().saveTerritoryToYaml();
            return true;
        } else if (args[0].equalsIgnoreCase("info")) {
            Polygon.getSender().sendToSender(commandSender, "调试信息");
            Polygon.getSender().sendToSender(commandSender, "领地数量:" + Polygon.getTerritoryManager().territoryMap.size());
            for (Map.Entry<String, Territory> stringTerritoryEntry : Polygon.getTerritoryManager().territoryMap.entrySet()) {
                Polygon.getSender().sendToSender(commandSender, "领地名字:" + stringTerritoryEntry.getValue().getDisplay());
                Polygon.getSender().sendToSender(commandSender, "领地ID:" + stringTerritoryEntry.getValue().getId());
                Polygon.getSender().sendToSender(commandSender, "领地世界:" + stringTerritoryEntry.getValue().getWorld());
                Polygon.getSender().sendToSender(commandSender, "领地最大高度:" + stringTerritoryEntry.getValue().getMaxHeight());
                Polygon.getSender().sendToSender(commandSender, "领地最小高度:" + stringTerritoryEntry.getValue().getMinHeight());
                Polygon.getSender().sendToSender(commandSender, "领地成员数量:" + stringTerritoryEntry.getValue().getTerritoryMemberManager().territoryMap.size());
                for (Map.Entry<String, TerritoryMember> stringTerritoryMemberEntry : stringTerritoryEntry.getValue().getTerritoryMemberManager().territoryMap.entrySet()) {
                    Polygon.getSender().sendToSender(commandSender, "领地成员名字:" + stringTerritoryMemberEntry.getValue().getPlayer().getName());
                    Polygon.getSender().sendToSender(commandSender, "领地成员ID:" + stringTerritoryMemberEntry.getValue().getId());
                }
                Polygon.getSender().sendToSender(commandSender, "领地节点数量:" + stringTerritoryEntry.getValue().getNodes().size());
                for (int i = 0; i < stringTerritoryEntry.getValue().getNodes().size(); i++) {
                    Polygon.getSender().sendToSender(commandSender, "领地节点" + i + "X:" + stringTerritoryEntry.getValue().getNodes().get(i).getX());
                    Polygon.getSender().sendToSender(commandSender, "领地节点" + i + "Z:" + stringTerritoryEntry.getValue().getNodes().get(i).getZ());
                }
            }
        } else if (args[0].equalsIgnoreCase("building")) {
            Territory territory = Polygon.getTerritoryManager().getTerritory(args[1]);

            Polygon.getBuildingManager().addBuilding(args[2], territory.getBuilding());
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e建筑保存成功");
            Polygon.getBuildingManager().saveTerritoryToYaml();
        } else if (args[0].equalsIgnoreCase("place")) {
            Player player = (Player) commandSender;
            Building building = Polygon.getBuildingManager().getBuilding(args[1]);
            if (building != null) {
                building.restoreBuildingAsync(player.getLocation(), Polygon.getInstance().getConfig().getInt("place-delay", 5));
                Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e建筑放置成功");
            } else {
                Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e没有这个建筑");
            }
        }

        return true;
    }
}

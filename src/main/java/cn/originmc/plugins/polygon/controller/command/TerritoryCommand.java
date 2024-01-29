package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.controller.listener.PolygonSelectionListener;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.manager.TerritoryManager;
import cn.originmc.plugins.polygon.core.region.object.Node;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.data.yaml.core.TerritoryData;
import cn.originmc.plugins.polygon.utils.hook.ProtocolLibHook;
import cn.originmc.plugins.polygon.utils.region.NodeUtil;
import cn.originmc.plugins.polygon.utils.text.Sender;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TerritoryCommand implements CommandExecutor {
    private Sender s = new Sender(Polygon.getInstance());
    private TerritoryManager territoryManager = Polygon.getTerritoryManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            s.sendToSender(sender, LangData.getList("help"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                return handleCreate(sender, args);
            case "remove":
                return handleRemove(sender, args);
            case "show":
                return handleShow(sender, args);
            case "tp":
                return handleTp(sender, args);
                // TODO 待实现的指令
//            case "setspawn":
//                return handleSetspawn(sender, args);
//            case "tobuilding":
//                return handleTobuilding(sender,args);
            default:
                s.sendToSender(sender, LangData.getList("unknown-command"));
                return true;
        }
    }

    private boolean handleCreate(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            s.sendToSender(sender, "&cOnly players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            s.sendToSender(player, "&cUsage: /territory create <id> [display] [maxHeight] [minHeight]");
            return true;
        }

        String id = args[1];
        if (territoryManager.hasTerritory(id)) {
            s.sendToSender(player, LangData.getPrefix() + LangData.getServerText("territory-id-exist", "&c领地ID已经存在"));
            return true;
        }

        String display = args.length > 2 ? args[2] : id;
        List<Node> nodes = PolygonSelectionListener.selectionMap.get(player);
        if (nodes.size() < 3) {
            s.sendToSender(player, LangData.getPrefix() + LangData.getServerText("node-not-enough", "&c节点不足"));
            return true;
        }

        Territory territory = createTerritory(player, id, display, nodes, args);
        territoryManager.addTerritory(territory);
        s.sendToSender(player, LangData.getPrefix() + LangData.getServerText("territory-create-success", "&a领地创建成功").replace("~", id));
        territoryManager.saveTerritoryToYaml();
        return true;
    }

    private Territory createTerritory(Player player, String id, String display, List<Node> nodes, String[] args) {
        double max = args.length >= 4 ? Double.parseDouble(args[3]) : NodeUtil.getMaxHeight(nodes);
        double min = args.length >= 5 ? Double.parseDouble(args[4]) : NodeUtil.getMinHeight(nodes);

        Territory territory = new Territory(id, display, player.getWorld().getName(), min, max);
        territory.addNodes(nodes);

        TerritoryMember member = new TerritoryMember(player.getUniqueId().toString());
        member.addFlags(FlagsGenerator.createDefaultOwnerFlags());
        territory.addMember(member);

        territory.addFlags(FlagsGenerator.createVisitorFlags());
        territory.addFlags(FlagsGenerator.createMemberFlags());

        return territory;
    }

    private boolean handleRemove(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Territory territory = Polygon.getTerritoryManager().getTerritory(player.getLocation());
            if (territory == null) {
                s.sendToSender(player, LangData.getPrefix() + LangData
                        .getServerText("not-in-territory", "&c你不在领地内"));
                return true;
            }
            if (!territory.hasMember(player.getUniqueId().toString())) {
                s.sendToSender(player, LangData.getPrefix() + LangData
                        .getServerText("no-permission", "&c你没有权限这样做"));
                return true;
            }
            Polygon.getTerritoryManager().removeTerritory(territory.getId());
            TerritoryData.yamlManager.delElement(territory.getId());
            Polygon.getSender().sendToSender(player, LangData.getPrefix() + LangData
                    .getServerText("remove-success", "~&a删除成功").replace("~", territory.getId()));
            return true;
        } else {
            if (args.length < 2) {
                s.sendToSender(sender, "&cUsage: /territory remove <id>");
                return true;
            }

            String id = args[1];
            Territory territory = territoryManager.getTerritory(id);
            if (territory == null) {
                s.sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
                return true;
            }

            territoryManager.removeTerritory(id);
            TerritoryData.yamlManager.delElement(id);
            s.sendToSender(sender, LangData.getPrefix() + LangData.getServerText("remove-success", "&a删除成功").replace("~", id));
            return true;
        }
    }

    private boolean handleShow(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 2) {
                Territory territory = Polygon.getTerritoryManager().getTerritory(player.getLocation());
                if (territory == null) {
                    s.sendToSender(player, LangData.getPrefix() + LangData
                            .getServerText("not-in-territory", "&c你不在领地内"));
                    return true;
                }
                for (Node node : territory.getNodes()) {
                    ProtocolLibHook.sendFakeBlock(node.getBukkitLocation(player.getWorld(), player.getY()), Material.DIAMOND_BLOCK, player);
                }

            } else {
                Territory territory = Polygon.getTerritoryManager().getTerritory(args[1]);
                if (territory == null) {
                    s.sendToSender(player, LangData.getPrefix() + LangData
                            .getServerText("territory-id-not-exist", "&c领地id不存在"));
                    return true;
                }
                for (Node node : territory.getNodes()) {
                    ProtocolLibHook.sendFakeBlock(node.getBukkitLocation(player.getWorld(), player.getY()), Material.DIAMOND_BLOCK, player);
                }
            }
        }
        return true;
    }

    private boolean handleTp(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // 确保提供了足够的参数
            if (args.length < 2) {
                s.sendToSender(player, "&c用法: /territory tp <id>");
                return true;
            }

            String id = args[1];
            Territory territory = territoryManager.getTerritory(id);

            // 确保领地存在
            if (territory == null) {
                s.sendToSender(player, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
                return true;
            }
            player.teleport(territory.calculateCenter().getBukkitLocation(Bukkit.getWorld(territory.getWorld()), territory.getMaxHeight()));

            s.sendToSender(player, LangData.getPrefix() + LangData.getServerText("teleport-success", "&a传送成功"));

            return true;
        } else {
            // 确保提供了足够的参数
            if (args.length < 3) {
                s.sendToSender(sender, "&c用法: /territory tp <id> <playerName>");
                return true;
            }
            Player player = Bukkit.getPlayer(args[2]);
            if (player == null) {
                s.sendToSender(sender, LangData.getPrefix() + LangData.getServerText("player-not-exist", "&c玩家不存在"));
                return true;
            }
            String id = args[1];
            Territory territory = territoryManager.getTerritory(id);

            // 确保领地存在
            if (territory == null) {
                s.sendToSender(player, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
                return true;
            }

            player.teleport(territory.calculateCenter().getBukkitLocation(Bukkit.getWorld(territory.getWorld()), territory.getMaxHeight()));
            s.sendToSender(player, LangData.getPrefix() + LangData.getServerText("teleport-success", "&a传送成功"));

        }
        return true;
    }
}

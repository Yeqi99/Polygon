package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.controller.listener.PolygonSelectionListener;
import cn.originmc.plugins.polygon.core.flag.NormalFlags;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Node;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import cn.originmc.plugins.polygon.utils.region.NodeUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("create");
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public boolean needPlayer() {
        return true;
    }

    @Override
    public void execute(String[] args, CommandSender sender) {
        Player player = (Player) sender;
        String id = args[0];
        if (Polygon.getTerritoryManager().hasTerritory(id)) {
            Polygon.getSender().sendToSender(player, LangData.getPrefix() + LangData.getServerText("territory-id-exist", "&c领地ID已经存"));
            return;
        }
        String display = id;
        if (args.length > 1) {
            display = args[1];
        }
        String worldName = player.getWorld().getName();
        List<Node> nodes = PolygonSelectionListener.selectionMap.get(player);
        if (nodes.size() < 3) {
            Polygon.getSender().sendToSender(player, LangData.getPrefix() + LangData.getServerText("node-not-enough", "&c节点不足"));
            return;
        }
        Territory territory = null;
        if (args.length < 4) {
            territory = new Territory(id, display, worldName, NodeUtil.getMaxHeight(nodes), NodeUtil.getMinHeight(nodes));

        } else {
            // 初始化领土参数
            double max = Double.parseDouble(args[2]);
            double min = Double.parseDouble(args[3]);
            // 新建领土示例
            territory = new Territory(id, display, worldName, min, max);
        }
        // 将当前选中节点加入领土
        territory.addNodes(nodes);
        // 初始化flag模板
        // 初始化创建者flags,并将成员加入领土
        TerritoryMember territoryMember = new TerritoryMember(player.getUniqueId().toString());
        NormalFlags flags = FlagsGenerator.createDefaultOwnerFlags();
        territoryMember.addFlags(flags);
        territory.addMember(territoryMember);
        // 初始化访客和成员flags
        territory.addFlags(FlagsGenerator.createVisitorFlags());
        territory.addFlags(FlagsGenerator.createMemberFlags());

        // 将初始化好的领土加入领土管理器
        Polygon.getTerritoryManager().addTerritory(territory);
        Polygon.getSender().sendToSender(player, LangData.getPrefix() +
                LangData.getServerText("territory-create-success", "&a领地创建成功")
                        .replace("~", id));
        // 保存领土缓存到yaml
        Polygon.getTerritoryManager().saveTerritoryToYaml();
    }

    @Override
    public String getPermission() {
        return "polygon.create";
    }


}

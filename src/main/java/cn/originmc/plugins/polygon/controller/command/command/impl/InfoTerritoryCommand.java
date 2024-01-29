package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class InfoTerritoryCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("info territory");
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public boolean needPlayer() {
        return false;
    }

    @Override
    public void execute(String[] args, CommandSender sender) {
        String id = args[0];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);
        if (territory == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return;
        }
        Polygon.getSender().sendToSender(sender, LangData.getPrefix() +
                LangData.getServerText("territory-info", "&a领地信息").replace("~", territory.getId()));
        String territoryLang = LangData.getServerText("territory", "&a领土");
        String nameLang = LangData.getServerText("name", "&a名字");
        String maxLang = LangData.getServerText("max", "&a最大");
        String minLang = LangData.getServerText("min", "&a最小");
        String memberLang = LangData.getServerText("member", "&a成员");
        String nodeLang = LangData.getServerText("node", "&a节点");
        String amountLang = LangData.getServerText("amount", "&a数量");
        String heightLang = LangData.getServerText("height", "&a高度");
        String groupLang = LangData.getServerText("group", "&a组");
        Polygon.getSender().sendToSender(sender, territoryLang + nameLang + ":" + territory.getDisplay());
        Polygon.getSender().sendToSender(sender, "ID:" + territory.getId());
        Polygon.getSender().sendToSender(sender, maxLang + heightLang + ":" + territory.getMaxHeight());
        Polygon.getSender().sendToSender(sender, minLang + heightLang + ":" + territory.getMinHeight());
        Polygon.getSender().sendToSender(sender, territoryLang + memberLang + amountLang + ":" + territory.getTerritoryMemberManager().territoryMap.size());
        for (Map.Entry<String, TerritoryMember> stringTerritoryMemberEntry : territory.getTerritoryMemberManager().territoryMap.entrySet()) {
            Polygon.getSender().sendToSender(sender, territoryLang + memberLang + ":" + stringTerritoryMemberEntry.getValue().getPlayer().getName());
            Polygon.getSender().sendToSender(sender, territoryLang + groupLang + ":" + FlagsGenerator.getGroup(stringTerritoryMemberEntry.getValue()));
        }
        Polygon.getSender().sendToSender(sender, territoryLang + nodeLang + amountLang + ":" + territory.getNodes().size());
    }

    @Override
    public String getPermission() {
        return "polygon.info.territory";
    }
}

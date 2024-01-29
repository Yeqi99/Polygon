package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.data.yaml.core.TerritoryData;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("remove");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean needPlayer() {
        return false;
    }

    @Override
    public void execute(String[] args, CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Territory territory = Polygon.getTerritoryManager().getTerritory(player.getLocation());
            if (territory == null) {
                Polygon.getSender().sendToSender(player, LangData.getPrefix() + LangData
                        .getServerText("not-in-territory", "&c你不在领地内"));
                return;
            }
            Polygon.getTerritoryManager().removeTerritory(territory.getId());
            TerritoryData.yamlManager.delElement(territory.getId());
            Polygon.getSender().sendToSender(player, LangData.getPrefix() + LangData
                    .getServerText("remove-success", "~&a删除成功").replace("~", territory.getId()));
        } else {
            Territory territory = Polygon.getTerritoryManager().getTerritory(args[0]);
            if (territory == null) {
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData
                        .getServerText("territory-id-not-exist", "&c领地不存在"));
                return;
            }
            Polygon.getTerritoryManager().removeTerritory(territory.getId());
            TerritoryData.yamlManager.delElement(territory.getId());
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData
                    .getServerText("remove-success", "~&a删除成功").replace("~", territory.getId()));
        }
    }

    @Override
    public String getPermission() {
        return "polygon.remove";
    }
}

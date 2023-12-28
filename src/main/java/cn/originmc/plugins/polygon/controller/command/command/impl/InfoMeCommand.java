package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoMeCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("info me");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean needPlayer() {
        return true;
    }

    @Override
    public void execute(String[] args, CommandSender sender) {
        Player player = (Player) sender;
        for (Territory value : Polygon.getTerritoryManager().territoryMap.values()) {
            if (!value.getTerritoryMemberManager().hasMember(player.getUniqueId().toString())) {
                continue;
            }
            TerritoryMember territoryMember = value.getTerritoryMemberManager().getMember(player.getUniqueId().toString());
            String group = FlagsGenerator.getGroup(territoryMember);

            Polygon.getSender().sendToPlayer(player, "&c" + value.getId() + ":" + value.getDisplay() + "&c(" + group + ")");
        }
    }

    @Override
    public String getPermission() {
        return "polygon.info.me";
    }
}

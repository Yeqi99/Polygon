package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.core.flag.NormalFlag;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TpCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("tp");
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
        Player player= (Player) sender;
        String id = args[0];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);
        if (territory == null){
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return;
        }
        NormalFlag flag= FlagsGenerator.getMemberFlag(territory,player.getUniqueId().toString(),"tp");
        if (flag==null){
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
            return;
        }
        if (flag.getValue("enable").equalsIgnoreCase("true")){
            player.teleport(territory.calculateCenter().getBukkitLocation(player.getWorld(),territory.getMaxHeight()));
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("tp-success", "&a传送成功"));
        }else {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
        }
    }

    @Override
    public String getPermission() {
        return "polygon.tp";
    }
}

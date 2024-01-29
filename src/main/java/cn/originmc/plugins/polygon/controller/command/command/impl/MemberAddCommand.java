package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MemberAddCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("member add");
    }

    @Override
    public int getSize() {
        return 2;
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
        if (sender instanceof Player){
            // 检查指令执行者是否有权限添加成员
            Player player = (Player) sender;
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(player.getUniqueId().toString());
            if(senderMember==null){
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return;
            }
            String group = FlagsGenerator.getGroup(senderMember);
            if (!group.equalsIgnoreCase("owner") && !group.equalsIgnoreCase("admin")){
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return;
            }
        }


        String name = args[1];
        Player player = Bukkit.getOfflinePlayer(name).getPlayer();
        if (player == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("player-not-exist", "&c玩家不存在"));
            return;
        }
        TerritoryMember member = new TerritoryMember(player.getUniqueId().toString());
        member.addFlags(FlagsGenerator.createMemberFlags());
        territory.getTerritoryMemberManager().addMember(member);
        Polygon.getTerritoryManager().saveTerritoryToYaml();
    }

    @Override
    public String getPermission() {
        return "polygon.member.add";
    }
}

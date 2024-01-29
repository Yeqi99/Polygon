package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.core.flag.NormalFlag;
import cn.originmc.plugins.polygon.core.flag.NormalFlags;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FlagRemoveTerritory implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("flag remove territory");
    }

    @Override
    public int getSize() {
        return 4;
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
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            // 检查指令执行者是否有权限
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(senderPlayer.getUniqueId().toString());
            if (senderMember == null) {
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return;
            }
            String group = FlagsGenerator.getGroup(senderMember);
            if (!group.equalsIgnoreCase("owner") && !group.equalsIgnoreCase("admin")) {
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return;
            }
        }
        String group = args[1];
        NormalFlags flags = (NormalFlags) territory.getFlags(group);
        if (flags == null) {
            flags = new NormalFlags(group);
        }
        String flag = args[2];
        NormalFlag normalFlag = (NormalFlag) flags.getFlag(flag);
        if (normalFlag == null) {
            normalFlag = new NormalFlag(flag);
        }
        if (args.length > 3) {
            String key = args[3];
            normalFlag.removeValue(key);
            Polygon.getTerritoryManager().saveTerritoryToYaml();
        } else {
            flags.removeFlag(flag);
            Polygon.getTerritoryManager().saveTerritoryToYaml();
        }
    }

    @Override
    public String getPermission() {
        return "polygon.flag.remove.territory";
    }
}

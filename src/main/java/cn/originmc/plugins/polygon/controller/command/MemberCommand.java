package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.core.player.object.TerritoryMember;
import cn.originmc.plugins.polygon.core.region.object.Territory;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.text.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MemberCommand implements CommandExecutor {
    private final Sender s = new Sender(Polygon.getInstance());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 0) {
            s.sendToSender(sender, LangData.getList("help"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                return handleAdd(sender, args);
            case "remove":
                return handleRemove(sender, args);
            default:
                s.sendToSender(sender, LangData.getList("unknown-command"));
                return true;
        }
    }

    public boolean handleAdd(CommandSender sender, String[] args) {
        String id = args[1];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);
        if (territory == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return true;
        }
        if (sender instanceof Player) {
            // 检查指令执行者是否有权限添加成员
            Player player = (Player) sender;
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(player.getUniqueId().toString());
            if (senderMember == null) {
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return true;
            }
            String group = FlagsGenerator.getGroup(senderMember);
            if (!group.equalsIgnoreCase("owner") && !group.equalsIgnoreCase("admin")) {
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return true;
            }
        }


        String name = args[2];
        Player player = Bukkit.getOfflinePlayer(name).getPlayer();
        if (player == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("player-not-exist", "&c玩家不存在"));
            return true;
        }
        TerritoryMember member = new TerritoryMember(player.getUniqueId().toString());
        member.addFlags(FlagsGenerator.createMemberFlags());
        territory.getTerritoryMemberManager().addMember(member);
        Polygon.getTerritoryManager().saveTerritoryToYaml();
        return true;
    }

    public boolean handleRemove(CommandSender sender, String[] args) {
        String id = args[1];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);
        if (territory == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return true;
        }
        if (sender instanceof Player) {
            // 检查指令执行者是否有权限删除成员
            Player player = (Player) sender;
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(player.getUniqueId().toString());
            if (senderMember == null) {
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return true;
            }
            String group = FlagsGenerator.getGroup(senderMember);
            if (!group.equalsIgnoreCase("owner") && !group.equalsIgnoreCase("admin")) {
                Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("no-permission", "&c你没有权限"));
                return true;
            }
        }


        String name = args[2];
        Player player = Bukkit.getOfflinePlayer(name).getPlayer();
        if (player == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("player-not-exist", "&c玩家不存在"));
            return true;
        }
        territory.getTerritoryMemberManager().removeMember(player.getUniqueId().toString());
        Polygon.getTerritoryManager().saveTerritoryToYaml();
        return true;
    }
}

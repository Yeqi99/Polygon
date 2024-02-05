package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.flag.FlagsGenerator;
import cn.originmc.plugins.polygon.core.flag.NormalFlag;
import cn.originmc.plugins.polygon.core.flag.NormalFlags;
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

// TODO 待实现
public class FlagCommand implements CommandExecutor {
    Sender s = new Sender(Polygon.getInstance());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 0) {
            s.sendToSender(sender, LangData.getList("help"));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "addmember":
                return handleAddMember(sender, args);
            case "addterritory":
                return handleAddTerritory(sender, args);
            case "removemember":
                return handleRemoveMember(sender, args);
            case "removeterritory":
                return handleRemoveTerritory(sender, args);
            default:
                s.sendToSender(sender, LangData.getList("unknown-command"));
                return true;
        }
    }

    public boolean handleAddMember(CommandSender sender, String[] args) {
        String name = args[1];
        Player player = Bukkit.getOfflinePlayer(name).getPlayer();
        if (player == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("player-not-exist", "&c玩家不存在"));
            return true;
        }
        String id = args[2];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);
        if (territory == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return true;
        }
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            // 检查指令执行者是否有权限
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(senderPlayer.getUniqueId().toString());
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
        if (!territory.getTerritoryMemberManager().hasMember(player.getUniqueId().toString())) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("not-member", "&c该玩家不是领地成员"));
            return true;
        }
        TerritoryMember targetMember = territory.getTerritoryMemberManager().getMember(player.getUniqueId().toString());
        NormalFlags flags = (NormalFlags) targetMember.getFlags("default");
        if (flags == null) {
            flags = new NormalFlags("default");
        }
        String flag = args[3];
        NormalFlag normalFlag = (NormalFlag) flags.getFlag(flag);
        if (normalFlag == null) {
            normalFlag = new NormalFlag(flag);
        }
        String key = args[4];
        String value = args[5];
        normalFlag.addValue(key, value);
        Polygon.getTerritoryManager().saveTerritoryToYaml();
        return true;
    }

    public boolean handleRemoveMember(CommandSender sender, String[] args) {
        String name = args[1];
        Player player = Bukkit.getOfflinePlayer(name).getPlayer();
        if (player == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("player-not-exist", "&c玩家不存在"));
            return true;
        }
        String id = args[2];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);
        if (territory == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return true;
        }
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            // 检查指令执行者是否有权限
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(senderPlayer.getUniqueId().toString());
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
        if (!territory.getTerritoryMemberManager().hasMember(player.getUniqueId().toString())) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("not-member", "&c该玩家不是领地成员"));
            return true;
        }
        TerritoryMember targetMember = territory.getTerritoryMemberManager().getMember(player.getUniqueId().toString());
        NormalFlags flags = (NormalFlags) targetMember.getFlags("default");
        if (flags == null) {
            flags = new NormalFlags("default");
        }
        String flag = args[3];
        NormalFlag normalFlag = (NormalFlag) flags.getFlag(flag);
        if (normalFlag == null) {
            normalFlag = new NormalFlag(flag);
        }
        if (args.length > 4) {
            String key = args[4];
            normalFlag.removeValue(key);
            Polygon.getTerritoryManager().saveTerritoryToYaml();
        } else {
            flags.removeFlag(flag);
            Polygon.getTerritoryManager().saveTerritoryToYaml();
        }
        return true;
    }

    public boolean handleAddTerritory(CommandSender sender, String[] args) {
        String id = args[1];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);

        if (territory == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return true;
        }
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            // 检查指令执行者是否有权限
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(senderPlayer.getUniqueId().toString());
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
        String group = args[2];
        NormalFlags flags = (NormalFlags) territory.getFlags(group);
        if (flags == null) {
            flags = new NormalFlags(group);
        }
        String flag = args[3];
        NormalFlag normalFlag = (NormalFlag) flags.getFlag(flag);
        if (normalFlag == null) {
            normalFlag = new NormalFlag(flag);
        }
        String key = args[4];
        String value = args[5];
        normalFlag.addValue(key, value);
        Polygon.getTerritoryManager().saveTerritoryToYaml();
        return true;
    }

    public boolean handleRemoveTerritory(CommandSender sender, String[] args) {
        String id = args[1];
        Territory territory = Polygon.getTerritoryManager().getTerritory(id);

        if (territory == null) {
            Polygon.getSender().sendToSender(sender, LangData.getPrefix() + LangData.getServerText("territory-id-not-exist", "&c领地不存在"));
            return true;
        }
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            // 检查指令执行者是否有权限
            TerritoryMember senderMember = territory.getTerritoryMemberManager().getMember(senderPlayer.getUniqueId().toString());
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
        String group = args[2];
        NormalFlags flags = (NormalFlags) territory.getFlags(group);
        if (flags == null) {
            flags = new NormalFlags(group);
        }
        String flag = args[3];
        NormalFlag normalFlag = (NormalFlag) flags.getFlag(flag);
        if (normalFlag == null) {
            normalFlag = new NormalFlag(flag);
        }
        if (args.length > 4) {
            String key = args[4];
            normalFlag.removeValue(key);
            Polygon.getTerritoryManager().saveTerritoryToYaml();
        } else {
            flags.removeFlag(flag);
            Polygon.getTerritoryManager().saveTerritoryToYaml();
        }
        return true;
    }
}

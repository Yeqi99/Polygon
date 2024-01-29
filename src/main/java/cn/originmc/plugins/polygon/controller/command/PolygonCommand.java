package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
<<<<<<< HEAD
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.text.Sender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class PolygonCommand implements CommandExecutor {
    Sender sender = new Sender(Polygon.getInstance());

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        if (args.length == 0) {
            sender.sendToSender(commandSender, LangData.getList("help"));
            return true;
=======
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.controller.command.command.impl.*;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PolygonCommand implements CommandExecutor {
    public PolygonCommand() {
        registerCommand(new HelpCommand());
        registerCommand(new ReloadCommand());
        registerCommand(new CreateCommand());
        registerCommand(new RemoveCommand());
        registerCommand(new MemberAddCommand());
        registerCommand(new MemberRemoveCommand());
        registerCommand(new FlagAddTerritory());
        registerCommand(new FlagRemoveTerritory());
        registerCommand(new FlagAddMember());
        registerCommand(new FlagRemoveMember());
        registerCommand(new InfoMeCommand());
        registerCommand(new InfoTerritoryCommand());
        registerCommand(new TpCommand());
    }

    List<SubCommand> commands = new ArrayList<>();

    public void registerCommand(SubCommand subCommand) {
        commands.add(subCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        for (SubCommand subCommand : commands) {
            int len = subCommand.getPrefix().size() + subCommand.getSize();
            if (len > args.length) {
                continue;
            }
            boolean flag = true;
            for (int i = 0; i < subCommand.getPrefix().size(); i++) {
                String prefix = subCommand.getPrefix().get(i);
                if (!args[i].equalsIgnoreCase(prefix)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (!commandSender.hasPermission(subCommand.getPermission()) && !commandSender.isOp()) {
                    Polygon.getSender().sendToSender(commandSender, LangData.getPrefix() + LangData.getServerText("no-permission", "你没有权限"));
                    return true;
                }
                if (subCommand.needPlayer()) {
                    if (commandSender instanceof Player) {
                        subCommand.execute(args, commandSender);
                    } else {
                        Polygon.getSender().sendToSender(commandSender, LangData.getPrefix() + LangData.getServerText("no-player", "你必须是一个玩家"));
                    }
                } else {
                    subCommand.execute(args, commandSender);
                }
                return true;
            }
>>>>>>> 78d9eeb3b7ac1f2891a66ceb64e6fa124bf2db25
        }
        return true;
    }
}

package cn.originmc.plugins.polygon.controller.command.command;


import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    List<String> getPrefix();

    int getSize();

    boolean needPlayer();

    void execute(String[] args, CommandSender sender);

    String getPermission();
}

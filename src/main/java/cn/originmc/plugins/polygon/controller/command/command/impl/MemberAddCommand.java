package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MemberAddCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("member add");
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

    }

    @Override
    public String getPermission() {
        return null;
    }
}

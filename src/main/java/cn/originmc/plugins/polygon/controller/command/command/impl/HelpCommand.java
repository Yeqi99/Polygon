package cn.originmc.plugins.polygon.controller.command.command.impl;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.controller.command.command.SubCommand;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.BasicUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand implements SubCommand {
    @Override
    public List<String> getPrefix() {
        return BasicUtil.getPrefixList("help");
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
        Polygon.getSender().sendToSender(sender, LangData.getList("help"));
    }

    @Override
    public String getPermission() {
        return "polygon.help";
    }


}

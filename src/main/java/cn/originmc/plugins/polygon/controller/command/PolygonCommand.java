package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
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
        }
        return true;
    }
}

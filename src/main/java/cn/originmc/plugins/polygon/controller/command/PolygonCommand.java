package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.text.Sender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PolygonCommand implements CommandExecutor {
    private final Sender s = new Sender(Polygon.getInstance());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 0) {
            s.sendToSender(sender, LangData.getList("help"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                return handleHelp(sender, args);
            case "reload":
                return handleReload(sender, args);
            default:
                s.sendToSender(sender, LangData.getList("unknown-command"));
                return true;
        }
    }

    public boolean handleHelp(CommandSender sender, String[] args) {
        Polygon.getSender().sendToSender(sender, LangData.getList("help"));
        return true;
    }

    public boolean handleReload(CommandSender sender, String[] args) {
        Polygon.loadOrReload();
        Polygon.getSender().sendToSender(sender, LangData.getServerText("reload", "&a重载成功"));
        return true;
    }
}

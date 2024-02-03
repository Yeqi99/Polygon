package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.text.Sender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        return true;
    }

    public boolean handleAddMember(CommandSender sender, String[] args){

    }
}

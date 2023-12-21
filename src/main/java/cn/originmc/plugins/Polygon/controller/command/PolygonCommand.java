package cn.originmc.plugins.Polygon.controller.command;

import cn.originmc.plugins.Polygon.Polygon;
import cn.originmc.plugins.Polygon.core.region.manager.TerritoryManager;
import cn.originmc.plugins.Polygon.core.region.object.Territory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PolygonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            Polygon.loadOrReload();
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e重载成功");
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon reload 重载插件");
            return true;
        } else if (args[0].equalsIgnoreCase("create")) {
            if (args.length <= 2) {
                Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e/polygon create <领地名字>");
                return true;
            }
            if (Polygon.getTerritoryManager().getTerritory(args[1]) != null) {
                Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e领地已存在");
                return true;
            }
            Player player = (Player) commandSender;
            //TODO
            Territory territory=new Territory(args[1],player,);
            Polygon.getSender().sendToSender(commandSender, "§a[§bPolygon§a] §e领地创建成功");
            return true;
        }

        return true;
    }
}

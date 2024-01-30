package cn.originmc.plugins.polygon.controller.command;

import cn.originmc.plugins.polygon.Polygon;
import cn.originmc.plugins.polygon.core.building.object.Building;
import cn.originmc.plugins.polygon.data.yaml.config.LangData;
import cn.originmc.plugins.polygon.utils.text.Sender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildingCommand implements CommandExecutor {
    private final Sender s = new Sender(Polygon.getInstance());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 0) {
            s.sendToSender(sender, LangData.getList("help"));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "place":
                return handlePlace(sender, args);
            default:
                s.sendToSender(sender, LangData.getList("unknown-command"));
                return true;
        }
    }

    public boolean handlePlace(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String buildingId = args[1];
            Building building = Polygon.getBuildingManager().getBuilding(buildingId);
            if (building == null) {
                return true;
            }
            building.restoreBuilding(player.getLocation());
        }
        return true;
    }
}

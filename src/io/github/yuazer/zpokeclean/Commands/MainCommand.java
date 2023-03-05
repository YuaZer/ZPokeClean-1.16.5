package io.github.yuazer.zpokeclean.Commands;

import io.github.yuazer.zaxlib.Utils.YamlUtils;
import io.github.yuazer.zpokeclean.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("zpokeclean")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§b/zpokeclean §a简写-> §b/zpc");
                sender.sendMessage("§b/zpokeclean reload §a重载配置文件");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
                Main.getInstance().reloadConfig();
                sender.sendMessage(YamlUtils.getConfigMessage("Message.reload", Main.pluginName));
                return true;
            }
        }
        return false;
    }
}

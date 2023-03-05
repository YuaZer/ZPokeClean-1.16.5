package io.github.yuazer.zpokeclean;

import io.github.yuazer.zaxlib.Utils.NMSUtils;
import io.github.yuazer.zaxlib.Utils.PluginWelCome;
import io.github.yuazer.zaxlib.Utils.YamlUtils;
import io.github.yuazer.zpokeclean.CleanMode.TimeModule;
import io.github.yuazer.zpokeclean.Commands.MainCommand;
import io.github.yuazer.zpokeclean.GlobalTime.TimeRunning;
import net.minecraft.world.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main extends JavaPlugin {
    public static final String pluginName = "ZPokeClean";
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static List<TimeModule> modules = new ArrayList<>();
    private Set<String> TimeMsg = getConfig().getConfigurationSection("ClearMessage").getKeys(false);

    public Set<String> getTimeMsg() {
        return TimeMsg;
    }

    public void setTimeMsg(Set<String> timeMsg) {
        TimeMsg = timeMsg;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginWelCome.logLoaded(this);
        PluginWelCome.checkDepend(this, getDescription().getDepend());
        saveDefaultConfig();
        TimeRunning timeRunning = new TimeRunning();
        timeRunning.runTaskTimerAsynchronously(this, 0L, 20L);
        worldCleanTask();
        Bukkit.getPluginCommand("zpokeclean").setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        PluginWelCome.logDisable(this);
        TimeMsg.clear();
        modules.clear();
    }

    public void worldCleanTask() {
        for (String world : YamlUtils.getConfigStringList("EnableWorld", Main.pluginName)) {
            World w = NMSUtils.bkToNmsWorld(Bukkit.getServer().getWorld(world));
            if (w!=null){
                TimeModule timeModule = new TimeModule(w);
                timeModule.runTaskTimerAsynchronously(this, 0L, YamlUtils.getConfigInt("GlobalSetting.TimeSetting", Main.pluginName) * 60L * 20);
                try {
                    modules.add(timeModule);
                } catch (NullPointerException e) {
                    System.out.println("§c配置文件存在服务器没有的世界!:"+w.getWorld().getName()+" 请检查配置文件的配置!");
                }
            }
        }
    }
}

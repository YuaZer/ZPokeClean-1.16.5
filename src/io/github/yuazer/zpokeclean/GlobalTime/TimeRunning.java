package io.github.yuazer.zpokeclean.GlobalTime;

import io.github.yuazer.zaxlib.Utils.YamlUtils;
import io.github.yuazer.zpokeclean.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeRunning extends BukkitRunnable {
    private static int GlobalTime = 0;

    public static void setGlobalTime(int globalTime) {
        GlobalTime = globalTime;
    }

    public static int getGlobalTime() {
        return GlobalTime;
    }

    @Override
    public void run() {
        GlobalTime++;
//        for (String t: Main.getInstance().getConfig().getConfigurationSection("ClearMessage").getKeys(false)){
//            int i = Integer.parseInt(t);
//            //判断倒计时
//            if (getGlobalTime()==(YamlUtils.getConfigInt("GlobalSetting.TimeSetting",Main.pluginName)*60-i)){
//                Bukkit.getServer().broadcastMessage(YamlUtils.getConfigMessage("Message.ClearMessage."+t,Main.pluginName));
//            }
//        }
        String t = String.valueOf(YamlUtils.getConfigInt("GlobalSetting.TimeSetting", Main.pluginName) * 60 - getGlobalTime());
        if (Main.getInstance().getTimeMsg().contains(t)) {
            Bukkit.getServer().broadcastMessage(YamlUtils.getConfigMessage("ClearMessage." + t, Main.pluginName));
        }
    }
}

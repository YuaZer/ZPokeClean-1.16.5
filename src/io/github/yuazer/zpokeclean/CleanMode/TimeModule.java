package io.github.yuazer.zpokeclean.CleanMode;


import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import io.github.yuazer.zaxlib.Utils.YamlUtils;
import io.github.yuazer.zpokeclean.GlobalTime.TimeRunning;
import io.github.yuazer.zpokeclean.Main;
import net.minecraft.entity.Entity;
import org.bukkit.Bukkit;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;

public class TimeModule extends BukkitRunnable {
    private net.minecraft.world.World world;

    public TimeModule(net.minecraft.world.World world) {
        this.world = world;
    }

    @Override
    public void run() {
        int count = 0;
        for (Entity entity : world.getMinecraftWorld().getEntities().collect(Collectors.toSet())) {
            if (entity instanceof PixelmonEntity) {
                PixelmonEntity entityPixelmon = ((PixelmonEntity) entity);
                if (entityPixelmon.hasOwner()||entityPixelmon.battleController!=null){
                    continue;
                }
                if (YamlUtils.getConfigStringList("SafePokemon",Main.pluginName).contains(entityPixelmon.getPokemonName())){
                    continue;
                }
                if (!YamlUtils.getConfigBoolean("GlobalSetting.shiny", Main.pluginName) && entityPixelmon.getPokemon().isShiny()) {
                    continue;
                }
                if (!YamlUtils.getConfigBoolean("GlobalSetting.lengendary", Main.pluginName) && entityPixelmon.isLegendary()) {
                    continue;
                }
                if (!YamlUtils.getConfigBoolean("GlobalSetting.ultrabeast", Main.pluginName) && entityPixelmon.getPokemon().isUltraBeast()) {
                    continue;
                }
                if (!YamlUtils.getConfigBoolean("GlobalSetting.boss", Main.pluginName) && entityPixelmon.isBossPokemon()) {
                    continue;
                }
                if (!YamlUtils.getConfigBoolean("GlobalSetting.unNormal", Main.pluginName) && entityPixelmon.canDespawn) {
                    continue;
                }
                entityPixelmon.unloadEntity();
                count++;
            }
        }
        TimeRunning.setGlobalTime(0);
        Bukkit.getServer().broadcastMessage(YamlUtils.getConfigMessage("Message.cleanMessage",Main.pluginName)
                .replace("%total%",String.valueOf(count))
                .replace("%world%", world.getWorld().getName()));
    }
}

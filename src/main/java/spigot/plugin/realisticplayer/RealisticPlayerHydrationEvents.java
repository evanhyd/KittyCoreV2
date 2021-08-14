package spigot.plugin.realisticplayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import spigot.plugin.KittyCore;

import java.util.HashMap;
import java.util.UUID;


public class RealisticPlayerHydrationEvents implements Listener
{
    public static void updatePlayerHydrationThread()
    {
        new BukkitRunnable()
        {
            final HashMap<UUID, Vector> posMap = new HashMap<>();

            @Override
            public void run()
            {
                for(Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());

                    Vector playerPos = posMap.get(player.getUniqueId());

                    if(playerPos != null && !player.getLocation().toVector().equals(playerPos))
                    {
                        //1000 seconds of walking
                        double hydrationChange = -0.001;

                        if(player.isSwimming() || player.isSprinting()) hydrationChange *= 2.0;
                        if(player.isFlying() || player.isInsideVehicle() || player.isRiptiding()) hydrationChange = 0.0;
                        if(player.getPlayerWeather() == WeatherType.DOWNFALL) hydrationChange = 0.001;

                        realisticPlayer.setHydration(realisticPlayer.getHydration() + hydrationChange);
                    }

                    posMap.put(player.getUniqueId(), player.getLocation().toVector());
                }
            }

        }.runTaskTimer(KittyCore.getPlugin(KittyCore.class), 0, 20);
    }

    @EventHandler
    public void onPlayerConsumeFood(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();
        RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());

        Material material = event.getItem().getType();
        realisticPlayer.setHydration(realisticPlayer.getHydration() + RealisticPlayerHydrationConstant.get(material));
    }

    @EventHandler
    public void onPlayerDrowning(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof Player)
        {
            Player player = (Player) entity;
            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());
            if(event.getCause() == EntityDamageEvent.DamageCause.DROWNING)
            {
                realisticPlayer.setHydration(realisticPlayer.getHydration() + 0.15);
            }
        }
    }

    @EventHandler
    public void onPlayerBurning(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof Player)
        {
            Player player = (Player) entity;
            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());

            switch(event.getCause())
            {
                case FIRE:
                case FIRE_TICK:
                case LAVA:
                case HOT_FLOOR:
                    realisticPlayer.setHydration(realisticPlayer.getHydration() - 0.05);
                    break;
            }
        }
    }
}

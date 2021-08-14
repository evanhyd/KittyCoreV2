package spigot.plugin.realisticplayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import spigot.plugin.KittyCore;
import java.util.Random;

public class RealisticPlayerSanityEvents implements Listener
{
    public static void updatePlayerSanityThread()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for(Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());
                    int lightLevel = player.getEyeLocation().getBlock().getLightLevel();

                    double newSanity = realisticPlayer.getSanity();
                    newSanity += Math.min(0.01, 0.01 * (lightLevel - 8) * (1.0 + ((20.0 - player.getHealth()) / 20.0)));

                    realisticPlayer.setSanity(newSanity);
                }
            }

        }.runTaskTimer(KittyCore.getPlugin(KittyCore.class), 0, 20);
    }

    @EventHandler
    public void onPlayerWakeUp(PlayerBedLeaveEvent event)
    {
        Player player = event.getPlayer();
        RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());
        if(player.getWorld().getTime() >= 1000) realisticPlayer.setSanity(1.0);
    }

    @EventHandler
    public void onPlayerKillPlayer(EntityDamageByEntityEvent event)
    {
        Entity killer = event.getDamager();
        Entity victim = event.getEntity();

        if(killer instanceof Player && victim instanceof Player && victim.isDead())
        {
            Player player = (Player) killer;
            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());
            realisticPlayer.setSanity(0.0);
        }
    }

    @EventHandler
    public void onPlayerDamageZombie(EntityDamageByEntityEvent event)
    {
        Entity killer = event.getDamager();
        Entity victim = event.getEntity();

        if(killer instanceof Player && victim instanceof Zombie)
        {
            Player player = (Player) killer;

            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());

            if(realisticPlayer.getSanity() < 0.05)
            {
                for(Entity entity : player.getNearbyEntities(60.0, 15.0, 60.0))
                {
                    if(entity instanceof Zombie)
                    {
                        Zombie zombie = (Zombie) entity;
                        zombie.setTarget(player);

                        AttributeInstance reinforcement = zombie.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS);
                        if(reinforcement != null) reinforcement.setBaseValue(1.0);
                    }
                }
            }
        }
    }

    public static void onPlayerHallucinationThread()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for(Player player : Bukkit.getServer().getOnlinePlayers())
                {
                    RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());
                    if(realisticPlayer == null) continue;

                    Random random = new Random();
                    double soundHallucinationChance = Math.sqrt(realisticPlayer.getSanity() / 10.0) + 0.77;

                    if(random.nextDouble() > soundHallucinationChance)
                    {
                        Sound sound = Sound.values()[random.nextInt(Sound.values().length)];
                        float volume = random.nextFloat() + 0.5f;
                        float pitch = random.nextFloat() + 1.0f;

                        player.playSound(player.getEyeLocation(), sound, volume, pitch);
                    }

                    double visualHallucinationChance = Math.sqrt(realisticPlayer.getSanity() / 10.0) + 0.84;
                    if(random.nextDouble() > visualHallucinationChance)
                    {
                        boolean hasOtherPlayerNearby = false;
                        for(Entity entity : player.getNearbyEntities(40.0, 8.0, 40.0))
                        {
                            if(entity instanceof Player && entity.getUniqueId() != player.getUniqueId())
                            {
                                hasOtherPlayerNearby = true;
                                break;
                            }
                        }

                        if(!hasOtherPlayerNearby)
                        {
                            EntityType entityType = EntityType.values()[random.nextInt(EntityType.values().length)];

                            Location entityLocation = player.getEyeLocation();
                            entityLocation.setX(entityLocation.getX() + random.nextInt(20) - 11);
                            entityLocation.setY(entityLocation.getY() + random.nextInt(4) - 2);
                            entityLocation.setZ(entityLocation.getZ() + random.nextInt(20) - 10);

                            Entity entity = player.getWorld().spawnEntity(entityLocation, entityType);
                            entity.setInvulnerable(true);
                            entity.setGravity(false);

                            new BukkitRunnable()
                            {
                                @Override
                                public void run()
                                {
                                    entity.remove();
                                }
                            }.runTaskLater(KittyCore.getPlugin(KittyCore.class), 4);
                        }
                    }

                }
            }
        }.runTaskTimer(KittyCore.getPlugin(KittyCore.class), 0, 200);
    }
}

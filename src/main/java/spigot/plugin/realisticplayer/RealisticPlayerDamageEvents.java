package spigot.plugin.realisticplayer;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RealisticPlayerDamageEvents implements Listener
{
    @EventHandler
    public void onPlayerDamagedByEnvironment(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof Player)
        {
            Player player = (Player)entity;
            if(player.isDead()) return;
            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());

            switch (event.getCause())
            {
                case DROWNING:
                    player.spawnParticle(Particle.BUBBLE_COLUMN_UP, player.getEyeLocation(), 5);
                case STARVATION:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0, false,  false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0, false,  false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0, false,  false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 3, false,  false, false));
                    break;

                case SUFFOCATION:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false,  false, false));
                    break;

                case LIGHTNING:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,  1200, 0, false,  false, false));
                    break;

                case FALLING_BLOCK:
                case FLY_INTO_WALL:
                    realisticPlayer.setHeadHealth(realisticPlayer.getHeadHealth() - event.getFinalDamage() / 20.0);
                    event.setDamage(event.getDamage() * 2.0);
                    break;

                case FALL:
                    realisticPlayer.setLimbHealth(realisticPlayer.getLimbHealth() - event.getFinalDamage() / 20.0);
                    break;

                default:
                    break;
            }
        }
    }


    @EventHandler
    public void onPlayerDamagedByLivingEntity(EntityDamageByEntityEvent event)
    {
        if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player)
        {
            LivingEntity damager = (LivingEntity) event.getDamager();
            Player player = (Player) event.getEntity();
            if(player.isDead()) return;
            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());

            if(event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.ENTITY_SWEEP_ATTACK)
            {
                Location locationDiff = player.getLocation().subtract(damager.getLocation());

                //horizontal distance
                double diffXZ = Math.hypot(locationDiff.getX(), locationDiff.getZ());

                //calc the vertical diff, tan(a) * diffXZ = diffY
                double diffY = Math.tan(Math.toRadians(damager.getEyeLocation().getPitch())) * diffXZ;

                //calc the hitting Y position
                double hitY = damager.getEyeLocation().getY() - diffY;

                double unitHeight = (player.getEyeLocation().getY() - player.getLocation().getY()) / 5.3;
                double headHeight = player.getLocation().getY() + unitHeight * 4.5;
                double bodyHeight = player.getLocation().getY() + unitHeight * 2.5;


                double damage = event.getFinalDamage();
                if(hitY >= headHeight)
                {
                    realisticPlayer.setHeadHealth(realisticPlayer.getHeadHealth() - damage / 20.0);
                    damage *= 1.15;
                }
                else if(hitY >= bodyHeight)
                {
                    realisticPlayer.setBodyHealth(realisticPlayer.getBodyHealth() - damage / 20.0);
                    //damage *= 1.0;
                }
                else
                {
                    realisticPlayer.setLimbHealth(realisticPlayer.getLimbHealth() - damage / 20.0);
                    damage *= 0.85;
                }

                event.setDamage(damage);
            }
        }
    }

    @EventHandler
    public void onPlayerRegenerateHealth(EntityRegainHealthEvent event)
    {
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());

            //multiply 1.00001 to fix the float rounding error
            double regainedHealth = 1.00001 * event.getAmount() / 20.0;
            realisticPlayer.setHeadHealth(realisticPlayer.getHeadHealth() + regainedHealth);
            realisticPlayer.setBodyHealth(realisticPlayer.getBodyHealth() + regainedHealth);
            realisticPlayer.setLimbHealth(realisticPlayer.getLimbHealth() + regainedHealth);
        }
    }
}

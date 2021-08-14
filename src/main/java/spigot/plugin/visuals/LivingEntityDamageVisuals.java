package spigot.plugin.visuals;

import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class LivingEntityDamageVisuals implements Listener
{
    @EventHandler
    public void onLivingEntityBleeding(EntityDamageEvent event)
    {
        if(event.getEntity() instanceof LivingEntity)
        {
            LivingEntity victim = (LivingEntity) event.getEntity();
            int bloodNum = (int)Math.pow(event.getFinalDamage(), 2) / 5;

            switch(event.getCause())
            {
                case ENTITY_ATTACK:
                case ENTITY_SWEEP_ATTACK:
                    victim.getWorld().spawnParticle(Particle.CRIMSON_SPORE ,victim.getEyeLocation(), bloodNum);
                    break;

                case FALL:
                    victim.getWorld().spawnParticle(Particle.CRIMSON_SPORE ,victim.getLocation(), bloodNum);
                    break;
            }
        }
    }

    @EventHandler
    public void onLivingEntityDrowning(EntityDamageEvent event)
    {
        if(event.getEntity() instanceof LivingEntity && event.getCause() == EntityDamageEvent.DamageCause.DROWNING)
        {
            LivingEntity victim = (LivingEntity) event.getEntity();
            victim.getWorld().spawnParticle(Particle.BUBBLE_COLUMN_UP ,victim.getEyeLocation(), 15);
        }
    }
}

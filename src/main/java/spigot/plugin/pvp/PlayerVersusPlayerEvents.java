package spigot.plugin.pvp;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.UUID;

public class PlayerVersusPlayerEvents implements Listener
{
    private static final HashMap<UUID, Boolean> playerPVPStatusHashmap = new HashMap<>();

    public static void setPlayerPVPStatus(UUID uuid, boolean pvpStatus)
    {
        playerPVPStatusHashmap.put(uuid, pvpStatus);
    }

    public static boolean getPlayerPVPStatus(UUID uuid)
    {
        return playerPVPStatusHashmap.get(uuid);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        playerPVPStatusHashmap.putIfAbsent(event.getPlayer().getUniqueId(), false);
    }

    @EventHandler
    public void onPlayerDamagedByPlayer(EntityDamageByEntityEvent event)
    {
        Entity victim = event.getEntity();
        Entity damager = event.getDamager();

        if(victim instanceof Player)
        {
            if(damager instanceof Player)
            {
                if(!isGoodDamage(victim.getUniqueId(), damager.getUniqueId())) event.setCancelled(true);
            }
            else if(damager instanceof Projectile)
            {
                ProjectileSource shooter = ((Projectile) damager).getShooter();
                if(shooter instanceof Player)
                {
                    if(!isGoodDamage(victim.getUniqueId(), ((Player) shooter).getUniqueId())) event.setCancelled(true);
                }
            }
        }
    }

    private boolean isGoodDamage(UUID uuid1, UUID uuid2)
    {
        return playerPVPStatusHashmap.get(uuid1) && playerPVPStatusHashmap.get(uuid2);
    }
}

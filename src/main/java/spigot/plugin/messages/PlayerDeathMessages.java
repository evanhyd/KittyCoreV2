package spigot.plugin.messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathMessages implements Listener
{
    @EventHandler
    public void sendPlayerDeathCoordinates(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        World world = player.getWorld();
        Location location = player.getLocation();
        player.sendMessage(ChatColor.RED + "Death Coordinates:\n" +
                (int) location.getX() + " " +
                (int) location.getY() + " " +
                (int) location.getZ() +
                " in " + world.getName());
    }

    @EventHandler
    public void sendPlayerDeathMessages(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
        if(entityDamageEvent == null) return;

        switch(entityDamageEvent.getCause())
        {
            case BLOCK_EXPLOSION:
            case ENTITY_EXPLOSION:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " got obliterated by the explosion");
                break;
            }

            case FALL:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " broke his ankle");
                break;
            }

            case FALLING_BLOCK:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " suffered serious brain concussion");
                break;
            }

            case FIRE:
            case FIRE_TICK:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " was burning alive into ashe");
                break;}

            case FLY_INTO_WALL:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " was driving too fast without having a valid licence");
                break;
            }

            case LAVA:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " forgot to add Lava Block to his X-ray");
                break;
            }
            case PROJECTILE:
            {
                LivingEntity killer = player.getKiller();
                if (killer != null)
                    event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " got penetrated by " + killer.getName());
                break;
            }
            case STARVATION:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " starved to death!");
                break;
            }
            case SUICIDE:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + ", please call https://www.crisisservicescanada.ca/en for help");
                break;
            }
            case THORNS:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " tried to punch a hedgehog");
                break;
            }
            case VOID:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " plunged into void for absolutely no reason");
                break;
            }
            case FREEZE:
            {
                event.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getName() + " has transcended into an ice-cream");
                break;
            }

            case ENTITY_SWEEP_ATTACK:
            {
                LivingEntity killer = player.getKiller();
                if(killer != null) event.setDeathMessage(ChatColor.LIGHT_PURPLE + killer.getName() + " sliced " + player.getName() + " into meat pieces");
                break;
            }

            default:
                break;
        }
    }
}

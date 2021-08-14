package spigot.plugin.messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class PlayerJoinMessages implements Listener
{
    @EventHandler
    public void onPlayerJoinMessage(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if(player.hasPlayedBefore())
        {
            event.setJoinMessage(ChatColor.GREEN + player.getName() + " hopped on the server :)");
        }
        else
        {
            for(Player audience : getOnlinePlayers())
            {
                audience.sendTitle(ChatColor.GOLD + "Welcome", ChatColor.GOLD + player.getName(), 20, 100, 20);
            }
        }
    }
}

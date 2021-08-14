package spigot.plugin.messages;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitMessages implements Listener
{
    @EventHandler
    public void onPlayerQuitMessage(PlayerQuitEvent event)
    {
        event.setQuitMessage(ChatColor.GOLD + event.getPlayer().getName() + " rage quit!");
    }
}

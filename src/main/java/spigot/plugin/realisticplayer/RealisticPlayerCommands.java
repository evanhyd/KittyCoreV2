package spigot.plugin.realisticplayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RealisticPlayerCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            RealisticPlayer realisticPlayer = RealisticPlayerManager.getRealisticPlayer(player.getUniqueId());
            player.sendMessage("Hydration: "+ realisticPlayer.getHydration() + "\n");
            player.sendMessage("Sanity: "+ realisticPlayer.getSanity() + "\n");
            player.sendMessage("Head: "+ realisticPlayer.getHeadHealth() + "\n");
            player.sendMessage("Body: "+ realisticPlayer.getBodyHealth() + "\n");
            player.sendMessage("Limb: "+ realisticPlayer.getLimbHealth() + "\n");

            return false;
        }

        return true;
    }
}

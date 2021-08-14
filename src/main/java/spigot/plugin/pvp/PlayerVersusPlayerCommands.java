package spigot.plugin.pvp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerVersusPlayerCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if(args.length == 1)
            {
                if(args[0].equalsIgnoreCase("true"))
                {
                    PlayerVersusPlayerEvents.setPlayerPVPStatus(uuid, true);
                    player.sendMessage(ChatColor.RED + "PVP is now ON");
                    return false;
                }
                if(args[0].equalsIgnoreCase("false"))
                {
                    PlayerVersusPlayerEvents.setPlayerPVPStatus(uuid, false);
                    player.sendMessage(ChatColor.GREEN + "PVP is now off");
                    return false;
                }
                return true;
            }
            return true;
        }
        return true;
    }
}

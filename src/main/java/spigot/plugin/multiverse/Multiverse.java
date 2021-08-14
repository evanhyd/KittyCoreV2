package spigot.plugin.multiverse;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Multiverse implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player && args.length == 1)
        {
            Player player = (Player)sender;
            if(!player.isOp()) return true;

            World world = Bukkit.getServer().createWorld(new WorldCreator(args[0]));
            if(world == null)
            {
                player.sendMessage(args[0] +  " is loading right now, please run this command again!");
                return false;
            }

            Block block = world.getHighestBlockAt(0 ,0);
            player.teleport(block.getLocation());

            return false;
        }

        return true;
    }
}
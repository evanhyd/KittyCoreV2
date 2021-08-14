package spigot.plugin.realisticplayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RealisticPlayerManager implements Listener
{
    private static final HashMap<UUID, RealisticPlayer> realisticPlayerHashMap = new HashMap<>();;

    public static RealisticPlayer getRealisticPlayer(UUID uuid)
    {
        return RealisticPlayerManager.realisticPlayerHashMap.get(uuid);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        RealisticPlayer realisticPlayer = realisticPlayerHashMap.get(player.getUniqueId());
        if(realisticPlayer == null) realisticPlayerHashMap.put(player.getUniqueId(), new RealisticPlayer(player));
        else realisticPlayer.refreshBar();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        RealisticPlayer realisticPlayer = realisticPlayerHashMap.get(player.getUniqueId());
        realisticPlayer.reset();
    }

    public static void loadRealisticPlayerDataFile()
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(REALISTIC_PLAYER_DATA_FILE));

            while(true)
            {
                String line = bufferedReader.readLine();
                if(line == null) break;

                String[] args = line.split(" ");

                UUID newUUID = UUID.fromString(args[0]);
                RealisticPlayer realisticPlayer = new RealisticPlayer(
                        newUUID,
                        Double.parseDouble(args[1]),
                        Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]),
                        Double.parseDouble(args[5]));

                realisticPlayerHashMap.put(newUUID, realisticPlayer);
            }

        }catch(IOException iox)
        {
            System.out.println("There was a problem while loading the realistic player data file :(");
        }
    }

    public static void saveRealisticPlayerDataFile()
    {
        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(REALISTIC_PLAYER_DATA_FILE));

            for(Map.Entry<UUID, RealisticPlayer> pair : realisticPlayerHashMap.entrySet())
            {
                RealisticPlayer realisticPlayer = pair.getValue();
                bufferedWriter.write(
                        pair.getKey().toString() + " " +
                        String.format("%.5f", realisticPlayer.getHeadHealth()) + " " +
                        String.format("%.5f", realisticPlayer.getBodyHealth()) + " " +
                        String.format("%.5f", realisticPlayer.getLimbHealth()) + " " +
                        String.format("%.5f", realisticPlayer.getSanity()) + " " +
                        String.format("%.5f", realisticPlayer.getHydration()));

                bufferedWriter.newLine();
            }
            bufferedWriter.close();

        }catch(IOException iox)
        {
            System.out.println("There was a problem while saving the realistic player data file :(");
        }
    }

    private static final String REALISTIC_PLAYER_DATA_FILE = "realistic_player_data_file.txt";
}
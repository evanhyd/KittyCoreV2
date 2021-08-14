package spigot.plugin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import spigot.plugin.messages.*;
import spigot.plugin.multiverse.Multiverse;
import spigot.plugin.pvp.PlayerVersusPlayerCommands;
import spigot.plugin.realisticplayer.*;
import spigot.plugin.recipes.*;
import spigot.plugin.visuals.*;

public final class KittyCore extends JavaPlugin
{
    private void registerRealisticPlayer()
    {
        getServer().getPluginManager().registerEvents(new RealisticPlayerManager(), this);
        getServer().getPluginManager().registerEvents(new RealisticPlayerSanityEvents(), this);
        getServer().getPluginManager().registerEvents(new RealisticPlayerHydrationEvents(), this);
        getServer().getPluginManager().registerEvents(new RealisticPlayerDamageEvents(), this);

        RealisticPlayerHydrationEvents.updatePlayerHydrationThread();
        RealisticPlayerSanityEvents.updatePlayerSanityThread();
        RealisticPlayerSanityEvents.onPlayerHallucinationThread();

        this.getCommand("debug").setExecutor(new RealisticPlayerCommands());

        RealisticPlayerManager.loadRealisticPlayerDataFile();
    }

    private void registerMessagesEvents()
    {
        getServer().getPluginManager().registerEvents(new PlayerDeathMessages(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinMessages(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitMessages(), this);
    }

    private void addCratingRecipes()
    {
        getServer().addRecipe(CraftingRecipes.craftingSweetBerriesSugar());
    }

    private void addFurnaceRecipes()
    {
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondOreToCoal());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondToCoal());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondBlockToCoalBlock());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondHelmetToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondChestplateToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondLeggingsToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondBootsToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondSwordToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondPickaxeToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondShovelToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondHoeToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceDiamondHorseArmorToCoals());
        getServer().addRecipe(FurnaceRecipes.furnaceSweetBerriesToRedDye());
        getServer().addRecipe(FurnaceRecipes.furnaceIronBarsToIronNugget());
    }
    private void registerVisualsEvents()
    {
        getServer().getPluginManager().registerEvents(new LivingEntityDamageVisuals(), this);
    }

    @Override
    public void onEnable()
    {
        System.out.println(ChatColor.YELLOW + "Initializing KittyCore");

        registerRealisticPlayer();
        registerMessagesEvents();
        addCratingRecipes();
        addFurnaceRecipes();
        registerVisualsEvents();

        this.getCommand("multiverse").setExecutor(new Multiverse());
        this.getCommand("pvp").setExecutor(new PlayerVersusPlayerCommands());
        System.out.println(ChatColor.GREEN + "KittyCore has been loaded!");
    }

    @Override
    public void onDisable()
    {
        RealisticPlayerManager.saveRealisticPlayerDataFile();
    }
}

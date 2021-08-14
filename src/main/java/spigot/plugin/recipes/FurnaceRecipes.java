package spigot.plugin.recipes;

import spigot.plugin.KittyCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class FurnaceRecipes
{
    public static FurnaceRecipe furnaceDiamondOreToCoal()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_ore_to_coal");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL), Material.DIAMOND_ORE, 1.0f, 1600);
    }
    public static FurnaceRecipe furnaceDiamondToCoal()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_to_coal");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL), Material.DIAMOND, 1.0f, 1600);
    }

    public static FurnaceRecipe furnaceDiamondBlockToCoalBlock()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_block_to_coal_block");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL_BLOCK), Material.DIAMOND_BLOCK, 9.0f, 1600 * 9);
    }

    public static FurnaceRecipe furnaceDiamondHelmetToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_helmet_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 5), Material.DIAMOND_HELMET, 5.0f, 1600 * 5);
    }

    public static FurnaceRecipe furnaceDiamondChestplateToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_chestplate_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 8), Material.DIAMOND_CHESTPLATE, 8.0f, 1600 * 8);
    }

    public static FurnaceRecipe furnaceDiamondLeggingsToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_leggings_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 7), Material.DIAMOND_LEGGINGS, 7.0f, 1600 * 7);
    }

    public static FurnaceRecipe furnaceDiamondBootsToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_boots_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 4), Material.DIAMOND_BOOTS, 4.0f, 1600 * 4);
    }

    public static FurnaceRecipe furnaceDiamondSwordToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_sword_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 2), Material.DIAMOND_SWORD, 2.0f, 1600 * 2);
    }

    public static FurnaceRecipe furnaceDiamondPickaxeToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_pickaxe_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 3), Material.DIAMOND_PICKAXE, 3.0f, 1600 * 3);
    }

    public static FurnaceRecipe furnaceDiamondShovelToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_shovel_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 1), Material.DIAMOND_SHOVEL, 1.0f, 1600);
    }

    public static FurnaceRecipe furnaceDiamondHoeToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_hoe_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 2), Material.DIAMOND_HOE, 2.0f, 1600 * 2);
    }

    public static FurnaceRecipe furnaceDiamondHorseArmorToCoals()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_diamond_horse_armor_to_coals");
        return new FurnaceRecipe(key, new ItemStack(Material.COAL, 6), Material.DIAMOND_HORSE_ARMOR, 6.0f, 1600 * 6);
    }

    public static FurnaceRecipe furnaceSweetBerriesToRedDye()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_sweet_berries_to_red_dye");
        return new FurnaceRecipe(key, new ItemStack(Material.RED_DYE, 1), Material.SWEET_BERRIES, 1.0f, 200);
    }

    public static FurnaceRecipe furnaceIronBarsToIronNugget()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "furnace_iron_bars_to_iron_nugget");
        return new FurnaceRecipe(key, new ItemStack(Material.IRON_NUGGET, 1), Material.IRON_BARS, 1.0f, 200);
    }
}

package spigot.plugin.recipes;

import spigot.plugin.KittyCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftingRecipes
{
    public static ShapelessRecipe craftingSweetBerriesSugar()
    {
        NamespacedKey key = new NamespacedKey(KittyCore.getPlugin(KittyCore.class), "crafting_sweet_berries_sugar");
        ShapelessRecipe sugarRecipe = new ShapelessRecipe(key, new ItemStack(Material.SUGAR));
        sugarRecipe.addIngredient(4, Material.SWEET_BERRIES);

        return sugarRecipe;
    }
}

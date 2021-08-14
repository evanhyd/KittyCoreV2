package spigot.plugin.realisticplayer;

import org.bukkit.Material;

import java.util.HashMap;

public class RealisticPlayerHydrationConstant
{
    private RealisticPlayerHydrationConstant() {}

    private static final HashMap<Material, Double> hydrationValue = new HashMap<Material, Double>()
    {
        {
            put(Material.ENCHANTED_GOLDEN_APPLE, 0.08);
            put(Material.GOLDEN_APPLE, 0.06);
            put(Material.GOLDEN_CARROT, 0.04);
            put(Material.COOKED_MUTTON, -0.05);
            put(Material.COOKED_PORKCHOP, -0.05);
            put(Material.COOKED_SALMON, -0.05);
            put(Material.COOKED_BEEF, -0.05);
            put(Material.BAKED_POTATO, -0.05);
            put(Material.BEETROOT_SOUP, 0.35);
            put(Material.BEETROOT, 0.04);
            put(Material.BREAD, -0.03);
            put(Material.CARROT, 0.04);
            put(Material.COOKED_CHICKEN, -0.05);
            put(Material.COOKED_COD, -0.05);
            put(Material.COOKED_RABBIT, -0.05);
            put(Material.MUSHROOM_STEW, 0.35);
            put(Material.RABBIT_STEW, 0.35);
            put(Material.SUSPICIOUS_STEW, 0.30);
            put(Material.APPLE, 0.08);
            put(Material.CHORUS_FRUIT, 0.13);
            put(Material.DRIED_KELP, -0.07);
            put(Material.MELON_SLICE, 0.03);
            put(Material.POISONOUS_POTATO, 0.05);
            put(Material.POTATO, 0.05);
            put(Material.PUMPKIN_PIE, -0.10);
            put(Material.BEEF, 0.03);
            put(Material.CHICKEN,0.03);
            put(Material.MUTTON, 0.03);
            put(Material.PORKCHOP,-0.08);
            put(Material.RABBIT, 0.03);
            put(Material.CAKE, -0.03);
            put(Material.COOKIE, -0.01);
            put(Material.PUFFERFISH, 0.03);
            put(Material.COD, 0.04);
            put(Material.SALMON, 0.04);
            put(Material.ROTTEN_FLESH, 0.03);
            put(Material.SPIDER_EYE, 0.01);
            put(Material.SWEET_BERRIES, 0.06);
            put(Material.TROPICAL_FISH, 0.03);
            put(Material.HONEY_BOTTLE, 0.10);
            put(Material.POTION, 1.0);
            put(Material.MILK_BUCKET, 1.0);
            put(Material.GLOW_BERRIES, 0.07);
        }
    };

    public static double get(Material material)
    {
        Double hydrationGain = hydrationValue.get(material);
        if(hydrationGain == null) return 0.0;
        else return hydrationGain;
    }
}

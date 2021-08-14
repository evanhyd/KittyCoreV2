package spigot.plugin.realisticplayer;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class RealisticPlayer
{
    private final UUID uuid;
    private double headHealth;
    private double bodyHealth;
    private double limbHealth;

    private double sanity;
    private BossBar sanityBar;
    private double hydration;
    private BossBar hydrationBar;

    public RealisticPlayer(Player player)
    {
        this.uuid = player.getUniqueId();
        this.headHealth = 1.0;
        this.bodyHealth = 1.0;
        this.limbHealth = 1.0;

        this.sanityBar = Bukkit.createBossBar("Sanity", BarColor.PINK, BarStyle.SOLID);
        setSanity(1.0);
        this.hydrationBar = Bukkit.createBossBar("Hydration", BarColor.BLUE, BarStyle.SOLID);
        setHydration(1.0);
        refreshBar();
    }

    public RealisticPlayer(UUID newUUID, double newHeadHealth, double newBodyHealth, double newLimbHealth, double newSanity, double newHydration)
    {
        this.uuid = newUUID;
        this.headHealth = newHeadHealth;
        this.bodyHealth = newBodyHealth;
        this.limbHealth = newLimbHealth;

        this.sanityBar = Bukkit.createBossBar("Sanity", BarColor.PINK, BarStyle.SOLID);
        setSanity(1.0);
        this.hydrationBar = Bukkit.createBossBar("Hydration", BarColor.BLUE, BarStyle.SOLID);
        setHydration(1.0);
        refreshBar();
    }

    public void reset()
    {
        this.headHealth = 1.0;
        this.bodyHealth = 1.0;
        this.limbHealth = 1.0;

        setSanity(1.0);
        setHydration(1.0);
        refreshBar();
    }

    public void setHeadHealth(double headHealth)
    {
        this.headHealth = Math.max(0.0, Math.min(1.0, headHealth));

        Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) return;

        if (this.headHealth < 0.10) player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5 * 20, 0));
        if (this.headHealth < 0.05) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 0));
    }

    public void setBodyHealth(double bodyHealth)
    {
        this.bodyHealth = Math.max(0.0, Math.min(1.0, bodyHealth));
    }

    public void setLimbHealth(double limbHealth)
    {
        this.limbHealth = Math.max(0.0, Math.min(1.0, limbHealth));

        Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) return;


        AttributeInstance damageInstance = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (damageInstance == null || speedInstance == null) return;


        double speedScalar = 0.0, damageScalar = 0.0;
        if (this.limbHealth < 0.75) speedScalar = (1.0 - (this.limbHealth / 0.75)) * -0.45;
        if (this.limbHealth < 0.80) damageScalar = (1.0 - (this.limbHealth / 0.80)) * -0.20;

        AttributeModifier speedModifier = new AttributeModifier(LIMB_SPEED_MODIFIER_UUID, "limb speed", speedScalar, AttributeModifier.Operation.ADD_SCALAR);
        AttributeModifier damageModifier = new AttributeModifier(LIMB_DAMAGE_MODIFIER_UUID,"limb damage", damageScalar, AttributeModifier.Operation.ADD_SCALAR);

        speedInstance.removeModifier(speedModifier);
        speedInstance.addModifier(speedModifier);

        damageInstance.removeModifier(damageModifier);
        damageInstance.addModifier(damageModifier);
    }

    public void setSanity(double sanity)
    {
        this.sanity = Math.max(0.0, Math.min(1.0, sanity));
        this.sanityBar.setProgress(this.sanity);

        if(this.sanity > 0.75)
        {
            this.sanityBar.setColor(BarColor.GREEN);
            this.sanityBar.removeFlag(BarFlag.CREATE_FOG);
            this.sanityBar.removeFlag(BarFlag.DARKEN_SKY);
        }
        else if(this.sanity > 0.50)
        {
            this.sanityBar.setColor(BarColor.YELLOW);
            this.sanityBar.removeFlag(BarFlag.CREATE_FOG);
            this.sanityBar.removeFlag(BarFlag.DARKEN_SKY);
        }
        else if(this.sanity > 0.25)
        {
            this.sanityBar.setColor(BarColor.PINK);
            this.sanityBar.addFlag(BarFlag.CREATE_FOG);
            this.sanityBar.removeFlag(BarFlag.DARKEN_SKY);
        }
        else if(this.sanity > 0.05)
        {
            this.sanityBar.setColor(BarColor.RED);
            this.sanityBar.addFlag(BarFlag.CREATE_FOG);
            this.sanityBar.addFlag(BarFlag.DARKEN_SKY);
        }
        else
        {
            this.sanityBar.setColor(BarColor.PURPLE);
            this.sanityBar.addFlag(BarFlag.CREATE_FOG);
            this.sanityBar.addFlag(BarFlag.DARKEN_SKY);
        }
    }

    public void setHydration(double hydration)
    {
        this.hydration = Math.max(0.0, Math.min(1.0, hydration));
        this.hydrationBar.setProgress(this.hydration);

        if(this.hydration > 0.75)
        {
            this.hydrationBar.setColor(BarColor.BLUE);
            this.hydrationBar.removeFlag(BarFlag.CREATE_FOG);
            this.hydrationBar.removeFlag(BarFlag.DARKEN_SKY);
        }
        else if(this.hydration > 0.35)
        {
            this.hydrationBar.setColor(BarColor.YELLOW);
            this.hydrationBar.addFlag(BarFlag.CREATE_FOG);
            this.hydrationBar.removeFlag(BarFlag.DARKEN_SKY);
        }
        else
        {
            this.hydrationBar.setColor(BarColor.RED);
            this.hydrationBar.addFlag(BarFlag.CREATE_FOG);
            this.hydrationBar.addFlag(BarFlag.DARKEN_SKY);
        }
    }

    public void refreshBar()
    {
        Player player = Bukkit.getPlayer(this.uuid);
        if(player != null)
        {
            this.sanityBar.addPlayer(player);
            this.hydrationBar.addPlayer(player);
        }
    }

    public double getHeadHealth()
    {
        return this.headHealth;
    }

    public double getBodyHealth()
    {
        return this.bodyHealth;
    }

    public double getLimbHealth()
    {
        return this.limbHealth;
    }

    public double getSanity()
    {
        return sanity;
    }

    public double getHydration()
    {
        return hydration;
    }


    private static final UUID LIMB_SPEED_MODIFIER_UUID = UUID.nameUUIDFromBytes("limb_speed_modifier_uuid".getBytes());
    private static final UUID LIMB_DAMAGE_MODIFIER_UUID = UUID.nameUUIDFromBytes("limb_damage_modifier_uuid".getBytes());
}

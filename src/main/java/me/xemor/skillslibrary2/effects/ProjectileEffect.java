package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.entity.EntityData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class ProjectileEffect extends Effect implements EntityEffect, TargetEffect, LocationEffect {

    private final EntityData projectile;
    private final double velocity;

    public ProjectileEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);

        if (configurationSection.isString("entity")) {
            this.projectile = EntityData.create(EntityType.valueOf(configurationSection.getString("entity")));
        } else {
            ConfigurationSection entitySection = configurationSection.getConfigurationSection("entity");
            this.projectile = entitySection != null ? EntityData.create(entitySection, EntityType.SNOWBALL) : EntityData.create(EntityType.SNOWBALL);
        }

        velocity = configurationSection.getDouble("velocity", 1.0);
    }

    @Override
    public boolean useEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            Vector velocity = livingEntity.getEyeLocation().getDirection().multiply(this.velocity);
            Entity projectile = this.projectile.spawnEntity(entity.getWorld(), livingEntity.getEyeLocation().add(velocity));
            projectile.setVelocity(velocity);
        }
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        useEffect(entity, target.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        Vector velocity = location.subtract(entity.getLocation()).toVector().normalize().multiply(this.velocity);
        Entity projectileEntity = projectile.spawnEntity(location.getWorld(), entity.getLocation().add(velocity));
        projectileEntity.setVelocity(velocity);
        return false;
    }
}

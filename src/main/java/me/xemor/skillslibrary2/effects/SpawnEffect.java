package me.xemor.skillslibrary2.effects;

import me.xemor.configurationdata.entity.EntityData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnEffect extends Effect implements EntityEffect, TargetEffect, LocationEffect {

    private final EntityData entityData;

    public SpawnEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);

        if (configurationSection.isString("entity")) {
            this.entityData = EntityData.create(EntityType.valueOf(configurationSection.getString("entity")));
        } else {
            ConfigurationSection entitySection = configurationSection.getConfigurationSection("entity");
            this.entityData = entitySection != null ? EntityData.create(entitySection, EntityType.ZOMBIE) : EntityData.create(EntityType.ZOMBIE);
        }
    }

    @Override
    public boolean useEffect(Entity entity) {
        useEffect(entity, entity.getLocation());
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Location location) {
        entityData.spawnEntity(location.getWorld(), location);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        useEffect(entity, target.getLocation());
        return false;
    }
}

package me.xemor.skillslibrary2.effects;

import me.xemor.skillslibrary2.SkillsLibrary;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ActionBarEffect extends Effect implements EntityEffect, TargetEffect {

    private final String message;

    public ActionBarEffect(int effect, ConfigurationSection configurationSection) {
        super(effect, configurationSection);
        this.message = configurationSection.getString("message");

        try {
            MiniMessage.miniMessage().deserialize(message, Placeholder.unparsed("player", ""));
        } catch (ParsingException e) {
            SkillsLibrary.getInstance().getLogger().severe("There is likely a legacy colour code at this location " + configurationSection.getCurrentPath() + ".message");
            e.printStackTrace();
        }
    }

    @Override
    public boolean useEffect(Entity entity, Entity target) {
        sendMessage(target);
        return false;
    }

    @Override
    public boolean useEffect(Entity entity) {
        sendMessage(entity);
        return false;
    }

    public void sendMessage(Entity entity) {
        if (entity instanceof Player player) {
            Audience audience = SkillsLibrary.getBukkitAudiences().player(player);
            try {
                Component component = MiniMessage.miniMessage().deserialize(message, Placeholder.unparsed("player", player.getDisplayName()));
                audience.sendActionBar(component);
            } catch (ParsingException e) {
                SkillsLibrary.getInstance().getLogger().severe("There is likely a legacy colour code in this message " + message);
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

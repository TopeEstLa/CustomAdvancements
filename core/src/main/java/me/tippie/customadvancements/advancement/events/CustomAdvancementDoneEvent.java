package me.tippie.customadvancements.advancement.events;

import me.tippie.customadvancements.advancement.CAdvancement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomAdvancementDoneEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final CAdvancement advancement;

    public CustomAdvancementDoneEvent(Player player, CAdvancement advancement) {
        this.player = player;
        this.advancement = advancement;
    }

    public Player getPlayer() {
        return player;
    }

    public CAdvancement getAdvancement() {
        return advancement;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

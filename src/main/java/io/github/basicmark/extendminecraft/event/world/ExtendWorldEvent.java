package io.github.basicmark.extendminecraft.event.world;

import io.github.basicmark.extendminecraft.event.ExtendEvent;
import io.github.basicmark.extendminecraft.world.ExtendWorld;
import org.bukkit.event.HandlerList;

public class ExtendWorldEvent extends ExtendEvent {
    private static final HandlerList handlers = new HandlerList();
    private ExtendWorld world;

    public ExtendWorldEvent(ExtendWorld world) {
        this.world = world;
    }

    public ExtendWorld getWorld() {
        return world;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

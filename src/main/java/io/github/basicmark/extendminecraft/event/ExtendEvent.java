package io.github.basicmark.extendminecraft.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExtendEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public ExtendEvent() {
        super();
    }

    public ExtendEvent(boolean isAsync) {
        super(isAsync);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

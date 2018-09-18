package io.github.basicmark.extendminecraft.event.world;

import io.github.basicmark.extendminecraft.ExtendChunk;
import org.bukkit.event.HandlerList;

public class ExtendChunkLoadEvent extends ExtendChunkEvent {
    private static final HandlerList handlers = new HandlerList();

    public ExtendChunkLoadEvent(ExtendChunk chunk, boolean newChunk) {
        super(chunk);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

package io.github.basicmark.extendminecraft.event.world;

import io.github.basicmark.extendminecraft.ExtendChunk;
import org.bukkit.event.HandlerList;

public class ExtendChunkEvent extends ExtendWorldEvent {
    private static final HandlerList handlers = new HandlerList();
    private ExtendChunk chunk;

    public ExtendChunkEvent(ExtendChunk chunk) {
        super(chunk.getWorld());
        this.chunk = chunk;
    }

    public ExtendChunk getChunk() {
        return chunk;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

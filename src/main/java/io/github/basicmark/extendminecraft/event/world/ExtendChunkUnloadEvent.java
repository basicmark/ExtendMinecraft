package io.github.basicmark.extendminecraft.event.world;

import io.github.basicmark.extendminecraft.ExtendChunk;
import org.bukkit.event.HandlerList;

public class ExtendChunkUnloadEvent extends ExtendChunkEvent {
    private static final HandlerList handlers = new HandlerList();
    private ExtendChunk chunk;
	private boolean save;
	private boolean cancel;

    public ExtendChunkUnloadEvent(ExtendChunk chunk, boolean save) {
        super(chunk);
		this.save = save;
		this.cancel = false;
    }

	public boolean isCancelled() {
        return cancel;
    }
	
	public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

	public boolean isSaveChunk() {
        return save;
    }
	
	public void setSaveChunk(boolean save) {
        this.save = save;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

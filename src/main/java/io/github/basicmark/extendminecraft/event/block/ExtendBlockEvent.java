package io.github.basicmark.extendminecraft.event.block;

import io.github.basicmark.extendminecraft.block.ExtendBlock;
import io.github.basicmark.extendminecraft.world.ExtendWorld;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExtendBlockEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private ExtendBlock block;

    public ExtendBlockEvent(ExtendBlock block) {
        this.block = block;
    }

    public ExtendBlock getBlock() {
        return block;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

package io.github.basicmark.extendminecraft.event.block;

import io.github.basicmark.extendminecraft.block.ExtendBlock;
import org.bukkit.event.HandlerList;

public class ExtendBlockChangeEvent extends ExtendBlockEvent{
    private static final HandlerList handlers = new HandlerList();
    ExtendBlock afterBlock;

    public ExtendBlockChangeEvent(ExtendBlock beforeBlock, ExtendBlock afterBlock) {
        super(beforeBlock);
        this.afterBlock = afterBlock;
    }

    public ExtendBlock getAfterBlock() {
        return afterBlock;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

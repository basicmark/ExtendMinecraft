package io.github.basicmark.extendminecraft.event.block;

import io.github.basicmark.extendminecraft.block.ExtendBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ExtendBlockPlaceEvent extends ExtendBlockEvent{
    private static final HandlerList handlers = new HandlerList();
    ExtendBlock placedBlock;
    ExtendBlock replacedBlockState;
    ExtendBlock placedAgainst;
    ItemStack itemInHand;
    Player thePlayer;
    boolean canBuild;
    EquipmentSlot hand;

    public ExtendBlockPlaceEvent(ExtendBlock placedBlock, ExtendBlock replacedBlockState, ExtendBlock placedAgainst, ItemStack itemInHand, Player thePlayer, boolean canBuild, EquipmentSlot hand) {
        super(placedBlock);
        this.placedBlock = placedBlock;
        this.replacedBlockState = replacedBlockState;
        this.placedAgainst = placedAgainst;
        this.itemInHand = itemInHand;
        this.thePlayer = thePlayer;
        this.canBuild = canBuild;
        this.hand = hand;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

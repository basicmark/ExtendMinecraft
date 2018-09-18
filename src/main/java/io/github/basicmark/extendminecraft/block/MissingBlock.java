package io.github.basicmark.extendminecraft.block;


import org.bukkit.Material;
import org.bukkit.block.Block;

public class MissingBlock extends ExtendBlock{

    public MissingBlock(Block block, String fullName) {
        super(block, fullName);
    }

    public boolean canBreak() {
        return false;
    }
}

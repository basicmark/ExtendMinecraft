package io.github.basicmark.extendminecraft.block;

import org.bukkit.block.Block;

public class MissingBlockFactory extends ExtendBlockFactory<MissingBlock> {
    public MissingBlockFactory() {
        super("extendminecraft", "missing");
    }

    @Override
    public MissingBlock newBlock(Block block) {
        MissingBlock extBlock = new MissingBlock(block, getFullName());

        return extBlock;
    }
}

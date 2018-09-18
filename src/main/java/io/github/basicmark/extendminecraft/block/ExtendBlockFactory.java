package io.github.basicmark.extendminecraft.block;

import io.github.basicmark.extendminecraft.ExtendMinecraft;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;


public abstract class ExtendBlockFactory<TBlock extends ExtendBlock> {
    private String name;
    private String nameSpace;
    private String fullName;

    public ExtendBlockFactory(String nameSpace, String name) {
        this.name = name;
        this.nameSpace = nameSpace;
        fullName = nameSpace + ":" + name;
    }

    public abstract TBlock newBlock(Block block);

    public TBlock loadBlock(Block block, ConfigurationSection config) {
        TBlock extBlock = newBlock(block);

        extBlock.load(config);
        return extBlock;
    }

    public void saveBlock(ConfigurationSection config, TBlock block) {
        block.save(config);
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }
}

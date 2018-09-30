package io.github.basicmark.extendminecraft.block;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

public abstract class ExtendBlock {
    String fullName;
    private Block block;

    public ExtendBlock(Block block, String fullName) {
        this.block = block;
        this.fullName = fullName;
    }

    public Block getBukkitBlock() {
        return block;
    }

    public boolean canBreak() {
        return true;
    }

    public String getFullName() {
        return fullName;
    }

    public void save(ConfigurationSection config) {
    }

    public boolean load(ConfigurationSection config) {
        return true;
    }

    public void unload() {
    }

    public void postChunkLoad() {
    }
}

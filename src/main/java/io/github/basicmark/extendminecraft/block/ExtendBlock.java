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

    public void load(ConfigurationSection config) {
    }

    public void unload() {
    }

    public void postChunkLoad() {
    }
}

package io.github.basicmark.extendminecraft.block;

import io.github.basicmark.extendminecraft.ExtendMinecraft;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;


public abstract class ExtendBlock {
    private String fullName;
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

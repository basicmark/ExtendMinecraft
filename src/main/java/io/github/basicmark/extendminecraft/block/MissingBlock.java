package io.github.basicmark.extendminecraft.block;


import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

public class MissingBlock extends ExtendBlock {
    ConfigurationSection storedConfig;

    public MissingBlock(Block block, String fullName) {
        super(block, fullName);
    }

    public boolean canBreak() {
        return false;
    }

    public void setBlockType(String type) {
        this.fullName = type;
    }

    public ConfigurationSection getStoredConfig() {
        return storedConfig;
    }

    /*
     * For saving and loading just use the raw
     * configuration section, this means data
     * will not be lost if the block is not
     * unknown at load/unload time.
     */
    public void save(ConfigurationSection config) {
        for (String key : storedConfig.getKeys(true)) {
            config.set(key, storedConfig.get(key));
        }
    }

    public boolean load(ConfigurationSection config) {
        storedConfig = config;
        return true;
    }
}

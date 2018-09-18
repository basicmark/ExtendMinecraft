package io.github.basicmark.extendminecraft.block;

import io.github.basicmark.extendminecraft.ExtendMinecraft;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemorySection;

public class ExtendBlockState {
    ExtendBlock extBlockState;

    public ExtendBlockState(ExtendBlock extBlock) {
        /* Clone the block via a configuration section in memory */
        MemoryConfiguration memConfig = new MemoryConfiguration();
        ConfigurationSection config = memConfig.createSection("data");
        this.extBlockState = ExtendMinecraft.blockRegistry.getLoader(extBlock.getFullName()).loadBlock(extBlock.getBukkitBlock(), config);
    }

    public ExtendBlock getExtendBlockState() {
        return extBlockState;
    }

    public ExtendBlock getExtendBlock() {
        return ExtendMinecraft.getInstantce().getBlock(extBlockState.getBukkitBlock());
    }

    public void update() {
        ExtendMinecraft.getInstantce().setBlock(extBlockState);
    }
}

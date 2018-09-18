package io.github.basicmark.extendminecraft;

import io.github.basicmark.extendminecraft.block.ExtendBlock;
import io.github.basicmark.extendminecraft.block.ExtendBlockFactory;
import io.github.basicmark.extendminecraft.world.ExtendWorld;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ExtendChunk {
    private ExtendWorld world;
	private Chunk chunk;
	private Map<String, ExtendBlock> extBlocks = new HashMap<String, ExtendBlock>();

    public ExtendChunk(ExtendWorld world, Chunk chunk) {
        this.world = world;
        this.chunk = chunk;
    }

    public boolean isExtended() {
        return !extBlocks.isEmpty();
    }

    public Chunk getBukkitChunk() {
        return chunk;
    }

    public ExtendWorld getWorld() {
        return world;
    }

    public void setBlock(ExtendBlock block, int xOffset, int yOffset, int zOffset) {
        extBlocks.put(getKey(xOffset, yOffset, zOffset), block);
    }

    public void clearBlock(int xOffset, int yOffset, int zOffset) {
        extBlocks.remove(getKey(xOffset, yOffset, zOffset));
    }

    public ExtendBlock getBlock(int xOffset, int yOffset, int zOffset) {
        return extBlocks.get(getKey(xOffset, yOffset, zOffset));
    }

    public void load(ConfigurationSection config) {
        /* Load all the blocks in the chunk first */
        for (String key : config.getKeys(false)) {
            ConfigurationSection blockConfig = config.getConfigurationSection(key);
            String blockType = blockConfig.getString("type");
            ExtendBlockFactory factory = ExtendMinecraft.blockRegistry.getLoader(blockType);
            //ExtendBlock extBlock = factory.loadBlock(blockConfig.getConfigurationSection("data"), Material.valueOf(blockConfig.getString("material")));
            int offsets[] = getOffsets(key);
            Block block = chunk.getBlock(offsets[0], offsets[1], offsets[2]);
            ExtendBlock extBlock = factory.newBlock(block);
            extBlock.load(blockConfig.getConfigurationSection("data"));

            extBlocks.put(key, extBlock);
        }

        /* Then inform them the chunk has been loaded */
        for (ExtendBlock extBlock : extBlocks.values()) {
            extBlock.postChunkLoad();
        }
    }

    public void save(ConfigurationSection config) {
        for (String extBlockKey : extBlocks.keySet()) {
            ConfigurationSection blockConfig = config.createSection(extBlockKey);
            ExtendBlock extBlock = extBlocks.get(extBlockKey);

            blockConfig.set("type", extBlock.getFullName());
            //blockConfig.set("material", extBlock.getMaterial().name());
            extBlock.save(blockConfig.createSection("data"));
        }
    }

    public void unload() {
        for (ExtendBlock extBlock : extBlocks.values()) {
            extBlock.unload();
        }
    }

    private String getKey(int xOffset, int yOffset, int zOffset) {
        return "" + xOffset + "_" + yOffset + "_" + zOffset;
    }

    private int[] getOffsets(String key) {
        String parts[] = key.split("_");
        int ret[] = new int[parts.length];
        for (int i=0; i<ret.length; i++) {
            ret[i] = Integer.parseInt(parts[i]);
        }

        return ret;
    }
}

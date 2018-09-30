package io.github.basicmark.extendminecraft;

import io.github.basicmark.extendminecraft.block.ExtendBlock;
import io.github.basicmark.extendminecraft.block.ExtendBlockFactory;
import io.github.basicmark.extendminecraft.block.MissingBlock;
import io.github.basicmark.extendminecraft.world.ExtendWorld;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExtendChunk {
    private ExtendWorld world;
	private Chunk chunk;
	private Map<String, ExtendBlock> extBlocks = new HashMap<String, ExtendBlock>();
	private boolean loadedBlocks = false;

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

    public boolean shouldSave() {
        return loadedBlocks | isExtended();
    }

    public void clearLoadedBlocks() {
        loadedBlocks = false;
    }

    public void load(ConfigurationSection config) {
        /* If there are no blocks in the configuration section then early exit */
        if (config.getKeys(false).isEmpty()) {
            return;
        }

        loadedBlocks = true;

        /* Load all the blocks in the chunk first */
        for (String key : config.getKeys(false)) {
            ConfigurationSection blockConfig = config.getConfigurationSection(key);
            String blockType = blockConfig.getString("type");
            ExtendBlockFactory factory = ExtendMinecraft.blockRegistry.getLoader(blockType);
            int offsets[] = getOffsets(key);
            Block block = chunk.getBlock(offsets[0], offsets[1], offsets[2]);
            ExtendBlock extBlock = factory.newBlock(block);
            if (extBlock instanceof MissingBlock) {
                MissingBlock missingBlock = (MissingBlock) extBlock;
                missingBlock.setBlockType(blockType);
            }
            boolean success = extBlock.load(blockConfig.getConfigurationSection("data"));
            if (success) {
                extBlocks.put(key, extBlock);
            } else {
                ExtendMinecraft.getInstantce().recordLostBlock(world.getBukkitWorld(), chunk, offsets[0], offsets[1], offsets[2], blockConfig);
                config.set(key, null);
            }
        }

        /* Then inform them the chunk has been loaded */
        for (ExtendBlock extBlock : extBlocks.values()) {
            extBlock.postChunkLoad();
        }
    }

    public void save(ConfigurationSection config) {
        loadedBlocks = true;
        for (String extBlockKey : extBlocks.keySet()) {
            ConfigurationSection blockConfig = config.createSection(extBlockKey);
            ExtendBlock extBlock = extBlocks.get(extBlockKey);

            blockConfig.set("type", extBlock.getFullName());
            extBlock.save(blockConfig.createSection("data"));
        }
    }

    public void unload() {
        for (ExtendBlock extBlock : extBlocks.values()) {
            extBlock.unload();
        }
    }

    /* Replace the missing block holder with that of an instance of the real block */
    public void blockRegistoryUpdate(ExtendBlockFactory factory) {
        boolean updated = false;
        Iterator<String> keyIter = extBlocks.keySet().iterator();
        //for (String extBlockKey : extBlocks.keySet()) {
        while(keyIter.hasNext()) {
            String extBlockKey = keyIter.next();
            ExtendBlock extBlock = extBlocks.get(extBlockKey);
            if (extBlock instanceof MissingBlock) {
                MissingBlock missingBlock = (MissingBlock) extBlock;
                if (missingBlock.getFullName().equals(factory.getFullName())) {
                    ConfigurationSection blockConfig = missingBlock.getStoredConfig();
                    ExtendBlock realExtBlock = factory.newBlock(extBlock.getBukkitBlock());
                    boolean success = realExtBlock.load(blockConfig);
                    if (success) {
                        extBlocks.put(extBlockKey, extBlock);
                        updated = true;
                    } else {
                        int offsets[] = getOffsets(extBlockKey);
                        ExtendMinecraft.getInstantce().recordLostBlock(world.getBukkitWorld(), chunk, offsets[0], offsets[1], offsets[2], blockConfig);
                        keyIter.remove();
                    }
                }
            }
        }

        /*
         * Updating blocks from missing to their real blocks could impact other blocks and so
         * notify all ExtendBlocks in the chunk.
         */
        if (updated) {
            for (ExtendBlock extBlock : extBlocks.values()) {
                extBlock.postChunkLoad();
            }
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

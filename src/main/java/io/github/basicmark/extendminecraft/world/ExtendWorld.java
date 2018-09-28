package io.github.basicmark.extendminecraft.world;

import io.github.basicmark.extendminecraft.ExtendChunk;
import io.github.basicmark.extendminecraft.ExtendMinecraft;
import io.github.basicmark.extendminecraft.block.ExtendBlockFactory;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExtendWorld {
    private ExtendMinecraft plugin;
    private Map<Chunk, ExtendChunk> extChunks;
    private World world;
    private File file;
    private YamlConfiguration data;

    public ExtendWorld(ExtendMinecraft plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.extChunks = new HashMap<Chunk, ExtendChunk>();
        this.file = new File(plugin.getDataFolder() + File.separator + "worlds" + File.separator + world.getName());
        this.data = new YamlConfiguration();

        if (file.exists()) {
            try {
                data.load(file);
                plugin.getLogger().info("Loaded ExtendWorld for " + world.getName());
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to load config for " + world.getName());
            }
        } else {
            plugin.getLogger().info("Created ExtendWorld for " + world.getName());
            data.createSection("chunks");
        }
    }

    public void load() {
    }

    public void saveAll() {
        saveAll(true);
    }

    public void saveAll(boolean saveFile) {
        /* Safety check to make sure all chunks are saved */
        ConfigurationSection chunksSection = data.getConfigurationSection("chunks");
        for (ExtendChunk extChunk : extChunks.values()) {
            if (extChunk.shouldSave()) {
                if (extChunk.isExtended()) {
                    ConfigurationSection chunkSection = chunksSection.createSection(getKey(extChunk));
                    extChunk.save(chunkSection);
                } else {
                    chunksSection.set(getKey(extChunk), null);
                    extChunk.clearLoadedBlocks();
                }
            }
        }
        if (saveFile) {
            try {
                data.save(file);
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to save config for " + world.getName());
            }
        }
    }

    public void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save config for " + world.getName());
        }
    }

    public void unload() {
        /* Safety check to make sure all chunks are saved */
        saveAll();
    }

    public ExtendChunk loadChunk(Chunk chunk, boolean isNew) {
        ConfigurationSection chunksSection = data.getConfigurationSection("chunks");
        ConfigurationSection chunkSection = chunksSection.getConfigurationSection(getKey(chunk));
        ExtendChunk extChunk = new ExtendChunk(this, chunk);

        extChunks.put(chunk, extChunk);
        if (chunkSection != null) {
            extChunk.load(chunkSection);
        }
        return extChunk;
    }

    public void unloadChunk(ExtendChunk extChunk, boolean save) {
        if (extChunk.isExtended()) {
            ConfigurationSection chunksSection = data.getConfigurationSection("chunks");
            ConfigurationSection chunkSection = chunksSection.createSection(getKey(extChunk));
            extChunk.save(chunkSection);
            if (save) {
                save();
            }
            extChunk.unload();
            extChunks.remove(extChunk.getBukkitChunk());
        }
    }

    public ExtendChunk getChunk(Chunk chunk) {
        return extChunks.get(chunk);
    }

    public World getBukkitWorld() {
        return world;
    }

    public void blockRegistoryUpdate(ExtendBlockFactory factory) {
        for (ExtendChunk extChunk : extChunks.values()) {
            extChunk.blockRegistoryUpdate(factory);
        }
    }

    private String getKey(ExtendChunk extChunk) {
        Chunk chunk = extChunk.getBukkitChunk();
        return chunk.getX() + "_" + chunk.getZ();
    }

    private String getKey(Chunk chunk) {
        return chunk.getX() + "_" + chunk.getZ();
    }
}

package io.github.basicmark.extendminecraft.block;

import io.github.basicmark.extendminecraft.ExtendMinecraft;
import io.github.basicmark.extendminecraft.world.ExtendWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class ExtendBlockRegistry {
    private Map<String, ExtendBlockFactory> registry = new HashMap<String, ExtendBlockFactory>();
    private ExtendBlockFactory defaultBlockLoader;

    public ExtendBlockRegistry(ExtendBlockFactory defaultBlockLoader) {
        this.defaultBlockLoader = defaultBlockLoader;
    }

    public void add(ExtendBlockFactory blockFactory) {
        ExtendMinecraft.getInstantce().getLogger().info("Registering block loader for " + blockFactory.getFullName());
        registry.put(blockFactory.getFullName(), blockFactory);
        for (World world : Bukkit.getServer().getWorlds()) {
            ExtendWorld extendWorld = ExtendMinecraft.getInstantce().getWorld(world);
            extendWorld.blockRegistoryUpdate(blockFactory);
        }
    }

    /*
     * Ensure all extended blocks are updated before their loaders are unregistered,
     * the actual saving of file will be handled as part of disabling ExtendMinecraft.
     */
    private void updateConfigs() {
        for (World world : Bukkit.getServer().getWorlds()) {
            ExtendWorld extendWorld = ExtendMinecraft.getInstantce().getWorld(world);
            extendWorld.saveAll(false);
        }
    }

    public void remove(ExtendBlockFactory blockFactory) {
        ExtendMinecraft.getInstantce().getLogger().info("Unregistering block loader for " + blockFactory.getFullName());
        updateConfigs();
        registry.remove(blockFactory.getFullName());
    }

    public void remove(String name) {
        ExtendMinecraft.getInstantce().getLogger().info("Unregistering block loader for " + name);
        updateConfigs();
        registry.remove(name);
    }

    public boolean isDefaultBlock(ExtendBlock block) {
        return block.getFullName() == defaultBlockLoader.getFullName();
    }

    public ExtendBlockFactory getLoader(String name) {
        ExtendBlockFactory loader = registry.get(name);
        if (loader == null) {
            return defaultBlockLoader;
        }

        return loader;
    }
}

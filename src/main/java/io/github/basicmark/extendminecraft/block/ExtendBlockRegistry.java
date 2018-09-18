package io.github.basicmark.extendminecraft.block;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class ExtendBlockRegistry {
    private Map<String, ExtendBlockFactory> registry = new HashMap<String, ExtendBlockFactory>();
    private ExtendBlockFactory defaultBlockLoader;

    public ExtendBlockRegistry(ExtendBlockFactory defaultBlockLoader) {
        this.defaultBlockLoader = defaultBlockLoader;
    }

    public void add(ExtendBlockFactory blockFactory) {
        Bukkit.getLogger().info("Registering block loader for " + blockFactory.getFullName());
        registry.put(blockFactory.getFullName(), blockFactory);
    }

    public void remove(ExtendBlockFactory blockFactory) {
        registry.remove(blockFactory.getFullName());
    }

    public void remove(String name) {
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

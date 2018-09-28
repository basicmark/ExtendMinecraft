package io.github.basicmark.extendminecraft.event.world;

import io.github.basicmark.extendminecraft.ExtendChunk;
import io.github.basicmark.extendminecraft.ExtendMinecraft;
import io.github.basicmark.extendminecraft.Interceptor;
import io.github.basicmark.extendminecraft.world.ExtendWorld;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.*;

import java.util.HashMap;
import java.util.Map;

public class WorldEventInterceptor extends Interceptor implements Listener {
    Map<World, ExtendWorld> worlds = new HashMap<World, ExtendWorld>();

    public WorldEventInterceptor(ExtendMinecraft plugin) {
        super(plugin);
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		/* Load extended world information for already loaded worlds */
        for (World world : plugin.getServer().getWorlds()) {
            WorldLoadEvent worldEvent = new WorldLoadEvent(world);
            onWorldLoad(worldEvent);
            for (Chunk chunk : world.getLoadedChunks()) {
                ChunkLoadEvent chunkEvent = new ChunkLoadEvent(chunk, false);
                onChunkLoad(chunkEvent);
            }
        }
    }

    public ExtendWorld getExtendWorld(World world) {
        return worlds.get(world);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled=true)
    public void onWorldLoad(WorldLoadEvent event) {
        /* Create the extended world */
        ExtendWorld extWorld = new ExtendWorld(plugin, event.getWorld());
        worlds.put(event.getWorld(), extWorld);

        /* Load the extended world data */
        extWorld.load();

        /* Fire the extended world event */
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled=true)
    public void onWorldUnload(WorldUnloadEvent event) {
        ExtendWorld extWorld = worlds.get(event.getWorld());

        /* Fire the extended world unload event */

        /* Save the extended world data */
        extWorld.unload();

        /* Remove the world */
        worlds.remove(event.getWorld());
    }
	
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled=true)
    public void onChunkLoad(ChunkLoadEvent event) {
        /* Create the extended chunk */
        ExtendWorld extWorld = worlds.get(event.getWorld());
        if (extWorld == null) {
            plugin.getLogger().severe("Missing world in onChunkLoad for " + event.getWorld().getName());
            plugin.getLogger().severe("Have following worlds");
            for (World listWorld : worlds.keySet()) {
                plugin.getLogger().severe(listWorld.getName());
            }
            return;
        }
        ExtendChunk extChunk;
        extChunk = extWorld.loadChunk(event.getChunk(), event.isNewChunk());

        /* Load the extended chunk data ready for operations */
        
        /* Fire the extended event */
        ExtendChunkLoadEvent extEvent = new ExtendChunkLoadEvent(extChunk, event.isNewChunk());
        plugin.getServer().getPluginManager().callEvent(extEvent);
    }
	
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled=true)
    public void onChunkUnload(ChunkUnloadEvent event) {
        ExtendWorld extWorld = worlds.get(event.getWorld());
        ExtendChunk extChunk = extWorld.getChunk(event.getChunk());

        /* Fire the extended event */
        ExtendChunkUnloadEvent extEvent = new ExtendChunkUnloadEvent(extChunk, event.isSaveChunk());
        plugin.getServer().getPluginManager().callEvent(extEvent);

        if (event.isSaveChunk()) {
            /* Save the extended chunk data */
            extWorld.unloadChunk(extChunk, event.isSaveChunk());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled=true)
    public void onWorldSaveEvent(WorldSaveEvent event) {
        ExtendWorld extWorld = worlds.get(event.getWorld());
        extWorld.saveAll();
    }
}

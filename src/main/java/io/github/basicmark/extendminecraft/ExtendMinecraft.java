package io.github.basicmark.extendminecraft;

import io.github.basicmark.extendminecraft.block.*;
import io.github.basicmark.extendminecraft.event.block.ExtendBlockChangeEvent;
import io.github.basicmark.extendminecraft.event.world.WorldEventInterceptor;
import io.github.basicmark.extendminecraft.world.ExtendWorld;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;


public class ExtendMinecraft extends JavaPlugin {
    public static ExtendBlockRegistry blockRegistry = new ExtendBlockRegistry(new MissingBlockFactory());
    WorldEventInterceptor worldEventInterceptor;
    static private ExtendMinecraft instance = null;

    static public ExtendMinecraft getInstantce() {
        return instance;
    }

	public void onEnable() {
        instance = this;
        worldEventInterceptor = new WorldEventInterceptor(this);
        ExtendMinecraft.blockRegistry.add(new TestBlockFactory(1));
        ExtendMinecraft.blockRegistry.add(new TestBlockFactory(2));
	}

	public void onDisable() {
 
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("extendminecraft")){
		    if ((args.length == 2) && args[0].equals("add")) {
                String name = args[1];
		        if (sender instanceof Player) {
		            Player player = (Player) sender;
                    ExtendWorld extendWorld = worldEventInterceptor.getExtendWorld(player.getWorld());
                    ExtendChunk extChunk = extendWorld.getChunk(player.getLocation().getChunk());
                    Block block = player.getLocation().getChunk().getBlock(
                            player.getLocation().getBlockX() & 0xf,
                            player.getLocation().getBlockY() & 0xff,
                            player.getLocation().getBlockZ() & 0xf);
                    ExtendBlock extBlock = blockRegistry.getLoader(name).newBlock(block);
                    getLogger().info(extBlock.toString());
                    extChunk.setBlock(extBlock,
                            player.getLocation().getBlockX() & 0xf,
                            player.getLocation().getBlockY() & 0xff,
                            player.getLocation().getBlockZ() & 0xf);

		            block.setType(Material.GOLD_BLOCK,true);
                    worldEventInterceptor.getExtendWorld(player.getWorld()).save();
                }
            }
			return true;
		}
		return false;
	}

	public void setBlock(ExtendBlock extBlock) {
	    Block block = extBlock.getBukkitBlock();
	    ExtendBlock beforeExtBlock = getBlock(block);
	    if (beforeExtBlock == null) {
            beforeExtBlock = new NullBlock(block);
        }
        ExtendWorld extWorld = worldEventInterceptor.getExtendWorld(block.getWorld());
        ExtendChunk extChunk = extWorld.getChunk(block.getChunk());
        extChunk.setBlock(extBlock,
                block.getX() & 0xf,
                block.getY() & 0xff,
                block.getZ() & 0xf);

        ExtendBlockChangeEvent changeEvent = new ExtendBlockChangeEvent(beforeExtBlock, extBlock);
        getServer().getPluginManager().callEvent(changeEvent);
    }

	public ExtendBlock getBlock(Block block) {
	    ExtendWorld extWorld = worldEventInterceptor.getExtendWorld(block.getWorld());
	    if (extWorld == null) {
	        return new NullBlock(block);
        }

        ExtendChunk extChunk = extWorld.getChunk(block.getChunk());
        if (extChunk == null) {
            return new NullBlock(block);
        }

        ExtendBlock extBlock = extChunk.getBlock(block.getX() & 0xf, block.getY(), block.getZ() & 0xf);
        return extBlock;
    }

    public void clearBlock(ExtendBlock extBlock) {
        Block block = extBlock.getBukkitBlock();
        ExtendWorld extWorld = worldEventInterceptor.getExtendWorld(block.getWorld());
        ExtendChunk extChunk = extWorld.getChunk(block.getChunk());
        extChunk.clearBlock(
                block.getX() & 0xf,
                block.getY() & 0xff,
                block.getZ() & 0xf);

        ExtendBlockChangeEvent changeEvent = new ExtendBlockChangeEvent(extBlock, new NullBlock(block));
        getServer().getPluginManager().callEvent(changeEvent);
    }
}


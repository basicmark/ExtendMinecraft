package io.github.basicmark.extendminecraft;

import io.github.basicmark.extendminecraft.block.*;
import io.github.basicmark.extendminecraft.event.block.ExtendBlockChangeEvent;
import io.github.basicmark.extendminecraft.event.world.WorldEventInterceptor;
import io.github.basicmark.extendminecraft.world.ExtendWorld;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

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
	}

	public void onDisable() {
        for (World world : getServer().getWorlds()) {
            ExtendWorld extendWorld = getWorld(world);
            extendWorld.saveAll();
        }
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("extendminecraft")){
		    if ((args.length == 1) && args[0].equals("?")) {
                sender.sendMessage("ExtendMinecraft, a plugin to add custom blocks and more to minecraft");
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

    public ExtendWorld getWorld(World world) {
        return worldEventInterceptor.getExtendWorld(world);
    }
}


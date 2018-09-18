package io.github.basicmark.extendminecraft.block;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

public class TestBlockFactory extends ExtendBlockFactory<ExtendBlock>{
    int number;

    public TestBlockFactory(int number) {
        super("extendminecraft", "testblock" + number);
        this.number = number;
    }

    @Override
    public TestBlock newBlock(Block block) {
        TestBlockSub extBlock = new TestBlockSub(block, getFullName());

        return extBlock;
    }

    class TestBlock extends ExtendBlock {

        public TestBlock(Block block, String fullName) {
            super(block, fullName);
        }

        @Override
        public void save(ConfigurationSection config) {
            config.set("message", "This is test block ");
        }

    }

    class TestBlockSub extends TestBlock {

        public TestBlockSub(Block block, String fullName) {
            super(block, fullName);
        }

        @Override
        public void save(ConfigurationSection config) {
            config.set("message", "This is test block sub");
        }

    }

}

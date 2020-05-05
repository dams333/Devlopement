package ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class OneSecondBlock extends DevBlock{

    public OneSecondBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.ONE_SECOND);
    }

    public OneSecondBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.ONE_SECOND);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ONE_SECOND;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/wait/one_second/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        try {
            blockConfig.save(blockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

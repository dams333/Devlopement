package ch.dams333.devlopement.objects.devBlock.blocks.start_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DevStartBlock extends DevBlock{

    public DevStartBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.ONE_SECOND);
    }

    public DevStartBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.ONE_SECOND);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ONE_SECOND;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/start/dev_start/" + super.getUuid().toString() + ".yml");
        try {
            Files.deleteIfExists(blockFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

package ch.dams333.devlopement.objects.devBlock.blocks.start_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ElseStartBlock extends DevBlock{

    public ElseStartBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.ELSE_START);
    }

    public ElseStartBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.ELSE_START);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ELSE_START;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/start/else_start/" + super.getUuid().toString() + ".yml");
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

    @Override
    public void clickOn(PlayerInteractEvent e) {

    }

    @Override
    public void execute(CodeExecutor codeExecutor) {

    }

    @Override
    public void inventoryOn(InventoryClickEvent e) {

    }

}

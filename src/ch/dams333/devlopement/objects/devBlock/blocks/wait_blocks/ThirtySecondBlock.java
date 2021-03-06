package ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.io.IOException;

public class ThirtySecondBlock extends DevBlock{

    public ThirtySecondBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.THIRTY_SECOND);
    }

    public ThirtySecondBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.THIRTY_SECOND);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.THIRTY_SECOND;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/wait/thirty_second/" + super.getUuid().toString() + ".yml");
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
    public void clickOn(PlayerInteractEvent p) {

    }

    @Override
    public void execute(CodeExecutor codeExecutor) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(super.getMain(), new Runnable() {
            @Override
            public void run() {
                codeExecutor.moveHead();
            }
        }, 600L);
    }

    @Override
    public void inventoryOn(InventoryClickEvent e) {

    }


}

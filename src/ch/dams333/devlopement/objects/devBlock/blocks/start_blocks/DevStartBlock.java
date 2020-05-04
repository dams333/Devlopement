package ch.dams333.devlopement.objects.devBlock.blocks.start_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.Location;

public class DevStartBlock extends DevBlock{

    public DevStartBlock(Devlopement main, Location loc) {
        super(main, loc);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEV_START;
    }

}

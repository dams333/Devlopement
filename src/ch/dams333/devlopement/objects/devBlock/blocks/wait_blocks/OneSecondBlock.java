package ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.Location;

public class OneSecondBlock extends DevBlock{

    public OneSecondBlock(Devlopement main, Location loc) {
        super(main, loc);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ONE_SECOND;
    }

}

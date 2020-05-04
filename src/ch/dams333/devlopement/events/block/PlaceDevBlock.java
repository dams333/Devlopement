package ch.dams333.devlopement.events.block;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.devBlock.blocks.start_blocks.DevStartBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks.OneSecondBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceDevBlock implements Listener {
    Devlopement main;
    public PlaceDevBlock(Devlopement devlopement) {
        this.main = devlopement;
    }

    @EventHandler
    public void place(BlockPlaceEvent e){
        if(main.isInDevMod(e.getPlayer())){
            for(BlockType blockType : BlockType.values()){
                if(blockType.getMaterial() == e.getBlock().getType()){

                    if(blockType.toString().equalsIgnoreCase(BlockType.ONE_SECOND.toString())){
                        main.addDevBlock(new OneSecondBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.DEV_START.toString())){
                        main.addDevBlock(new DevStartBlock(main, e.getBlock().getLocation()));
                    }

                }
            }
        }
    }

}

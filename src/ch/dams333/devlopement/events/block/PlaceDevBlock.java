package ch.dams333.devlopement.events.block;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.devBlock.blocks.clear_blocks.ClearEffectsBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.clear_blocks.ClearItemsBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.condition_blocks.PlayerConditionBlockBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.give_blocks.GiveEffectsBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.give_blocks.GiveItemsBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.message_blocks.ActionbarMessageBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.message_blocks.ChatMessageBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.message_blocks.SubTitleMessageBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.message_blocks.TitleMessageBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.player_blocks.GameModeBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.player_blocks.PvpBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.start_blocks.DevStartBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.start_blocks.ElseStartBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.start_blocks.EventStartBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.tp_blocks.LastLocationTeleportationBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.tp_blocks.LocationTeleportationBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.tp_blocks.LocationsListTeleportationBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.variable_blocks.BooleanModifyBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.variable_blocks.IntegerModifyBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks.FiveSecondBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks.OneSecondBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks.TenSecondBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks.ThirtySecondBlock;
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
                    if(blockType.toString().equalsIgnoreCase(BlockType.FIVE_SECOND.toString())){
                        main.addDevBlock(new FiveSecondBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.TEN_SECOND.toString())){
                        main.addDevBlock(new TenSecondBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.THIRTY_SECOND.toString())){
                        main.addDevBlock(new ThirtySecondBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.DEV_START.toString())){
                        main.addDevBlock(new DevStartBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.EVENT_START.toString())){
                        main.addDevBlock(new EventStartBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.GIVE_ITEMS.toString())){
                        main.addDevBlock(new GiveItemsBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.GIVE_EFFECTS.toString())){
                        main.addDevBlock(new GiveEffectsBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.CHAT_MESSAGE.toString())){
                        main.addDevBlock(new ChatMessageBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.TITLE_MESSAGE.toString())){
                        main.addDevBlock(new TitleMessageBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.SUBTITLE_MESSAGE.toString())){
                        main.addDevBlock(new SubTitleMessageBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.ACTIONBAR_MESSAGE.toString())){
                        main.addDevBlock(new ActionbarMessageBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.LOCATION_TP.toString())){
                        main.addDevBlock(new LocationTeleportationBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.LIST_TP.toString())){
                        main.addDevBlock(new LocationsListTeleportationBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.LAST_TP.toString())){
                        main.addDevBlock(new LastLocationTeleportationBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.CLEAR_EFFECTS.toString())){
                        main.addDevBlock(new ClearEffectsBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.CLEAR_ITEMS.toString())){
                        main.addDevBlock(new ClearItemsBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.GAMEMODE.toString())){
                        main.addDevBlock(new GameModeBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.PVP.toString())){
                        main.addDevBlock(new PvpBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.ELSE_START.toString())){
                        main.addDevBlock(new ElseStartBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.PLAYER_CONDITION.toString())){
                        main.addDevBlock(new PlayerConditionBlockBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.INTEGER_MODIFY_VARIABLE.toString())){
                        main.addDevBlock(new IntegerModifyBlock(main, e.getBlock().getLocation()));
                    }
                    if(blockType.toString().equalsIgnoreCase(BlockType.BOOLEAN_MODIFY_VARIABLE.toString())){
                        main.addDevBlock(new BooleanModifyBlock(main, e.getBlock().getLocation()));
                    }
                }
            }
        }
    }

}

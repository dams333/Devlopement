package ch.dams333.devlopement.objects.devBlock.blocks;

import ch.dams333.devlopement.Devlopement;
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

import java.io.File;
import java.util.Objects;

public class BlocksDeserializer {

    public static void deserialize(Devlopement main){
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "one_second").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "one_second").listFiles())) {
                main.addDevBlock(new OneSecondBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "five_second").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "five_second").listFiles())) {
                main.addDevBlock(new FiveSecondBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "ten_second").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "ten_second").listFiles())) {
                main.addDevBlock(new TenSecondBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "thirty_second").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "thirty_second").listFiles())) {
                main.addDevBlock(new ThirtySecondBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "dev_start").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "dev_start").listFiles())) {
                main.addDevBlock(new DevStartBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "event_start").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "event_start").listFiles())) {
                main.addDevBlock(new EventStartBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "give" + File.separator + "give_items").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "give" + File.separator + "give_items").listFiles())) {
                main.addDevBlock(new GiveItemsBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "give" + File.separator + "give_effects").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "give" + File.separator + "give_effects").listFiles())) {
                main.addDevBlock(new GiveEffectsBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "chat_message").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "chat_message").listFiles())) {
                main.addDevBlock(new ChatMessageBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "title_message").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "title_message").listFiles())) {
                main.addDevBlock(new TitleMessageBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "subtitle_message").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "subtitle_message").listFiles())) {
                main.addDevBlock(new SubTitleMessageBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "actionbar_message").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "message" + File.separator + "actionbar_message").listFiles())) {
                main.addDevBlock(new ActionbarMessageBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "tp" + File.separator + "location_tp").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "tp" + File.separator + "location_tp").listFiles())) {
                main.addDevBlock(new LocationTeleportationBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "tp" + File.separator + "list_tp").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "tp" + File.separator + "list_tp").listFiles())) {
                main.addDevBlock(new LocationsListTeleportationBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "tp" + File.separator + "last_tp").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "tp" + File.separator + "last_tp").listFiles())) {
                main.addDevBlock(new LastLocationTeleportationBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "clear" + File.separator + "clear_items").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "clear" + File.separator + "clear_items").listFiles())) {
                main.addDevBlock(new ClearItemsBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "clear" + File.separator + "clear_effects").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "clear" + File.separator + "clear_effects").listFiles())) {
                main.addDevBlock(new ClearEffectsBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "player" + File.separator + "gamemode").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "player" + File.separator + "gamemode").listFiles())) {
                main.addDevBlock(new GameModeBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "player" + File.separator + "pvp").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "player" + File.separator + "pvp").listFiles())) {
                main.addDevBlock(new PvpBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "else_start").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "else_start").listFiles())) {
                main.addDevBlock(new ElseStartBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "condition" + File.separator + "player_condition").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "condition" + File.separator + "player_condition").listFiles())) {
                main.addDevBlock(new PlayerConditionBlockBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "variables" + File.separator + "integer_modify").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "variables" + File.separator + "integer_modify").listFiles())) {
                main.addDevBlock(new IntegerModifyBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "variables" + File.separator + "boolean_modify").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "variables" + File.separator + "boolean_modify").listFiles())) {
                main.addDevBlock(new BooleanModifyBlock(file, main));
            }
        }
    }

}

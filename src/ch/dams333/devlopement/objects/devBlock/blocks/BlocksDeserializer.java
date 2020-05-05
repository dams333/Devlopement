package ch.dams333.devlopement.objects.devBlock.blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.blocks.start_blocks.DevStartBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.wait_blocks.OneSecondBlock;

import java.io.File;
import java.util.Objects;

public class BlocksDeserializer {

    public static void deserialize(Devlopement main){
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "one_second").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "wait" + File.separator + "one_second").listFiles())) {
                main.addDevBlock(new OneSecondBlock(file, main));
            }
        }
        if(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "dev_start").listFiles() != null) {
            for (File file : Objects.requireNonNull(new File(main.getDataFolder() + File.separator + "blocks" + File.separator + "start" + File.separator + "dev_start").listFiles())) {
                main.addDevBlock(new DevStartBlock(file, main));
            }
        }
    }

}

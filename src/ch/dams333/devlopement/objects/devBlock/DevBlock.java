package ch.dams333.devlopement.objects.devBlock;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public abstract class DevBlock implements Listener {

    private UUID uuid;
    private Location loc;
    private BlockType blockType;
    private Devlopement main;

    public DevBlock(Devlopement main, Location loc) {
        this.main = main;
        this.uuid = UUID.randomUUID();
        this.loc = loc;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    public abstract BlockType getBlockType();

    public UUID getUuid() {
        return uuid;
    }

    public Location getLoc() {
        return loc;
    }

    public String getName() {
        return blockType.getName();
    }

    public List<String> getDescription() {
        return blockType.getDescription();
    }

    public Material getMaterial() {
        return blockType.getMaterial();
    }

    public byte getData() {
        return blockType.getData();
    }


}

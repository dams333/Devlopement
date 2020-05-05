package ch.dams333.devlopement.objects.devBlock;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.File;
import java.util.List;
import java.util.UUID;

public abstract class DevBlock implements Listener {

    private UUID uuid;
    private Location loc;
    private BlockType blockType;
    private Devlopement main;

    public DevBlock(Devlopement main, Location loc) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
        this.uuid = UUID.randomUUID();
        this.loc = loc;
    }

    public DevBlock(File file, Devlopement main) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
        this.uuid = UUID.fromString(file.getName().replaceAll(".yml", ""));
        this.loc = this.deserializeLoc(YamlConfiguration.loadConfiguration(file));
    }

    public abstract BlockType getBlockType();
    public abstract void serialize();

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


    protected Devlopement getMain() {
        return main;
    }


    @EventHandler
    public void blockBreak(BlockBreakEvent e){
        if(e.getBlock().getLocation().getWorld() == loc.getWorld() && e.getBlock().getLocation().getX() == loc.getX() && e.getBlock().getLocation().getY() == loc.getY() && e.getBlock().getLocation().getZ() == loc.getZ()){
            main.removeBlock(this);
        }
    }



    protected YamlConfiguration saveLocation(YamlConfiguration configuration, Location loc) {
        ConfigurationSection sec = configuration.createSection("Location");
        sec.set("World", loc.getWorld().getName());
        sec.set("X", loc.getX());
        sec.set("Y", loc.getY());
        sec.set("Z", loc.getZ());
        return configuration;
    }

    private Location deserializeLoc(YamlConfiguration configuration) {
        ConfigurationSection sec = configuration.getConfigurationSection("Location");
        World world = Bukkit.getWorld(sec.getString("World"));
        Double x = sec.getDouble("X");
        Double y = sec.getDouble("Y");
        Double z = sec.getDouble("Z");
        return  new Location(world, x, y, z);
    }

    protected void setType(BlockType type) {
        this.blockType = type;
    }


}

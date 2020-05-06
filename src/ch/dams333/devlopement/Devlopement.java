package ch.dams333.devlopement;

import ch.dams333.damsLib.DamsLIB;
import ch.dams333.devlopement.commands.admin.DevItemsCommand;
import ch.dams333.devlopement.commands.admin.DevModCommand;
import ch.dams333.devlopement.commands.admin.MessageCommand;
import ch.dams333.devlopement.events.actions.ClickInInventory;
import ch.dams333.devlopement.events.actions.CloseInventoryEvent;
import ch.dams333.devlopement.events.block.PlaceDevBlock;
import ch.dams333.devlopement.events.startLine.StartLineEvents;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.BlocksDeserializer;
import ch.dams333.devlopement.objects.messages.MessagesManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Devlopement extends JavaPlugin {

    private List<String> devmod;
    private List<DevBlock> devBlocks;

    private Map<Player, DevBlock> inModif;

    public static DamsLIB API;

    public MessagesManager messagesManager;

    @Override
    public void onEnable() {

        if(!new File(String.valueOf(getDataFolder())).exists()) {
            getDataFolder().mkdir();
        }

        API = (DamsLIB) getServer().getPluginManager().getPlugin("DamsLIB");

        this.messagesManager = new MessagesManager(this);

        devmod = new ArrayList<>();
        devBlocks = new ArrayList<>();
        inModif = new HashMap<>();


        getCommand("devmod").setExecutor(new DevModCommand(this));
        getCommand("devitems").setExecutor(new DevItemsCommand(this));
        getCommand("message").setExecutor(new MessageCommand(this));

        getServer().getPluginManager().registerEvents(new PlaceDevBlock(this), this);
        getServer().getPluginManager().registerEvents(new ClickInInventory(this), this);
        getServer().getPluginManager().registerEvents(new StartLineEvents(this), this);
        getServer().getPluginManager().registerEvents(new CloseInventoryEvent(this), this);


        BlocksDeserializer.deserialize(this);
        messagesManager.deserialize();


        //DEBUG ADD DAMS333 TO DEVMOD
        this.devmod.add("Dams333");
    }

    @Override
    public void onDisable() {
        deleteFilesForFolder(new File(String.valueOf(getDataFolder())));
        for(DevBlock devBlock : this.devBlocks){
            devBlock.serialize();
        }
        messagesManager.serialize();
    }

    public boolean isInDevMod(Player p) {
        return this.devmod.contains(p.getName());
    }

    public void addIntoDevMod(Player p) {
        this.devmod.add(p.getName());
    }

    public void removeFromDevMod(Player p) {
        this.devmod.remove(p.getName());
    }

    public void addDevBlock(DevBlock devBlock) {
        this.devBlocks.add(devBlock);
    }


    public void removeBlock(DevBlock devBlock) {
        for (DevBlock selfDevBlock : this.devBlocks) {
            if (selfDevBlock.getUuid().equals(devBlock.getUuid())) {
                this.devBlocks.remove(selfDevBlock);
                return;
            }
        }
    }

    private void deleteFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                deleteFilesForFolder(fileEntry);
            } else {
                try {
                    Files.deleteIfExists(Paths.get(fileEntry.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<DevBlock> getDevBlocks() {
        return devBlocks;
    }

    public void enterModif(Player p, DevBlock devBlock){
        this.inModif.put(p, devBlock);
    }

    public DevBlock getInModif(Player p){
        if(this.inModif.keySet().contains(p)){
            return this.inModif.get(p);
        }
        return null;
    }

    public boolean isInModif(Player p){
        return this.inModif.keySet().contains(p);
    }

    public void updateModif(DevBlock devBlock) {
        for(DevBlock selfDevBlock : this.devBlocks){
            if(selfDevBlock.getUuid().equals(devBlock.getUuid())){
                this.devBlocks.remove(selfDevBlock);
                break;
            }
        }
        this.devBlocks.add(devBlock);
    }

    public void closeModif(Player player) {
        this.inModif.remove(player);
    }
}


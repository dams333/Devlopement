package ch.dams333.devlopement;

import ch.dams333.damsLib.DamsLIB;
import ch.dams333.devlopement.commands.admin.*;
import ch.dams333.devlopement.events.actions.ClickInInventory;
import ch.dams333.devlopement.events.actions.CloseInventoryEvent;
import ch.dams333.devlopement.events.actions.LinkEvent;
import ch.dams333.devlopement.events.block.PlaceDevBlock;
import ch.dams333.devlopement.events.startLine.StartLineEvents;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blocks.BlocksDeserializer;
import ch.dams333.devlopement.objects.devBlock.link.LinksManager;
import ch.dams333.devlopement.objects.locations.LocationsManager;
import ch.dams333.devlopement.objects.messages.MessagesManager;
import ch.dams333.devlopement.objects.variables.VariablesManager;
import org.bukkit.configuration.ConfigurationSection;
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
    public LocationsManager locationsManager;
    public LinksManager linksManager;
    public VariablesManager variablesManager;

    private boolean pvp;

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    @Override
    public void onEnable() {

        if(!new File(String.valueOf(getDataFolder())).exists()) {
            getDataFolder().mkdir();
        }

        API = (DamsLIB) getServer().getPluginManager().getPlugin("DamsLIB");

        this.messagesManager = new MessagesManager(this);
        this.locationsManager = new LocationsManager(this);
        this.linksManager = new LinksManager(this);
        this.variablesManager = new VariablesManager(this);

        devmod = new ArrayList<>();
        devBlocks = new ArrayList<>();
        inModif = new HashMap<>();


        getCommand("devmod").setExecutor(new DevModCommand(this));
        getCommand("devitems").setExecutor(new DevItemsCommand(this));
        getCommand("message").setExecutor(new MessageCommand(this));
        getCommand("location").setExecutor(new LocationCommand(this));
        getCommand("link").setExecutor(new LinkCommand(this));
        getCommand("variable").setExecutor(new VariableCommand(this));

        getServer().getPluginManager().registerEvents(new PlaceDevBlock(this), this);
        getServer().getPluginManager().registerEvents(new ClickInInventory(this), this);
        getServer().getPluginManager().registerEvents(new StartLineEvents(this), this);
        getServer().getPluginManager().registerEvents(new CloseInventoryEvent(this), this);
        getServer().getPluginManager().registerEvents(new LinkEvent(this), this);


        BlocksDeserializer.deserialize(this);
        messagesManager.deserialize();
        locationsManager.deserialize();
        linksManager.deserialize();
        variablesManager.deserialize();

        this.pvp = true;
        if(getConfig().isConfigurationSection("Global variables")){
            this.pvp = getConfig().getBoolean("PVP");
        }


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
        locationsManager.serialize();
        linksManager.serialize();
        variablesManager.serialize();

        for(String key : getConfig().getKeys(false)){
            getConfig().set(key, null);
        }

        ConfigurationSection sec = getConfig().createSection("Global variables");
        sec.set("PVP", this.pvp);

        saveConfig();

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


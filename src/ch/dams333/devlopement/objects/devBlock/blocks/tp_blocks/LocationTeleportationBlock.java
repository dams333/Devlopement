package ch.dams333.devlopement.objects.devBlock.blocks.tp_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class LocationTeleportationBlock extends DevBlock{

    private UUID locationUUID;
    private boolean all;

    public LocationTeleportationBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.LOCATION_TP);
        locationUUID = null;
        all = true;
    }

    public LocationTeleportationBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.LOCATION_TP);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(config.getKeys(false).contains("Tp")){
            this.locationUUID = UUID.fromString(config.getString("Tp"));
        }else{
            locationUUID = null;
        }
        all = config.getBoolean("All");

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LOCATION_TP;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/tp/location_tp/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        if(locationUUID != null){
            blockConfig.set("Tp", locationUUID.toString());
        }
        blockConfig.set("All", all);

        try {
            blockConfig.save(blockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickOn(PlayerInteractEvent e) {
        if(e.getPlayer().getPose() == Pose.SNEAKING) {
            e.setCancelled(true);
            getMain().enterModif(e.getPlayer(), this);
            this.loadWhoInventory(e.getPlayer());
        }else{
            e.setCancelled(true);
            getMain().enterModif(e.getPlayer(), this);
            this.loadLocationsInventory(e.getPlayer(), 1);
        }
    }


    @Override
    public void inventoryOn(InventoryClickEvent e) {
        if(e.getView().getTitle().equals(ChatColor.GOLD + "A qui envoyer le message")){
            if(e.getCurrentItem().getType() == Material.REDSTONE){
                this.all = true;
            }
            if(e.getCurrentItem().getType() == Material.EMERALD){
                this.all = false;
            }
            getMain().updateModif(this);
            loadWhoInventory((Player) e.getWhoClicked());
            getMain().enterModif((Player) e.getWhoClicked(), this);
        }else{
            ItemStack it = e.getCurrentItem();
            int page = Integer.parseInt(e.getView().getTitle().replaceAll(ChatColor.GOLD + "Choix de la location > Page ", ""));
            if(it.getType() == Material.PAPER){
                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                locationUUID = UUID.fromString(tag.getString("uuid"));
            }else if(it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page précédente")){
                if(page > 1){
                    page = page - 1;
                }
            }else if(it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page suivante")){
                int messagePerPage = 5 * 9;
                int start = page * messagePerPage;
                start++;
                if((getMain().locationsManager.getPlayerLocations((Player) e.getWhoClicked()).size()) >= start){
                    page = page + 1;
                }
            }
            getMain().updateModif(this);
            loadLocationsInventory((Player) e.getWhoClicked(), page);
            getMain().enterModif((Player) e.getWhoClicked(), this);
        }
    }

    private void loadWhoInventory(Player p){

        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "A qui envoyer le message");

        if(this.all){
            inv.setItem(4, getMain().API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "Tous les joueurs", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }else{
            inv.setItem(4, getMain().API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "Joueur de la ligne", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }

        p.openInventory(inv);

    }

    private void loadLocationsInventory(Player p, int page){
        getMain().locationsManager.loadLocationsInventory(p, page, this.locationUUID);
    }


    @Override
    public void execute(CodeExecutor codeExecutor) {

        if(all){
            for(Player p : Bukkit.getOnlinePlayers()){
                p.teleport(getMain().locationsManager.getLocation(this.locationUUID).getLocation());
                getMain().locationsManager.tp(p, getMain().locationsManager.getLocation(this.locationUUID).getLocation());
            }
        }else{
            codeExecutor.getLinePlayer().teleport(getMain().locationsManager.getLocation(this.locationUUID).getLocation());
            getMain().locationsManager.tp(codeExecutor.getLinePlayer(), getMain().locationsManager.getLocation(this.locationUUID).getLocation());
        }

        codeExecutor.moveHead();
    }


}

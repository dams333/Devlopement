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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocationsListTeleportationBlock extends DevBlock{

    private UUID locationUUID;

    public LocationsListTeleportationBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.LIST_TP);
        locationUUID = null;
    }

    public LocationsListTeleportationBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.LIST_TP);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(config.getKeys(false).contains("Tp")){
            this.locationUUID = UUID.fromString(config.getString("Tp"));
        }else{
            locationUUID = null;
        }

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LIST_TP;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/tp/list_tp/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        if(locationUUID != null){
            blockConfig.set("Tp", locationUUID.toString());
        }

        try {
            blockConfig.save(blockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickOn(PlayerInteractEvent e) {
            e.setCancelled(true);
            getMain().enterModif(e.getPlayer(), this);
            this.loadLocationsInventory(e.getPlayer(), 1);
    }


    @Override
    public void inventoryOn(InventoryClickEvent e) {
            ItemStack it = e.getCurrentItem();
            int page = Integer.parseInt(e.getView().getTitle().replaceAll(ChatColor.GOLD + "Choix de la liste de locations > Page ", ""));
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
                if((getMain().locationsManager.getPlayerLocationsLists((Player) e.getWhoClicked()).size()) >= start){
                    page = page + 1;
                }
            }
            getMain().updateModif(this);
            loadLocationsInventory((Player) e.getWhoClicked(), page);
            getMain().enterModif((Player) e.getWhoClicked(), this);
    }

    private void loadLocationsInventory(Player p, int page){
        getMain().locationsManager.loadLocationsListsInventory(p, page, this.locationUUID);
    }


    @Override
    public void execute(CodeExecutor codeExecutor) {

        List<Location> locs = new ArrayList<>();
        for(UUID gameLocation : getMain().locationsManager.getLocationsList(this.locationUUID).getLocations()){
            locs.add(getMain().locationsManager.getLocation(gameLocation).getLocation());
        }

        for(Player p : Bukkit.getOnlinePlayers()){
            if(locs.size() > 0){
                p.teleport(locs.get(0));
                getMain().locationsManager.tp(p, locs.get(0));
                locs.remove(locs.get(0));
            }
        }

        codeExecutor.moveHead();
    }


}

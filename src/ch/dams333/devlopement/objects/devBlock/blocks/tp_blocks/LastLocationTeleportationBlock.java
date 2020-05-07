package ch.dams333.devlopement.objects.devBlock.blocks.tp_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LastLocationTeleportationBlock extends DevBlock{

    private boolean all;

    public LastLocationTeleportationBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.LAST_TP);
        all = true;
    }

    public LastLocationTeleportationBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.LAST_TP);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        all = config.getBoolean("All");

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LAST_TP;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/tp/last_tp/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        blockConfig.set("All", all);

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
            this.loadWhoInventory(e.getPlayer());

    }


    @Override
    public void inventoryOn(InventoryClickEvent e) {

            if(e.getCurrentItem().getType() == Material.REDSTONE){
                this.all = true;
            }
            if(e.getCurrentItem().getType() == Material.EMERALD){
                this.all = false;
            }
            getMain().updateModif(this);
            loadWhoInventory((Player) e.getWhoClicked());
            getMain().enterModif((Player) e.getWhoClicked(), this);

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



    @Override
    public void execute(CodeExecutor codeExecutor) {

        if(all){
            for(Player p : Bukkit.getOnlinePlayers()){
                if(getMain().locationsManager.hasLastTP(p)){
                    getMain().locationsManager.teleportToLast(p);
                }
            }
        }else{
            if(getMain().locationsManager.hasLastTP(codeExecutor.getLinePlayer())){
                getMain().locationsManager.teleportToLast(codeExecutor.getLinePlayer());
            }
        }

        codeExecutor.moveHead();
    }


}

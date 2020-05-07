package ch.dams333.devlopement.objects.devBlock.blocks.give_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GiveItemsBlock extends DevBlock{

    private boolean all;

    public GiveItemsBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.GIVE_ITEMS);
        all = true;
    }

    public GiveItemsBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.GIVE_ITEMS);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        all = config.getBoolean("All");

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GIVE_ITEMS;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/give/give_items/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        blockConfig.set("All", this.all);

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
            this.loadInventory(e.getPlayer());
        }
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
        loadInventory((Player) e.getWhoClicked());
        getMain().enterModif((Player) e.getWhoClicked(), this);
    }

    @Override
    public void execute(CodeExecutor codeExecutor) {

        if(this.all){
            for(Player p : Bukkit.getOnlinePlayers()){
                for(ItemStack it : ((Chest)getLoc().getBlock().getState()).getInventory()){
                    if(it != null){
                        if(getMain().API.itemStackManager.isBoots(it) && p.getInventory().getBoots() == null){
                            p.getInventory().setBoots(it);
                        }else if(getMain().API.itemStackManager.isLeggings(it) && p.getInventory().getLeggings() == null){
                            p.getInventory().setLeggings(it);
                        }else if(getMain().API.itemStackManager.isChestplate(it) && p.getInventory().getChestplate() == null){
                            p.getInventory().setChestplate(it);
                        }else if(getMain().API.itemStackManager.isHelmet(it) && p.getInventory().getHelmet() == null){
                            p.getInventory().setHelmet(it);
                        }else {
                            p.getInventory().addItem(it);
                        }
                    }
                }
            }
        }else{
            for(ItemStack it : ((Chest)getLoc().getBlock().getState()).getInventory()){
                if(it != null){
                    if(getMain().API.itemStackManager.isBoots(it) && codeExecutor.getLinePlayer().getInventory().getBoots() == null){
                        codeExecutor.getLinePlayer().getInventory().setBoots(it);
                    }else if(getMain().API.itemStackManager.isLeggings(it) && codeExecutor.getLinePlayer().getInventory().getLeggings() == null){
                        codeExecutor.getLinePlayer().getInventory().setLeggings(it);
                    }else if(getMain().API.itemStackManager.isChestplate(it) && codeExecutor.getLinePlayer().getInventory().getChestplate() == null){
                        codeExecutor.getLinePlayer().getInventory().setChestplate(it);
                    }else if(getMain().API.itemStackManager.isHelmet(it) && codeExecutor.getLinePlayer().getInventory().getHelmet() == null){
                        codeExecutor.getLinePlayer().getInventory().setHelmet(it);
                    }else {
                        codeExecutor.getLinePlayer().getInventory().addItem(it);
                    }
                }
            }
        }

        codeExecutor.moveHead();
    }


    private void loadInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "A qui donner les items");

        if(this.all){
            inv.setItem(4, getMain().API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "Tous les joueurs", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }else{
            inv.setItem(4, getMain().API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "Joueur de la ligne", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }

        p.openInventory(inv);
    }

}

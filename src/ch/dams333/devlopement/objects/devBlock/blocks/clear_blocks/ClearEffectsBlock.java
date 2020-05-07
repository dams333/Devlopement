package ch.dams333.devlopement.objects.devBlock.blocks.clear_blocks;

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
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ClearEffectsBlock extends DevBlock{

    private boolean all;

    public ClearEffectsBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.CLEAR_EFFECTS);
        all = true;
    }

    public ClearEffectsBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.CLEAR_EFFECTS);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        all = config.getBoolean("All");

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CLEAR_EFFECTS;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/clear/clear_effects/" + super.getUuid().toString() + ".yml");
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
            e.setCancelled(true);
            getMain().enterModif(e.getPlayer(), this);
            this.loadInventory(e.getPlayer());
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

        if(all){
            for(Player p : Bukkit.getOnlinePlayers()){
               for(PotionEffect potionEffect : p.getActivePotionEffects()){
                   p.removePotionEffect(potionEffect.getType());
               }
            }
        }else{
            for(PotionEffect potionEffect : codeExecutor.getLinePlayer().getActivePotionEffects()){
                codeExecutor.getLinePlayer().removePotionEffect(potionEffect.getType());
            }
        }

        codeExecutor.moveHead();
    }


    private void loadInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Qui affecter");

        if(this.all){
            inv.setItem(4, getMain().API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "Tous les joueurs", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }else{
            inv.setItem(4, getMain().API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "Joueur de la ligne", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }

        p.openInventory(inv);
    }

}

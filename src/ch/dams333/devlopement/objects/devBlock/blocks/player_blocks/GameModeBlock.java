package ch.dams333.devlopement.objects.devBlock.blocks.player_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GameModeBlock extends DevBlock{

    private boolean all;
    private GameMode gameMode;

    public GameModeBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.GAMEMODE);
        all = true;
        gameMode = GameMode.SURVIVAL;
    }

    public GameModeBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.GAMEMODE);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        all = config.getBoolean("All");
        gameMode = GameMode.valueOf(config.getString("GameMode"));
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GAMEMODE;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/player/gamemode/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        blockConfig.set("All", this.all);
        blockConfig.set("GameMode", gameMode.toString());

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
        if(e.getCurrentItem().getType() == Material.WATER_BUCKET){
            this.gameMode = GameMode.SURVIVAL;
        }
        if(e.getCurrentItem().getType() == Material.LAVA_BUCKET){
            this.gameMode = GameMode.ADVENTURE;
        }
        if(e.getCurrentItem().getType() == Material.MILK_BUCKET){
            this.gameMode = GameMode.CREATIVE;
        }
        if(e.getCurrentItem().getType() == Material.BUCKET){
            this.gameMode = GameMode.SPECTATOR;
        }
        getMain().updateModif(this);
        loadInventory((Player) e.getWhoClicked());
        getMain().enterModif((Player) e.getWhoClicked(), this);
    }

    @Override
    public void execute(CodeExecutor codeExecutor) {

        if(all){
            for(Player p : Bukkit.getOnlinePlayers()){
                p.setGameMode(gameMode);
            }
        }else{
            codeExecutor.getLinePlayer().setGameMode(gameMode);
        }

        codeExecutor.moveHead();
    }


    private void loadInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Choix du mode de jeu");

        ItemStack survie = getMain().API.itemStackManager.create(Material.WATER_BUCKET, ChatColor.GOLD + "Survie", Arrays.asList(ChatColor.RED + "Non séectionné"));
        ItemStack aventure = getMain().API.itemStackManager.create(Material.LAVA_BUCKET, ChatColor.GOLD + "Aventure", Arrays.asList(ChatColor.RED + "Non séectionné"));
        ItemStack crea = getMain().API.itemStackManager.create(Material.MILK_BUCKET, ChatColor.GOLD + "Créatif", Arrays.asList(ChatColor.RED + "Non séectionné"));
        ItemStack spectator = getMain().API.itemStackManager.create(Material.BUCKET, ChatColor.GOLD + "Spectateur", Arrays.asList(ChatColor.RED + "Non séectionné"));

        if (gameMode == GameMode.SURVIVAL) {
            ItemMeta m = survie.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            survie.setItemMeta(m);
        }
        if (gameMode == GameMode.ADVENTURE) {
            ItemMeta m = aventure.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            aventure.setItemMeta(m);
        }
        if (gameMode == GameMode.CREATIVE) {
            ItemMeta m = crea.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            crea.setItemMeta(m);
        }
        if (gameMode == GameMode.SPECTATOR) {
            ItemMeta m = spectator.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            spectator.setItemMeta(m);
        }

        inv.setItem(1, survie);
        inv.setItem(2, aventure);
        inv.setItem(3, crea);
        inv.setItem(4, spectator);

        if(this.all){
            inv.setItem(8, getMain().API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "Tous les joueurs", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }else{
            inv.setItem(8, getMain().API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "Joueur de la ligne", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }

        p.openInventory(inv);
    }

}

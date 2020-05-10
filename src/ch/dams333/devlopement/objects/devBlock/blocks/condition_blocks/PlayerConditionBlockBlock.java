package ch.dams333.devlopement.objects.devBlock.blocks.condition_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class PlayerConditionBlockBlock extends DevBlock{


    private int number;
    private int effect;

    public PlayerConditionBlockBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.PLAYER_CONDITION);
        number = 1;
        effect = 1;
    }

    public PlayerConditionBlockBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.PLAYER_CONDITION);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        number = config.getInt("Number");
        effect = config.getInt("Effect");
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PLAYER_CONDITION;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/condition/player_condition/" + super.getUuid().toString() + ".yml");
        try {
            Files.deleteIfExists(blockFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }

        blockConfig = super.saveLocation(blockConfig, super.getLoc());
        blockConfig.set("Number", number);
        blockConfig.set("Effect", effect);

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
        loadInventory(e.getPlayer());
    }

    @Override
    public void execute(CodeExecutor codeExecutor) {
        int size = 0;
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getGameMode() != GameMode.SPECTATOR){
                size+=1;
            }
        }
        if(effect == 1){
            if(size < number){
                codeExecutor.moveHead();
            }else{
                if(getMain().linksManager.isLinked(this.getUuid())){
                    getMain().linksManager.startLineLinked(this.getUuid(), codeExecutor.getLinePlayer());
                }
            }
        }
        if(effect == 2){
            if(size <= number){
                codeExecutor.moveHead();
            }else{
                if(getMain().linksManager.isLinked(this.getUuid())){
                    getMain().linksManager.startLineLinked(this.getUuid(), codeExecutor.getLinePlayer());
                }
            }
        }
        if(effect == 3){
            if(size == number){
                codeExecutor.moveHead();
            }else{
                if(getMain().linksManager.isLinked(this.getUuid())){
                    getMain().linksManager.startLineLinked(this.getUuid(), codeExecutor.getLinePlayer());
                }
            }
        }
        if(effect == 4){
            if(size >= number){
                codeExecutor.moveHead();
            }else{
                if(getMain().linksManager.isLinked(this.getUuid())){
                    getMain().linksManager.startLineLinked(this.getUuid(), codeExecutor.getLinePlayer());
                }
            }
        }
        if(effect == 5){
            if(size > number){
                codeExecutor.moveHead();
            }else{
                if(getMain().linksManager.isLinked(this.getUuid())){
                    getMain().linksManager.startLineLinked(this.getUuid(), codeExecutor.getLinePlayer());
                }
            }
        }
    }


    @Override
    public void inventoryOn(InventoryClickEvent e) {

        if(e.getCurrentItem().getType() == Material.COMPARATOR){
            this.effect = e.getSlot();
        }
        if(e.getCurrentItem().getType() == Material.PAPER){
            if(e.getAction() == InventoryAction.PICKUP_HALF){
                if(number > 1){
                    number = number - 1;
                }
            }
            if(e.getAction() == InventoryAction.PICKUP_ALL){
                number = number + 1;
            }
        }

        getMain().updateModif(this);
        loadInventory((Player) e.getWhoClicked());
        getMain().enterModif((Player) e.getWhoClicked(), this);
    }

    private void loadInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Tester le nombre de joueurs");

        ItemStack i1 = getMain().API.itemStackManager.create(Material.COMPARATOR, ChatColor.GOLD + "Strictement inférieur à", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        ItemStack i2 = getMain().API.itemStackManager.create(Material.COMPARATOR, ChatColor.GOLD + "Inférieur à", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        ItemStack i3 = getMain().API.itemStackManager.create(Material.COMPARATOR, ChatColor.GOLD + "Egal à", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        ItemStack i4 = getMain().API.itemStackManager.create(Material.COMPARATOR, ChatColor.GOLD + "Supérieur à", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        ItemStack i5 = getMain().API.itemStackManager.create(Material.COMPARATOR, ChatColor.GOLD + "Strictement supérieur à", Arrays.asList(ChatColor.RED + "Non sélectionné"));

        if(this.effect == 1){
            i1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            ItemMeta m = i1.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i1.setItemMeta(m);
        }
        if(this.effect == 2){
            i2.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            ItemMeta m = i2.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i2.setItemMeta(m);
        }
        if(this.effect == 3){
            i3.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            ItemMeta m = i3.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i3.setItemMeta(m);
        }
        if(this.effect == 4){
            i4.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            ItemMeta m = i4.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i4.setItemMeta(m);
        }
        if(this.effect == 5){
            i5.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            ItemMeta m = i5.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            i5.setItemMeta(m);
        }

        inv.setItem(1, i1);
        inv.setItem(2, i2);
        inv.setItem(3, i3);
        inv.setItem(4, i4);
        inv.setItem(5, i5);

        inv.setItem(8, getMain().API.itemStackManager.create(Material.PAPER, ChatColor.GOLD + "Nombre: " + number, Arrays.asList(ChatColor.GRAY + "Clique gauche pour augmenter", ChatColor.GRAY + "Clique droit pour diminuer")));

        p.openInventory(inv);
    }

}

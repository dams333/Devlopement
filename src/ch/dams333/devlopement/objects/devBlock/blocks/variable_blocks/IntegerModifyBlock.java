package ch.dams333.devlopement.objects.devBlock.blocks.variable_blocks;

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
import java.util.UUID;

public class IntegerModifyBlock extends DevBlock{

    private UUID var;
    private int action;
    private boolean all;
    private int modifyer;

    public IntegerModifyBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.INTEGER_MODIFY_VARIABLE);
        this.action = 1;
        this.modifyer = 0;
        this.all = true;
    }

    public IntegerModifyBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.INTEGER_MODIFY_VARIABLE);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        this.action = config.getInt("Action");
        this.all = config.getBoolean("All");
        this.modifyer = config.getInt("Modifyer");

        if(config.getKeys(false).contains("Variable")){
            this.var = UUID.fromString(config.getString("Variable"));
        }

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.INTEGER_MODIFY_VARIABLE;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/variables/integer_modify/" + super.getUuid().toString() + ".yml");
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

        blockConfig.set("Action", this.action);
        blockConfig.set("Modifyer", this.modifyer);
        blockConfig.set("All", this.all);
        if(this.var != null){
            blockConfig.set("Variable", this.var.toString());
        }


        try {
            blockConfig.save(blockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickOn(PlayerInteractEvent e) {
        if(this.var == null){
            e.setCancelled(true);
            getMain().enterModif(e.getPlayer(), this);
            getMain().variablesManager.loadIntegersInventory(e.getPlayer(), 1, this.var);
        }else{
            this.loadInventory(e.getPlayer());
            e.setCancelled(true);
            getMain().enterModif(e.getPlayer(), this);
        }
    }

    private void loadInventory(Player p) {

        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Choix de l'action");

        if(!getMain().variablesManager.isGlobal(this.var)){
            if(all){
                inv.setItem(8, getMain().API.itemStackManager.create(Material.EMERALD, ChatColor.GOLD + "Changer la valeur de tous les joueurs", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
            }else{
                inv.setItem(8, getMain().API.itemStackManager.create(Material.REDSTONE, ChatColor.GOLD + "Changer la valeur du joueur de la ligne", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
            }
        }

        ItemStack a1 = getMain().API.itemStackManager.create(Material.REPEATER, ChatColor.GOLD + "Ajouter", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        ItemStack a2 = getMain().API.itemStackManager.create(Material.REPEATER, ChatColor.GOLD + "Définir sur", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        ItemStack a3 = getMain().API.itemStackManager.create(Material.REPEATER, ChatColor.GOLD + "Retirer", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        if(action == 1){
            ItemMeta m = a1.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            a1.setItemMeta(m);
            a1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        if(action == 2){
            ItemMeta m = a2.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            a2.setItemMeta(m);
            a2.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }
        if(action == 3){
            ItemMeta m = a3.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            a3.setItemMeta(m);
            a3.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }

        inv.setItem(1, a1);
        inv.setItem(2, a2);
        inv.setItem(3, a3);

        inv.setItem(5, getMain().API.itemStackManager.create(Material.LEVER, ChatColor.GOLD + "Modificateur: " + modifyer, Arrays.asList(ChatColor.GRAY + "Clique gauche pour augmenter", ChatColor.GRAY + "Clique droit pour diminuer")));

        p.openInventory(inv);

    }

    @Override
    public void execute(CodeExecutor codeExecutor) {
        if(this.action == 1){
            getMain().variablesManager.addInteger(var, modifyer, all, codeExecutor.getLinePlayer());
        }
        if(this.action == 2){
            getMain().variablesManager.setInteger(var, modifyer, all, codeExecutor.getLinePlayer());
        }
        if(this.action == 3){
            getMain().variablesManager.removeInteger(var, modifyer, all, codeExecutor.getLinePlayer());
        }

        codeExecutor.moveHead();
    }

    @Override
    public void inventoryOn(InventoryClickEvent e) {
        if(e.getView().getTitle().equals(ChatColor.GOLD + "Choix de l'action")){
            if(e.getCurrentItem().getType() == Material.REDSTONE){
                this.all = true;
            }
            if(e.getCurrentItem().getType() == Material.EMERALD){
                this.all = false;
            }
            if(e.getCurrentItem().getType() == Material.REPEATER){
                this.action = e.getSlot();
            }
            if(e.getCurrentItem().getType() == Material.LEVER){
                if(e.getAction() == InventoryAction.PICKUP_HALF){
                    this.modifyer--;
                }
                if(e.getAction() == InventoryAction.PICKUP_ALL){
                    this.modifyer++;
                }
            }


            getMain().updateModif(this);
            loadInventory((Player) e.getWhoClicked());
            getMain().enterModif((Player) e.getWhoClicked(), this);
        }else{
            ItemStack it = e.getCurrentItem();
            int page = Integer.parseInt(e.getView().getTitle().replaceAll(ChatColor.GOLD + "Choix de la variable > Page ", ""));
            if(it.getType() == Material.PAPER){
                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                var = UUID.fromString(tag.getString("uuid"));
            }else if(it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page précédente")){
                if(page > 1){
                    page = page - 1;
                    getMain().updateModif(this);
                    getMain().variablesManager.loadIntegersInventory((Player) e.getWhoClicked(), page, this.var);
                    getMain().enterModif((Player) e.getWhoClicked(), this);
                }
                return;
            }else if(it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page suivante")){
                int messagePerPage = 5 * 9;
                int start = page * messagePerPage;
                start++;
                if((getMain().variablesManager.getPlayerIntegers((Player) e.getWhoClicked()).size()) >= start){
                    page = page + 1;
                    getMain().updateModif(this);
                    getMain().variablesManager.loadIntegersInventory((Player) e.getWhoClicked(), page, this.var);
                    getMain().enterModif((Player) e.getWhoClicked(), this);
                }
                return;
            }
            getMain().updateModif(this);
            loadInventory((Player) e.getWhoClicked());
            getMain().enterModif((Player) e.getWhoClicked(), this);
        }
    }

}
package ch.dams333.devlopement.objects.devBlock.blocks.condition_blocks;

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

public class BooleanConditionBlock extends DevBlock{

    private UUID var;
    private int action;
    private boolean all;

    public BooleanConditionBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.BOOLEAN_TEST_VARIABLE);
        this.action = 1;
        this.all = true;
    }

    public BooleanConditionBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.BOOLEAN_TEST_VARIABLE);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        this.action = config.getInt("Action");
        this.all = config.getBoolean("All");

        if(config.getKeys(false).contains("Variable")){
            this.var = UUID.fromString(config.getString("Variable"));
        }

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BOOLEAN_TEST_VARIABLE;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/condition/boolean_test/" + super.getUuid().toString() + ".yml");
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
            getMain().variablesManager.loadBooleansInventroy(e.getPlayer(), 1, this.var);
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
                inv.setItem(8, getMain().API.itemStackManager.create(Material.EMERALD, ChatColor.GOLD + "Tester la valeur de tous les joueurs", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
            }else{
                inv.setItem(8, getMain().API.itemStackManager.create(Material.REDSTONE, ChatColor.GOLD + "Tester la valeur du joueur de la ligne", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
            }
        }

        ItemStack a1 = getMain().API.itemStackManager.create(Material.EMERALD_BLOCK, ChatColor.GOLD + "La valeur doit être sur 'true'", Arrays.asList(ChatColor.RED + "Non sélectionné"));
        ItemStack a3 = getMain().API.itemStackManager.create(Material.REDSTONE_BLOCK, ChatColor.GOLD + "La valeur doit être sur 'false'", Arrays.asList(ChatColor.RED + "Non sélectionné"));

        if(action == 1){
            ItemMeta m = a1.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            a1.setItemMeta(m);
            a1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }

        if(action == 2){
            ItemMeta m = a3.getItemMeta();
            m.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            a3.setItemMeta(m);
            a3.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        }

        inv.setItem(2, a1);
        inv.setItem(5, a3);

        p.openInventory(inv);

    }

    @Override
    public void execute(CodeExecutor codeExecutor) {
        if(getMain().variablesManager.isBooleanRespondToAction(var, all, action, codeExecutor.getLinePlayer())){
            codeExecutor.moveHead();
        }else{
            if(getMain().linksManager.isLinked(this.getUuid())){
                getMain().linksManager.startLineLinked(this.getUuid(), codeExecutor.getLinePlayer());
            }
        }
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
            if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
                this.action = 1;
            }
            if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
                this.action = 2;
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
                    getMain().variablesManager.loadBooleansInventroy((Player) e.getWhoClicked(), page, this.var);
                    getMain().enterModif((Player) e.getWhoClicked(), this);
                }
                return;
            }else if(it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page suivante")){
                int messagePerPage = 5 * 9;
                int start = page * messagePerPage;
                start++;
                if((getMain().variablesManager.getPlayerBooleans((Player) e.getWhoClicked()).size()) >= start){
                    page = page + 1;
                    getMain().updateModif(this);
                    getMain().variablesManager.loadBooleansInventroy((Player) e.getWhoClicked(), page, this.var);
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

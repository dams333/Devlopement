package ch.dams333.devlopement.objects.devBlock.blocks.start_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import ch.dams333.devlopement.objects.start_events.EventStart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
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
import java.util.ArrayList;
import java.util.List;

public class EventStartBlock extends DevBlock{

    private EventStart eventStart;

    public EventStartBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.EVENT_START);
        this.eventStart = EventStart.NULL;
    }

    public EventStartBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.EVENT_START);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.eventStart = EventStart.fromString(config.getString("Event"));
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EVENT_START;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/start/event_start/" + super.getUuid().toString() + ".yml");
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

        blockConfig.set("Event", eventStart.toString());

        try {
            blockConfig.save(blockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickOn(PlayerInteractEvent e) {
        getMain().enterModif(e.getPlayer(), this);
        loadInventory(e.getPlayer());
    }

    @Override
    public void execute(CodeExecutor codeExecutor) {

    }

    public EventStart getEventStart() {
        return eventStart;
    }

    @Override
    public void inventoryOn(InventoryClickEvent e) {

        if(e.getCurrentItem().getType() == Material.PAPER){
            String event = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§6", "");
            this.eventStart = EventStart.fromName(event);

        }

        getMain().updateModif(this);
        loadInventory((Player) e.getWhoClicked());
        getMain().enterModif((Player) e.getWhoClicked(), this);
    }

    private void loadInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Choisir un évennement");

        for(EventStart eventStart : EventStart.values()){
            if(!eventStart.toName().equals("")){
                List<String> lore = new ArrayList<>();
                if(eventStart.toString().equals(this.eventStart.toString())){
                    lore.add(ChatColor.GREEN + "Sélectionné");
                }else{
                    lore.add(ChatColor.RED + "Non sélectionné");
                    lore.add(ChatColor.GRAY + "Clique droit pour choisir");
                }
                ItemStack it = getMain().API.itemStackManager.create(Material.PAPER, ChatColor.GOLD + eventStart.toName(), lore);
                if(eventStart.toString().equals(this.eventStart.toString())){
                    it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemMeta itM = it.getItemMeta();
                    itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    it.setItemMeta(itM);
                }
                inv.addItem(it);
            }
        }

        p.openInventory(inv);
    }

}

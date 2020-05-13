package ch.dams333.devlopement.objects.devBlock.blocks.timer_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
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
import java.util.UUID;

public class StartTimerBlock extends DevBlock{

    private UUID timerUUID;

    public StartTimerBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.START_TIMER);
        timerUUID = null;
    }

    public StartTimerBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.START_TIMER);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(config.getKeys(false).contains("Timer")){
            this.timerUUID = UUID.fromString(config.getString("Timer"));
        }else{
            timerUUID = null;
        }

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.START_TIMER;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/timer/start_timer/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        if(timerUUID != null){
            blockConfig.set("Timer", timerUUID.toString());
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
            this.loadInventory(e.getPlayer(), 1);
    }


    @Override
    public void inventoryOn(InventoryClickEvent e) {

            ItemStack it = e.getCurrentItem();
            int page = Integer.parseInt(e.getView().getTitle().replaceAll(ChatColor.GOLD + "Choix du Timer > Page ", ""));
            if(it.getType() == Material.CLOCK){
                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                timerUUID = UUID.fromString(tag.getString("uuid"));
            }else if(it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page précédente")){
                if(page > 1){
                    page = page - 1;
                }
            }else if(it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page suivante")){
                int messagePerPage = 5 * 9;
                int start = page * messagePerPage;
                start++;
                if((getMain().gameTimerManager.getPlayerTimers(((Player) e.getWhoClicked()).getUniqueId()).size()) >= start){
                    page = page + 1;
                }
            }
            getMain().updateModif(this);
            loadInventory((Player) e.getWhoClicked(), page);
            getMain().enterModif((Player) e.getWhoClicked(), this);

    }



    private void loadInventory(Player p, int page){
        getMain().gameTimerManager.loadTimersInventory(p, page, this.timerUUID);
    }


    @Override
    public void execute(CodeExecutor codeExecutor) {

        if(!getMain().gameTimerManager.isTimerStarted(timerUUID)){
            getMain().gameTimerManager.startTimer(timerUUID);
        }

        codeExecutor.moveHead();
    }


}

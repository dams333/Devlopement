package ch.dams333.devlopement.objects.timer;

import ch.dams333.devlopement.Devlopement;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GameTimerManager {

    private Devlopement main;

    private List<GameTimer> timers;

    public GameTimerManager(Devlopement main) {
        this.main = main;
        this.timers = new ArrayList<>();
        this.gameTimerTasks = new ArrayList<>();
    }

    public void deserialize() {
        File timerFile = new File(main.getDataFolder(), "timer/timers.yml");
        YamlConfiguration timerConfig = YamlConfiguration.loadConfiguration(timerFile);

        for(String timerUUId : timerConfig.getKeys(false)){
            ConfigurationSection sec = timerConfig.getConfigurationSection(timerUUId);
            String name = sec.getString("Name");
            UUID player = UUID.fromString(sec.getString("Player"));
            this.timers.add(new GameTimer(UUID.fromString(timerUUId), player, name));
        }

    }

    public void serialize() {
        File timerFile = new File(main.getDataFolder(), "timer/timers.yml");
        YamlConfiguration timerConfig = YamlConfiguration.loadConfiguration(timerFile);

        for(GameTimer gameTimer : timers){
            ConfigurationSection sec = timerConfig.createSection(gameTimer.getUuid().toString());
            sec.set("Name", gameTimer.getName());
            sec.set("Player", gameTimer.getOwner().toString());
        }

        try {
            timerConfig.save(timerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GameTimer> getPlayerTimers(UUID p){
        List<GameTimer> timers = new ArrayList<>();
        for(GameTimer timer : this.timers){
            if(timer.getOwner().equals(p)){
                timers.add(timer);
            }
        }
        return timers;
    }

    public boolean playerHasTimer(UUID p, String name) {
        for(GameTimer timer : getPlayerTimers(p)){
            if(timer.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public void addPlayerTimer(Player p, String name) {
        this.timers.add(new GameTimer(p.getUniqueId(), name));
    }

    public void removePlayerTimer(Player p, String name) {
        for(GameTimer gameTimer : this.getPlayerTimers(p.getUniqueId())){
            if(gameTimer.getName().equalsIgnoreCase(name)){
                this.timers.remove(gameTimer);
                return;
            }
        }
    }

    public GameTimer getPlayerTimerByPlayerAndName(UUID player, String timerName) {
        for(GameTimer gameTimer : this.getPlayerTimers(player)){
            if(gameTimer.getName().equalsIgnoreCase(timerName)){
                return gameTimer;
            }
        }
        return null;
    }


    public void loadTimersInventory(Player p, int page, UUID selected){

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Choix du Timer > Page " + page);

        inv.setItem(45, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page précédente"));
        inv.setItem(53, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page suivante"));


        int messagePerPage = 5 * 9;

        int start = (page - 1) * messagePerPage;


        for(int i = 0; i < messagePerPage; i++){

            if((getPlayerTimers(p.getUniqueId()).size() - 1) >= start){
                GameTimer timer = getPlayerTimers(p.getUniqueId()).get(start);
                ItemStack it = main.API.itemStackManager.create(Material.CLOCK, ChatColor.GOLD + timer.getName());
                if(selected != null && timer.getUuid().equals(selected)){
                    it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemMeta itM = it.getItemMeta();
                    itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itM.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
                    it.setItemMeta(itM);
                }

                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                tag.setString("uuid", timer.getUuid().toString());
                stack.setTag(tag);
                it = CraftItemStack.asCraftMirror(stack);

                inv.setItem(i, it);
            }else{
                break;
            }
            start++;
        }

        p.openInventory(inv);

    }

    private List<GameTimerTask> gameTimerTasks;

    public boolean isTimerStarted(UUID timerUUID) {
        for(GameTimerTask task : gameTimerTasks){
            if(task.getTimer().equals(timerUUID)){
                return true;
            }
        }
        return false;
    }

    public void startTimer(UUID timerUUID) {
        GameTimerTask task = new GameTimerTask(main, timerUUID);
        this.gameTimerTasks.add(task);
        task.runTaskTimer(main, 20, 20);
    }

    public void resetTimer(UUID timerUUID) {
        for(GameTimer gameTimer : this.timers){
            if(gameTimer.getUuid().equals(timerUUID)){
                int index = timers.indexOf(gameTimer);
                gameTimer.reset();
                timers.set(index, gameTimer);
                return;
            }
        }
    }

    public void pauseTimer(UUID timerUUID) {
        for(GameTimerTask task : gameTimerTasks){
            if(task.getTimer().equals(timerUUID)){
                gameTimerTasks.remove(task);
                task.cancel();
                return;
            }
        }
    }

    public void addTimeToTimer(UUID timer) {
        for(GameTimer gameTimer : this.timers){
            if(gameTimer.getUuid().equals(timer)){
                int index = timers.indexOf(gameTimer);
                gameTimer.addSecond();
                timers.set(index, gameTimer);
                return;
            }
        }
    }
}

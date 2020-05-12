package ch.dams333.devlopement.objects.messages;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.variables.BooleanVariable;
import ch.dams333.devlopement.objects.variables.IntegerVariable;
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

public class MessagesManager {

    private Devlopement main;
    private List<Message> messages;

    public MessagesManager(Devlopement main) {
        this.main = main;
        messages = new ArrayList<>();
    }

    public void serialize(){

        File messagesFile = new File(main.getDataFolder(), "messages/messages.yml");
        YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        for (String key : messagesConfig.getKeys(false)) {
            messagesConfig.set(key, null);
        }

        for(Message message : this.messages){
            ConfigurationSection sec = messagesConfig.createSection(message.getUuid().toString());
            sec.set("Player", message.getPlayer().toString());
            sec.set("Message", message.getMessage());
        }

        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void deserialize(){

        File messagesFile = new File(main.getDataFolder(), "messages/messages.yml");
        YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

        for(String messageUUID : messagesConfig.getKeys(false)){
            ConfigurationSection sec = messagesConfig.getConfigurationSection(messageUUID);
            UUID playerUUID = UUID.fromString(sec.getString("Player"));
            String message = sec.getString("Message");
            this.messages.add(new Message(UUID.fromString(messageUUID), playerUUID, message));
        }

    }

    public String getMessage(UUID uuid, Player linePlayer){
        for(Message message : this.messages){
            if(message.getUuid().equals(uuid)){
                String msg = message.getMessage().replaceAll("&", "§").replaceAll("%%PLAYER%%", linePlayer.getName());

                StringBuilder sb = new StringBuilder();

                for(String arg : msg.split(" ")){
                    String toAdd = arg;
                    if(arg.startsWith("%%") && arg.endsWith("%%")){
                        toAdd = arg.replaceAll("%%", "");
                        String[] arg2 = toAdd.split(":");
                        if(arg2[0].equalsIgnoreCase("var")){
                            String varName = arg2[1];
                            if(main.variablesManager.isVariableABooleanByNameAndPlayer(varName, message.getPlayer())){
                                BooleanVariable var = main.variablesManager.getBooleanByNameAndPlayer(varName, message.getPlayer());
                                if(var.isGlobal()){
                                    toAdd = String.valueOf(var.getGloabalValue());
                                }else{
                                    toAdd = String.valueOf(var.getPlayerValue(linePlayer));
                                }
                            }
                            if(main.variablesManager.isVariableAIntegerByNameAndPlayer(varName, message.getPlayer())){
                                IntegerVariable var = main.variablesManager.getIntegerByNameAndPlayer(varName, message.getPlayer());
                                if(var.isGlobal()){
                                    toAdd = String.valueOf(var.getGloabalValue());
                                }else{
                                    toAdd = String.valueOf(var.getPlayerValue(linePlayer));
                                }
                            }
                        }
                        if(arg2[0].equalsIgnoreCase("timer")){
                            String timerName = arg2[1];
                            String timerFormat = arg2[2];
                            if(main.gameTimerManager.playerHasTimer(message.getPlayer(), timerName)){
                                toAdd = main.gameTimerManager.getPlayerTimerByPlayerAndName(message.getPlayer(), timerName).toText(timerFormat);
                            }
                        }
                    }

                    sb.append(toAdd + " ");
                }

                return sb.toString();
            }
        }
        return "";
    }

    public List<Message> getPlayerMessages(Player p){
        List<Message> messages = new ArrayList<>();
        for(Message message : this.messages){
            if(message.getPlayer().equals(p.getUniqueId())){
                messages.add(message);
            }
        }
        return messages;
    }

    public void addMessage(Player p, String string) {
        this.messages.add(new Message(p.getUniqueId(), string));
    }

    public boolean isMessageAtIndex(Player p, int index) {
        return (getPlayerMessages(p).size()) >= index;
    }

    public void removeMessage(Player p, int index) {
        List<Message> messages = getPlayerMessages(p);
        index = index - 1;
        Message message = messages.get(index);
        for(Message msg : this.messages){
            if(msg.getUuid().equals(message.getUuid())){
                this.messages.remove(msg);
                return;
            }
        }
    }

    public void loadMessagesInventory(Player p, int page, UUID selected){

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Choix du message > Page " + page);

        inv.setItem(45, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page précédente"));
        inv.setItem(53, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page suivante"));


        int messagePerPage = 5 * 9;

        int start = (page - 1) * messagePerPage;


        for(int i = 0; i < messagePerPage; i++){

            if((getPlayerMessages(p).size() - 1) >= start){
                Message message = getPlayerMessages(p).get(start);
                ItemStack it = main.API.itemStackManager.create(Material.PAPER, getMessage(message.getUuid(), p));
                if(selected != null && message.getUuid().equals(selected)){
                    it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemMeta itM = it.getItemMeta();
                    itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itM.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
                    it.setItemMeta(itM);
                }

                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                tag.setString("uuid", message.getUuid().toString());
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
}

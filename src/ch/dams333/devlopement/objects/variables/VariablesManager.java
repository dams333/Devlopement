package ch.dams333.devlopement.objects.variables;

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
import java.util.*;

public class VariablesManager {

    private Devlopement main;

    private List<BooleanVariable> booleanVariables;
    private List<IntegerVariable> integerVariables;

    public VariablesManager(Devlopement main) {
        this.main = main;
        this.booleanVariables = new ArrayList<>();
        this.integerVariables = new ArrayList<>();
    }

    public void deserialize() {

        File boolFile = new File(main.getDataFolder(), "variables/booleans.yml");
        YamlConfiguration boolConfig = YamlConfiguration.loadConfiguration(boolFile);
        File intFile = new File(main.getDataFolder(), "variables/integers.yml");
        YamlConfiguration intConfig = YamlConfiguration.loadConfiguration(intFile);

        for(String uuid : boolConfig.getKeys(false)){
            ConfigurationSection sec = boolConfig.getConfigurationSection(uuid);
            UUID owner = UUID.fromString(sec.getString("Owner"));
            String name = sec.getString("Name");
            Boolean global = sec.getBoolean("isGlobal");
            Boolean defaultValue = sec.getBoolean("Default value");
            Boolean globalValue = sec.getBoolean("Global value");
            Map<UUID, Boolean> values = new HashMap<>();
            ConfigurationSection s = sec.getConfigurationSection("Values");
            for(String pUUId : s.getKeys(false)){
                values.put(UUID.fromString(pUUId), s.getBoolean(pUUId));
            }
            this.booleanVariables.add(new BooleanVariable(UUID.fromString(uuid), owner, name, global, defaultValue, globalValue, values));
        }

        for(String uuid : intConfig.getKeys(false)){
            ConfigurationSection sec = intConfig.getConfigurationSection(uuid);
            UUID owner = UUID.fromString(sec.getString("Owner"));
            String name = sec.getString("Name");
            Boolean global = sec.getBoolean("isGlobal");
            Integer defaultValue = sec.getInt("Default value");
            Integer globalValue = sec.getInt("Global value");
            Map<UUID, Integer> values = new HashMap<>();
            ConfigurationSection s = sec.getConfigurationSection("Values");
            for(String pUUId : s.getKeys(false)){
                values.put(UUID.fromString(pUUId), s.getInt(pUUId));
            }
            this.integerVariables.add(new IntegerVariable(UUID.fromString(uuid), owner, name, global, defaultValue, globalValue, values));
        }

    }

    public void serialize() {

        File boolFile = new File(main.getDataFolder(), "variables/booleans.yml");
        YamlConfiguration boolConfig = YamlConfiguration.loadConfiguration(boolFile);
        File intFile = new File(main.getDataFolder(), "variables/integers.yml");
        YamlConfiguration intConfig = YamlConfiguration.loadConfiguration(intFile);


        for(BooleanVariable var : this.booleanVariables){
            ConfigurationSection sec = boolConfig.createSection(var.getUuid().toString());
            sec.set("Owner", var.getOwner().toString());
            sec.set("Name", var.getName());
            sec.set("isGlobal", var.isGlobal());
            sec.set("Default value", var.getDefaultValue());
            sec.set("Global value", var.getGloabalValue());
            ConfigurationSection s = sec.createSection("Values");
            for(UUID pUUId : var.getValues().keySet()){
                s.set(pUUId.toString(), var.getValues().get(pUUId));
            }
        }


        for(IntegerVariable var : this.integerVariables){
            ConfigurationSection sec = intConfig.createSection(var.getUuid().toString());
            sec.set("Owner", var.getOwner().toString());
            sec.set("Name", var.getName());
            sec.set("isGlobal", var.isGlobal());
            sec.set("Default value", var.getDefaultValue());
            sec.set("Global value", var.getGloabalValue());
            ConfigurationSection s = sec.createSection("Values");
            for(UUID pUUId : var.getValues().keySet()){
                s.set(pUUId.toString(), var.getValues().get(pUUId));
            }
        }


        try {
            boolConfig.save(boolFile);
            intConfig.save(intFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean hasPlayerVariableByName(Player p, String name) {
        for(BooleanVariable var : this.booleanVariables){
            if(var.getName().equalsIgnoreCase(name) && var.getOwner().equals(p.getUniqueId())){
                return true;
            }
        }
        for(IntegerVariable var : this.integerVariables){
            if(var.getName().equalsIgnoreCase(name) && var.getOwner().equals(p.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    public void createBoolean(Player p, String name, boolean isGlobal, boolean defaultValue) {
        this.booleanVariables.add(new BooleanVariable(p.getUniqueId(), name, isGlobal, defaultValue));
    }

    public void createinteger(Player p, String name, boolean isGlobal, int defaultValue) {
        this.integerVariables.add(new IntegerVariable(p.getUniqueId(), name, isGlobal, defaultValue));
    }

    public void removeVar(Player p, String name) {
        for(BooleanVariable var : this.booleanVariables){
            if(var.getName().equalsIgnoreCase(name) && var.getOwner().equals(p.getUniqueId())){
                this.booleanVariables.remove(var);
                return;
            }
        }
        for(IntegerVariable var : this.integerVariables){
            if(var.getName().equalsIgnoreCase(name) && var.getOwner().equals(p.getUniqueId())){
                this.integerVariables.remove(var);
                return;
            }
        }
    }

    public List<BooleanVariable> getPlayerBooleans(Player p) {
        List<BooleanVariable> vars = new ArrayList<>();
        for(BooleanVariable var : this.booleanVariables){
            if(var.getOwner().equals(p.getUniqueId())){
                vars.add(var);
            }
        }
        return vars;
    }

    public List<IntegerVariable> getPlayerIntegers(Player p) {
        List<IntegerVariable> vars = new ArrayList<>();
        for(IntegerVariable var : this.integerVariables){
            if(var.getOwner().equals(p.getUniqueId())){
                vars.add(var);
            }
        }
        return vars;
    }



    public void loadBooleansInventroy(Player p, int page, UUID selected){

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Choix de la variable > Page " + page);

        inv.setItem(45, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page précédente"));
        inv.setItem(53, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page suivante"));


        int messagePerPage = 5 * 9;

        int start = (page - 1) * messagePerPage;


        for(int i = 0; i < messagePerPage; i++){

            if((getPlayerBooleans(p).size() - 1) >= start){
                BooleanVariable var = getPlayerBooleans(p).get(start);
                ItemStack it = main.API.itemStackManager.create(Material.PAPER, ChatColor.GOLD + var.getName());
                if(selected != null && var.getUuid().equals(selected)){
                    it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemMeta itM = it.getItemMeta();
                    itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itM.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
                    it.setItemMeta(itM);
                }

                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                tag.setString("uuid", var.getUuid().toString());
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

    public void loadIntegersInventory(Player p, int page, UUID selected){

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Choix de la variable > Page " + page);

        inv.setItem(45, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page précédente"));
        inv.setItem(53, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page suivante"));


        int messagePerPage = 5 * 9;

        int start = (page - 1) * messagePerPage;


        for(int i = 0; i < messagePerPage; i++){

            if((getPlayerIntegers(p).size() - 1) >= start){
                IntegerVariable var = getPlayerIntegers(p).get(start);
                ItemStack it = main.API.itemStackManager.create(Material.PAPER, ChatColor.GOLD + var.getName());
                if(selected != null && var.getUuid().equals(selected)){
                    it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemMeta itM = it.getItemMeta();
                    itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itM.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
                    it.setItemMeta(itM);
                }

                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                tag.setString("uuid", var.getUuid().toString());
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

    public boolean isGlobal(UUID var) {
        for(BooleanVariable booleanVariable : this.booleanVariables){
            if(var.equals(booleanVariable.getUuid())){
                return booleanVariable.isGlobal();
            }
        }
        for(IntegerVariable integerVariable : this.integerVariables){
            if(var.equals(integerVariable.getUuid())){
                return integerVariable.isGlobal();
            }
        }
        return false;
    }

    public void setBooleanTrue(UUID varID, boolean all, Player linePlayer) {
        BooleanVariable var = getBooleanVariableByUUID(varID);
        int index = this.booleanVariables.indexOf(var);

        if(var.isGlobal()){
            var.setGloabalValue(true);
        }else{
            if(all){
                for(Player p : Bukkit.getOnlinePlayers()){
                    var.setPlayerValue(p, true);
                }
            }else{
                var.setPlayerValue(linePlayer, true);
            }
        }

        this.booleanVariables.set(index, var);
    }

    public void switchBoolean(UUID varID, boolean all, Player linePlayer) {
        BooleanVariable var = getBooleanVariableByUUID(varID);
        int index = this.booleanVariables.indexOf(var);

        if(var.isGlobal()){
            var.setGloabalValue(!var.getGloabalValue());
        }else{
            if(all){
                for(Player p : Bukkit.getOnlinePlayers()){
                    var.setPlayerValue(p, !var.getPlayerValue(p));
                }
            }else{
                var.setPlayerValue(linePlayer, !var.getPlayerValue(linePlayer));
            }
        }

        this.booleanVariables.set(index, var);
    }

    public void setBooleanFalse(UUID varID, boolean all, Player linePlayer) {
        BooleanVariable var = getBooleanVariableByUUID(varID);
        int index = this.booleanVariables.indexOf(var);

        if(var.isGlobal()){
            var.setGloabalValue(false);
        }else{
            if(all){
                for(Player p : Bukkit.getOnlinePlayers()){
                    var.setPlayerValue(p, false);
                }
            }else{
                var.setPlayerValue(linePlayer, false);
            }
        }

        this.booleanVariables.set(index, var);
    }

    public void addInteger(UUID varID, int modifyer, boolean all, Player linePlayer) {
        IntegerVariable var = getIntegerVariableByUUID(varID);
        int index = this.integerVariables.indexOf(var);

        if(var.isGlobal()){
            var.setGloabalValue(var.getGloabalValue() + modifyer);
        }else{
            if(all) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    var.setPlayerValue(p, var.getPlayerValue(p) + modifyer);
                }
            }else{
                var.setPlayerValue(linePlayer, var.getPlayerValue(linePlayer) + modifyer);
            }
        }

        this.integerVariables.set(index, var);
    }

    public void setInteger(UUID varID, int modifyer, boolean all, Player linePlayer) {
        IntegerVariable var = getIntegerVariableByUUID(varID);
        int index = this.integerVariables.indexOf(var);

        if(var.isGlobal()){
            var.setGloabalValue(modifyer);
        }else{
            if(all) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    var.setPlayerValue(p, modifyer);
                }
            }else{
                var.setPlayerValue(linePlayer, modifyer);
            }
        }

        this.integerVariables.set(index, var);
    }

    public void removeInteger(UUID varID, int modifyer, boolean all, Player linePlayer) {
        IntegerVariable var = getIntegerVariableByUUID(varID);
        int index = this.integerVariables.indexOf(var);

        if(var.isGlobal()){
            var.setGloabalValue(var.getGloabalValue() - modifyer);
        }else{
            if(all) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    var.setPlayerValue(p, var.getPlayerValue(p) - modifyer);
                }
            }else{
                var.setPlayerValue(linePlayer, var.getPlayerValue(linePlayer) - modifyer);
            }
        }

        this.integerVariables.set(index, var);
    }

    private IntegerVariable getIntegerVariableByUUID(UUID varID) {
        for(IntegerVariable integerVariable : this.integerVariables){
            if(integerVariable.getUuid().equals(varID)){
                return integerVariable;
            }
        }
        return null;
    }


    private BooleanVariable getBooleanVariableByUUID(UUID varID) {
        for(BooleanVariable booleanVariable : this.booleanVariables){
            if(booleanVariable.getUuid().equals(varID)){
                return booleanVariable;
            }
        }
        return null;
    }

    public boolean isVariableABooleanByNameAndPlayer(String varName, UUID player) {
        for(BooleanVariable var : this.booleanVariables){
            if(var.getName().equalsIgnoreCase(varName) && var.getOwner().equals(player)){
                return true;
            }
        }
        return false;
    }

    public boolean isVariableAIntegerByNameAndPlayer(String varName, UUID player) {
        for(IntegerVariable var : this.integerVariables){
            if(var.getName().equalsIgnoreCase(varName) && var.getOwner().equals(player)){
                return true;
            }
        }
        return false;
    }

    public BooleanVariable getBooleanByNameAndPlayer(String varName, UUID player) {
        for(BooleanVariable var : this.booleanVariables){
            if(var.getName().equalsIgnoreCase(varName) && var.getOwner().equals(player)){
                return var;
            }
        }
        return null;
    }

    public IntegerVariable getIntegerByNameAndPlayer(String varName, UUID player) {
        for(IntegerVariable var : this.integerVariables){
            if(var.getName().equalsIgnoreCase(varName) && var.getOwner().equals(player)){
                return var;
            }
        }
        return null;
    }
}

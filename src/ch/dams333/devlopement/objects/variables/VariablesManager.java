package ch.dams333.devlopement.objects.variables;

import ch.dams333.devlopement.Devlopement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
}

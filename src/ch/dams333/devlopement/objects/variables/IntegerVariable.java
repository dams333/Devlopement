package ch.dams333.devlopement.objects.variables;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IntegerVariable {

    private UUID uuid;
    private UUID owner;
    private String name;
    private boolean global;
    private Integer defaultValue;
    private Integer gloabalValue;
    private Map<UUID, Integer> values;

    public IntegerVariable(UUID uuid, UUID owner, String name, boolean global, Integer defaultValue, Integer gloabalValue, Map<UUID, Integer> values) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.global = global;
        this.defaultValue = defaultValue;
        this.gloabalValue = gloabalValue;
        this.values = values;
    }

    public IntegerVariable(UUID owner, String name, boolean global, Integer defaultValue) {
        this.owner = owner;
        this.name = name;
        this.global = global;
        this.defaultValue = defaultValue;
        this.uuid = UUID.randomUUID();
        this.gloabalValue = defaultValue;
        this.values = new HashMap<>();

    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public boolean isGlobal() {
        return global;
    }

    public Integer getGloabalValue() {
        return gloabalValue;
    }


    public void setGloabalValue(Integer gloabalValue) {
        this.gloabalValue = gloabalValue;
    }

    public void setPlayerValue(Player p, Integer value){
        this.values.put(p.getUniqueId(), value);
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    public Map<UUID, Integer> getValues() {
        return values;
    }

    public Integer getPlayerValue(Player p){
        if(!this.values.keySet().contains(p.getUniqueId())){
            this.values.put(p.getUniqueId(), this.defaultValue);
        }
        return this.values.get(p.getUniqueId());
    }
}

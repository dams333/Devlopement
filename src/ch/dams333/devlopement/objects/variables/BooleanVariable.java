package ch.dams333.devlopement.objects.variables;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BooleanVariable {

    private UUID uuid;
    private UUID owner;
    private String name;
    private boolean global;
    private boolean defaultValue;
    private boolean gloabalValue;
    private Map<UUID, Boolean> values;

    public BooleanVariable(UUID uuid, UUID owner, String name, boolean global, boolean defaultValue, boolean gloabalValue, Map<UUID, Boolean> values) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.global = global;
        this.defaultValue = defaultValue;
        this.gloabalValue = gloabalValue;
        this.values = values;
    }

    public BooleanVariable(UUID owner, String name, boolean global, boolean defaultValue) {
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

    public boolean getGloabalValue() {
        return gloabalValue;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public Map<UUID, Boolean> getValues() {
        return values;
    }

    public void setGloabalValue(boolean gloabalValue) {
        this.gloabalValue = gloabalValue;
    }

    public void setPlayerValue(Player p, boolean value){
        this.values.put(p.getUniqueId(), value);
    }

    public boolean getPlayerValue(Player p){
        if(!this.values.keySet().contains(p.getUniqueId())){
            this.values.put(p.getUniqueId(), this.defaultValue);
        }
        return this.values.get(p.getUniqueId());
    }
}

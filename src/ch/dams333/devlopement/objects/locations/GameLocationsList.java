package ch.dams333.devlopement.objects.locations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLocationsList {

    private UUID uuid;
    private UUID player;
    private String name;
    private List<UUID> locations;

    public UUID getUuid() {
        return uuid;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public List<UUID> getLocations() {
        return locations;
    }

    public void removeLocation(UUID uuid){
        if(this.locations.contains(uuid)){
            this.locations.remove(uuid);
        }
    }

    public void addLocation(UUID uuid){
        this.locations.add(uuid);
    }

    public GameLocationsList(UUID player, String name) {
        this.player = player;
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.locations = new ArrayList<>();

    }

    public GameLocationsList(UUID uuid, UUID player, String name, List<UUID> locations) {

        this.uuid = uuid;
        this.player = player;
        this.name = name;
        this.locations = locations;
    }
}

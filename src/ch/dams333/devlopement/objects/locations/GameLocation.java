package ch.dams333.devlopement.objects.locations;

import org.bukkit.Location;

import java.util.UUID;

public class GameLocation {

    private UUID uuid;
    private UUID player;
    private String name;
    private Location location;

    public GameLocation(UUID uuid, UUID player, String name, Location location) {
        this.uuid = uuid;
        this.player = player;
        this.name = name;
        this.location = location;
    }

    public GameLocation(UUID player, String name, Location location) {
        this.player = player;
        this.name = name;
        this.location = location;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}

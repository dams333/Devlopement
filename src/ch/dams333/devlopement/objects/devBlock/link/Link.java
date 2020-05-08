package ch.dams333.devlopement.objects.devBlock.link;

import java.util.UUID;

public class Link {

    private UUID uuid;
    private UUID condition;
    private UUID start;
    private UUID player;

    public void setCondition(UUID condition) {
        this.condition = condition;
    }

    public void setStart(UUID start) {
        this.start = start;
    }

    public UUID getUuid() {

        return uuid;
    }

    public UUID getCondition() {
        return condition;
    }

    public UUID getStart() {
        return start;
    }

    public UUID getPlayer() {
        return player;
    }

    public Link(UUID player) {
        this.player = player;
        this.uuid = UUID.randomUUID();

    }

    public Link(UUID uuid, UUID condition, UUID start, UUID player) {

        this.uuid = uuid;
        this.condition = condition;
        this.start = start;
        this.player = player;
    }
}

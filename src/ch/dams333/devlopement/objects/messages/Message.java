package ch.dams333.devlopement.objects.messages;

import java.util.UUID;

public class Message {

    private UUID uuid;
    private UUID p;
    private String message;

    public UUID getUuid() {
        return uuid;
    }

    public Message(UUID p, String message) {
        this.p = p;
        this.message = message;
        this.uuid = UUID.randomUUID();
    }

    public UUID getPlayer() {
        return p;
    }

    public String getMessage() {
        return message;
    }

    public Message(UUID uuid, UUID p, String message) {

        this.uuid = uuid;
        this.p = p;
        this.message = message;
    }
}

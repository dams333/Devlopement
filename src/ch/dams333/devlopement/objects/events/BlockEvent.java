package ch.dams333.devlopement.objects.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BlockEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

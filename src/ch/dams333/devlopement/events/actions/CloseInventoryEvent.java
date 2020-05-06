package ch.dams333.devlopement.events.actions;

import ch.dams333.devlopement.Devlopement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseInventoryEvent implements Listener {
    Devlopement main;
    public CloseInventoryEvent(Devlopement devlopement) {
        this.main = devlopement;
    }

    @EventHandler
    public void close(InventoryCloseEvent e){
        if(main.isInModif((Player) e.getPlayer())){
            main.closeModif((Player) e.getPlayer());
        }
    }

}

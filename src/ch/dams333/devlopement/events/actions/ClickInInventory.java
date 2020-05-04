package ch.dams333.devlopement.events.actions;

import ch.dams333.devlopement.Devlopement;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickInInventory implements Listener {
    Devlopement main;
    public ClickInInventory(Devlopement devlopement) {
        this.main = devlopement;
    }

    @EventHandler
    public void click(InventoryClickEvent e){
        if(e.getView().getTitle().equals(ChatColor.GOLD + "Items de développement")){
            if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(" ")){
                e.setCancelled(true);
            }
        }
    }

}

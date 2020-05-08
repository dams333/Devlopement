package ch.dams333.devlopement.events.actions;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class LinkEvent implements Listener {
    Devlopement main;
    public LinkEvent(Devlopement devlopement) {
        this.main = devlopement;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void click(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR && e.getPlayer().getInventory().getItemInMainHand().hasItemMeta() && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Créateur de lien")) {
                if (e.getClickedBlock() != null && e.getClickedBlock().getType() != Material.AIR) {
                    if (main.linksManager.isInLink(e.getPlayer())) {
                        Player p = e.getPlayer();
                        e.setCancelled(true);
                        p.closeInventory();
                        if (e.getClickedBlock().getType() == BlockType.ELSE_START.getMaterial()) {
                            if (main.linksManager.needToAddStart(p)) {
                                p.sendMessage(ChatColor.GOLD + "Bloc de démarrage sélectionné");
                                main.linksManager.setStart(p, e.getClickedBlock());
                            } else {
                                p.sendMessage(ChatColor.RED + "Vous avez déjà lié un start");
                            }
                        } else {
                            if (main.linksManager.needToAddCondition(p)) {
                                p.sendMessage(ChatColor.GOLD + "Bloc de condition sélectionné");
                                main.linksManager.setCondition(p, e.getClickedBlock());
                            } else {
                                p.sendMessage(ChatColor.RED + "Vous avez déjà lié un bloc de condition");
                            }
                        }
                    }
                }
            }
        }
    }
}

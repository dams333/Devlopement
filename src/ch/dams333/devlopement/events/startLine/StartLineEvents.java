package ch.dams333.devlopement.events.startLine;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.devBlock.blocks.start_blocks.EventStartBlock;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import ch.dams333.devlopement.objects.start_events.EventStart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class StartLineEvents implements Listener {
    Devlopement main;
    public StartLineEvents(Devlopement devlopement) {
        this.main = devlopement;
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        for(DevBlock devBlock : main.getDevBlocks()){
            if(devBlock.getBlockType() == BlockType.EVENT_START){
                EventStartBlock block = (EventStartBlock) devBlock;
                if(block.getEventStart() == EventStart.JOIN){
                    new CodeExecutor(main, block.getLoc().clone(), e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        for(DevBlock devBlock : main.getDevBlocks()){
            if(devBlock.getBlockType() == BlockType.EVENT_START){
                EventStartBlock block = (EventStartBlock) devBlock;
                if(block.getEventStart() == EventStart.QUIT){
                    new CodeExecutor(main, block.getLoc().clone(), e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent e){

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p.getHealth() <= e.getFinalDamage()){
                e.setCancelled(true);
                this.safeDeath(p);

                Bukkit.broadcastMessage(ChatColor.GRAY + p.getName() + ChatColor.RED + " est mort !");

                for(DevBlock devBlock : main.getDevBlocks()){
                    if(devBlock.getBlockType() == BlockType.EVENT_START){
                        EventStartBlock block = (EventStartBlock) devBlock;
                        if(block.getEventStart() == EventStart.DEATH){
                            new CodeExecutor(main, block.getLoc().clone(), p);
                        }
                    }
                }
            }
        }

    }


    @EventHandler
    public void damageEntity(EntityDamageByEntityEvent e){

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            if(p.getHealth() <= e.getFinalDamage()) {

                Player damager = null;

                if (e.getDamager() instanceof Player) {
                    damager = (Player) e.getDamager();
                }
                if (e.getDamager() instanceof Arrow) {
                    if (((Arrow) e.getDamager()).getShooter() instanceof Player) {
                        damager = (Player) ((Arrow) e.getDamager()).getShooter();
                    }
                }

                if (damager != null) {

                    for (DevBlock devBlock : main.getDevBlocks()) {
                        if (devBlock.getBlockType() == BlockType.EVENT_START) {
                            EventStartBlock block = (EventStartBlock) devBlock;
                            if (block.getEventStart() == EventStart.Kill) {
                                new CodeExecutor(main, block.getLoc().clone(), damager);
                            }
                        }
                    }
                }
            }
        }

    }

    private void safeDeath(Player p) {
        p.setHealth(20);
        p.setFoodLevel(20);
        for(ItemStack it : p.getInventory()){
            if(it != null) {
                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), it);
            }
        }
        p.getInventory().clear();
        if(p.getBedSpawnLocation() != null) {
            p.teleport(p.getBedSpawnLocation());
        }else{
            p.teleport(p.getWorld().getSpawnLocation());
        }
    }

}

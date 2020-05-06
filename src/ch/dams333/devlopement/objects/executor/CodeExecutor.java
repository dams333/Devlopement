package ch.dams333.devlopement.objects.executor;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CodeExecutor {

    private Devlopement main;
    private Location loc;
    private int direction;
    private Player linePlayer;

    public Player getLinePlayer() {
        return linePlayer;
    }

    public CodeExecutor(Devlopement main, Location loc, Player linePlayer) {
        this.main = main;
        this.loc = loc;
        direction = 0;
        this.linePlayer = linePlayer;



        loc.add(0, 1, 0);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
        loc.add(0, -1, 0);

        loc.add(1, 0, 0);
        for(DevBlock devBlock : main.getDevBlocks()){
            if(devBlock.getLoc().getWorld() == loc.getWorld() && devBlock.getLoc().getX() == loc.getX() && devBlock.getLoc().getY() == loc.getY() &&devBlock.getLoc().getZ() == loc.getZ()) {
                direction = 1;
                break;
            }
        }
        loc.add(-1, 0, 0);
        if(direction == 0){
            loc.add(-1, 0, 0);
            for(DevBlock devBlock : main.getDevBlocks()){
                if(devBlock.getLoc().getWorld() == loc.getWorld() && devBlock.getLoc().getX() == loc.getX() && devBlock.getLoc().getY() == loc.getY() &&devBlock.getLoc().getZ() == loc.getZ()) {
                    direction = 2;
                    break;
                }
            }
            loc.add(1, 0, 0);
        }
        if(direction == 0){
            loc.add(0, 0, 1);
            for(DevBlock devBlock : main.getDevBlocks()){
                if(devBlock.getLoc().getWorld() == loc.getWorld() && devBlock.getLoc().getX() == loc.getX() && devBlock.getLoc().getY() == loc.getY() &&devBlock.getLoc().getZ() == loc.getZ()) {
                    direction = 3;
                    break;
                }
            }
            loc.add(0, 0, -1);
        }
        if(direction == 0){
            loc.add(0, 0, -1);
            for(DevBlock devBlock : main.getDevBlocks()){
                if(devBlock.getLoc().getWorld() == loc.getWorld() && devBlock.getLoc().getX() == loc.getX() && devBlock.getLoc().getY() == loc.getY() &&devBlock.getLoc().getZ() == loc.getZ()) {
                    direction = 4;
                    break;
                }
            }
            loc.add(0, 0, 1);
        }

        this.start();
    }

    private void start() {
        this.moveHead();
    }

    public void moveHead() {

        loc.add(0, 1, 0);
        loc.getBlock().setType(Material.AIR);
        loc.add(0, -1, 0);


        if(direction == 1){
            loc.add(1, 0, 0);
        }
        if(direction == 2){
            loc.add(-1, 0, 0);
        }
        if(direction == 3){
            loc.add(0, 0, 1);
        }
        if(direction == 4){
            loc.add(0, 0, -1);
        }

        if(direction != 0) {
            this.executeHead();
        }
    }

    private void executeHead() {
        for(DevBlock devBlock : main.getDevBlocks()){
            if(devBlock.getLoc().getWorld() == loc.getWorld() && devBlock.getLoc().getX() == loc.getX() && devBlock.getLoc().getY() == loc.getY() &&devBlock.getLoc().getZ() == loc.getZ()){
                loc.add(0, 1, 0);
                loc.getBlock().setType(Material.REDSTONE_BLOCK);
                loc.add(0, -1, 0);
                devBlock.execute(this);
                return;
            }
        }

    }
}

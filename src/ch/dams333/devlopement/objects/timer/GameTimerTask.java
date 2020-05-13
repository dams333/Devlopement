package ch.dams333.devlopement.objects.timer;

import ch.dams333.devlopement.Devlopement;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GameTimerTask extends BukkitRunnable {

    private UUID timer;
    private Devlopement main;

    public GameTimerTask(Devlopement main, UUID timer) {
        this.timer = timer;
        this.main = main;
    }

    public UUID getTimer() {
        return timer;
    }

    @Override
    public void run() {
        main.gameTimerManager.addTimeToTimer(timer);
    }
}

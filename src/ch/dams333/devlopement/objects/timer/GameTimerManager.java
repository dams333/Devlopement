package ch.dams333.devlopement.objects.timer;

import ch.dams333.devlopement.Devlopement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameTimerManager {

    private Devlopement main;

    private List<GameTimer> timers;

    public GameTimerManager(Devlopement main) {
        this.main = main;
        this.timers = new ArrayList<>();
    }

    public void deserialize() {
        File timerFile = new File(main.getDataFolder(), "timer/timers.yml");
        YamlConfiguration timerConfig = YamlConfiguration.loadConfiguration(timerFile);

        for(String timerUUId : timerConfig.getKeys(false)){
            ConfigurationSection sec = timerConfig.getConfigurationSection(timerUUId);
            String name = sec.getString(timerUUId);
            UUID player = UUID.fromString(sec.getString("Player"));
            this.timers.add(new GameTimer(UUID.fromString(timerUUId), player, name));
        }

    }

    public void serialize() {
        File timerFile = new File(main.getDataFolder(), "timer/timers.yml");
        YamlConfiguration timerConfig = YamlConfiguration.loadConfiguration(timerFile);

        for(GameTimer gameTimer : timers){
            ConfigurationSection sec = timerConfig.createSection(gameTimer.getUuid().toString());
            sec.set("Name", gameTimer.getName());
            sec.set("Player", gameTimer.getOwner().toString());
        }

        try {
            timerConfig.save(timerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GameTimer> getPlayerTimers(UUID p){
        List<GameTimer> timers = new ArrayList<>();
        for(GameTimer timer : this.timers){
            if(timer.getOwner().equals(p)){
                timers.add(timer);
            }
        }
        return timers;
    }

    public boolean playerHasTimer(UUID p, String name) {
        for(GameTimer timer : getPlayerTimers(p)){
            if(timer.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public void addPlayerTimer(Player p, String name) {
        this.timers.add(new GameTimer(p.getUniqueId(), name));
    }

    public void removePlayerTimer(Player p, String name) {
        for(GameTimer gameTimer : this.getPlayerTimers(p.getUniqueId())){
            if(gameTimer.getName().equalsIgnoreCase(name)){
                this.timers.remove(gameTimer);
                return;
            }
        }
    }

    public GameTimer getPlayerTimerByPlayerAndName(UUID player, String timerName) {
        for(GameTimer gameTimer : this.getPlayerTimers(player)){
            if(gameTimer.getName().equalsIgnoreCase(timerName)){
                return gameTimer;
            }
        }
        return null;
    }
}

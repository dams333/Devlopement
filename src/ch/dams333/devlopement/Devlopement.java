package ch.dams333.devlopement;

import ch.dams333.devlopement.commands.admin.DevModCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Devlopement extends JavaPlugin {

    private List<String> devmod;

    @Override
    public void onEnable(){

        devmod = new ArrayList<>();


        getCommand("devmod").setExecutor(new DevModCommand(this));




        //DEBUG ADD DAMS333 TO DEVMOD
        if(Bukkit.getPlayer("Dams333") != null){
            this.devmod.add("Dams333");
        }
    }

    public boolean isInDevMod(Player p){
        return this.devmod.contains(p.getName());
    }

    public void addIntoDevMod(Player p){
        this.devmod.add(p.getName());
    }

    public void removeFromDevMod(Player p){
        this.devmod.remove(p.getName());
    }

}

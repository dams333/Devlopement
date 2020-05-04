package ch.dams333.devlopement.commands.admin;

import ch.dams333.devlopement.Devlopement;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DevModCommand implements CommandExecutor {
    Devlopement main;
    public DevModCommand(Devlopement devlopement){
        this.main = devlopement;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.isInDevMod(p)){
                main.removeFromDevMod(p);
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Vous avez bien quitté le mode de développement");
            }else{
                main.addIntoDevMod(p);
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Vous avez bien rejoint le mode de développement");
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut être connecté sur le serveur pour faire celà");
        return false;
    }
}

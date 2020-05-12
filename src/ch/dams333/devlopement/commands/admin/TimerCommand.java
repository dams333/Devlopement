package ch.dams333.devlopement.commands.admin;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.timer.GameTimer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimerCommand implements CommandExecutor {
    Devlopement main;
    public TimerCommand(Devlopement devlopement) {
        this.main = devlopement;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("add")){
                    if(args.length == 2){
                        String name = args[1];
                        if(!main.gameTimerManager.playerHasTimer(p.getUniqueId(), name)){
                            main.gameTimerManager.addPlayerTimer(p, name);
                            p.sendMessage(ChatColor.DARK_GREEN + "Timer enregistré avec succès");
                            return true;
                        }
                        p.sendMessage(ChatColor.RED + "Vous avez déjà un timer nommé comme celà");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/timer add <name>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("remove")){
                    if(args.length == 2){
                        String name = args[1];
                        if(main.gameTimerManager.playerHasTimer(p.getUniqueId(), name)){
                            main.gameTimerManager.removePlayerTimer(p, name);
                            p.sendMessage(ChatColor.DARK_GREEN + "Timer supprimé avec succès");
                            return true;
                        }
                        p.sendMessage(ChatColor.RED + "Vous n'avez pas de timer nommé comme celà");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/timer remove <name>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    p.sendMessage(ChatColor.DARK_GREEN + "======= Vos Timers =======");
                    for(GameTimer gameTimer : main.gameTimerManager.getPlayerTimers(p.getUniqueId())){
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + gameTimer.getName());
                    }
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "/timer <add/remove/list>");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Ile faut être connecté sur le serveur pour faire celà");
        return false;
    }
}

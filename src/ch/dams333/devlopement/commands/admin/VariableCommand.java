package ch.dams333.devlopement.commands.admin;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.variables.BooleanVariable;
import ch.dams333.devlopement.objects.variables.IntegerVariable;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VariableCommand implements CommandExecutor {

    private Devlopement main;

    public VariableCommand(Devlopement main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("create")){
                    if(args.length == 5){
                        if(args[1].equalsIgnoreCase("boolean")){
                            String name = args[2];
                            if(args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")){
                                boolean isGlobal = Boolean.valueOf(args[3]);
                                if(args[4].equalsIgnoreCase("true") || args[4].equalsIgnoreCase("false")){
                                    boolean defaultValue = Boolean.valueOf(args[4]);
                                    if(!main.variablesManager.hasPlayerVariableByName(p, name)) {
                                        main.variablesManager.createBoolean(p, name, isGlobal, defaultValue);
                                        p.sendMessage(ChatColor.GREEN + "La variable a bien été enregistrée");
                                        return true;
                                    }
                                    p.sendMessage(ChatColor.RED + "Vous avez déjà une variable appelée comme celà");
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "/variable create <type> <name> <is global> <true/false>");
                                return true;
                            }
                            p.sendMessage(ChatColor.RED + "/variable create <type> <name> <true/false>");
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("integer")){
                            String name = args[2];
                            if(args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false")){
                                boolean isGlobal = Boolean.valueOf(args[3]);
                                if(main.API.isInt(args[4])){
                                    int defaultValue = Integer.parseInt(args[4]);
                                    if(defaultValue >= 0){
                                        if(!main.variablesManager.hasPlayerVariableByName(p, name)) {
                                            main.variablesManager.createinteger(p, name, isGlobal, defaultValue);
                                            p.sendMessage(ChatColor.GREEN + "La variable a bien été enregistrée");
                                            return true;
                                        }
                                        p.sendMessage(ChatColor.RED + "Vous avez déjà une variable appelée comme celà");
                                        return true;
                                    }
                                }
                                p.sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                                return true;
                            }
                            p.sendMessage(ChatColor.RED + "/variable create <type> <name> <true/false>");
                            return true;
                        }
                        p.sendMessage(ChatColor.RED + "/variable create <boolean/integer>");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/variable create <type> <name> <is global> <default value>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("remove")){
                    if(args.length == 2){
                        String name = args[1];
                        if(main.variablesManager.hasPlayerVariableByName(p, name)){
                            main.variablesManager.removeVar(p, name);
                            p.sendMessage(ChatColor.DARK_GREEN + "La variable a bien été supprimée");
                            return true;
                        }
                        p.sendMessage(ChatColor.RED + "Vous n'avez pas de variable nommée ainsi");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/variable remove <name>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    p.sendMessage(ChatColor.DARK_GREEN + "======= Vos variables boolean =======");
                    for(BooleanVariable var : main.variablesManager.getPlayerBooleans(p)){
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + var.getName());
                    }
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.DARK_GREEN + "======= Vos variables integer =======");
                    for(IntegerVariable var : main.variablesManager.getPlayerIntegers(p)){
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + var.getName());
                    }
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "/variable <create/remove/list>");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut être connecté sur le serveur pour faire celà");
        return false;
    }
}

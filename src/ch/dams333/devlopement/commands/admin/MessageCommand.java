package ch.dams333.devlopement.commands.admin;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.messages.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
    private Devlopement main;

    public MessageCommand(Devlopement main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("add")){
                    if(args.length >= 2){
                        StringBuilder sb = new StringBuilder();
                        for(int i = 1; i < args.length; i++){
                            sb.append(args[i] + " ");
                        }
                        main.messagesManager.addMessage(p, sb.toString());
                        p.sendMessage(ChatColor.GREEN + "Message enregistré");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/message add <message>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("remove")){
                    if(args.length == 2){
                        if(main.API.isInt(args[1])){
                            int index = Integer.parseInt(args[1]);
                            if(index > 0){
                                if(main.messagesManager.isMessageAtIndex(p, index)){
                                    main.messagesManager.removeMessage(p, index);
                                    p.sendMessage(ChatColor.GREEN + "Votre message a bien été supprimé");
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "Vous n'avez pas de message à cet index");
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.RED + "Veuillez entrer un nombre valide");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/message remove <index>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    int index = 1;
                    p.sendMessage(ChatColor.DARK_GREEN + "======= Vos messages =======");
                    for(Message message : main.messagesManager.getPlayerMessages(p)){
                        if (message.getPlayer().equals(p.getUniqueId())) {
                            p.sendMessage(ChatColor.LIGHT_PURPLE + "" + index + ") " + ChatColor.RESET + main.messagesManager.getMessage(message.getUuid(), p));
                            index = index + 1;
                        }
                    }
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "/message <add/remove/list>");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Il faut être connecté sur le serveur pour faire celà");
        return false;
    }
}

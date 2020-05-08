package ch.dams333.devlopement.commands.admin;

import ch.dams333.devlopement.Devlopement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkCommand implements CommandExecutor {

    private Devlopement main;

    public LinkCommand(Devlopement main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!main.linksManager.isInLink(p)){
                p.getInventory().addItem(main.API.itemStackManager.create(Material.STICK, ChatColor.GOLD + "Créateur de lien"));
                p.sendMessage(ChatColor.GOLD + "Voilà de quoi lier une condition à un 'si non'");
                main.linksManager.startLink(p);
                return true;
            }
            p.sendMessage(ChatColor.RED + "Vous avez déjà un lien en cours");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être connecté sur le serveur pour faire celà");
        return false;
    }
}

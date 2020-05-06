package ch.dams333.devlopement.commands.admin;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.locations.GameLocation;
import ch.dams333.devlopement.objects.locations.GameLocationsList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LocationCommand implements CommandExecutor {

    private Devlopement main;

    public LocationCommand(Devlopement main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("add")){
                    if(args.length <= 2){
                        String name = "";
                        if(args.length == 2){
                            name = args[1];
                        }else{
                            name = UUID.randomUUID().toString().substring(0, 5);
                        }
                        if(!main.locationsManager.playerHasLocationByName(p, name)){
                            main.locationsManager.createLocation(p, name, p.getLocation());
                            p.sendMessage(ChatColor.GREEN + "Location enregistrée avec succès");
                            return true;
                        }
                        p.sendMessage(ChatColor.RED + "Vous avez déjà une location nommée ainsi");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/location add [name]");
                    return true;
                }
                if(args[0].equalsIgnoreCase("remove")){
                    if(args.length == 2){
                        String name = args[1];
                        if(main.locationsManager.playerHasLocationByName(p, name)){
                            main.locationsManager.removePlayerLoc(p, name);
                            p.sendMessage(ChatColor.GREEN + "Location supprimée avec succès");
                            return true;
                        }
                        p.sendMessage(ChatColor.RED + "Vous n'avez pas de location nommée ainsi");
                        return true;
                    }
                    p.sendMessage(ChatColor.RED + "/location remove <name>");
                    return true;
                }
                if(args[0].equalsIgnoreCase("view")){
                    p.sendMessage(ChatColor.DARK_GREEN + "======= Vos locations =======");
                    for(GameLocation loc : main.locationsManager.getPlayerLocations(p)){
                        p.sendMessage(ChatColor.LIGHT_PURPLE + loc.getName() + ") " + ChatColor.GOLD + "X: " + Math.round(loc.getLocation().getX()) + ChatColor.GRAY + " | " + ChatColor.GOLD + "Y: " + Math.round(loc.getLocation().getY()) + ChatColor.GRAY + " | " + ChatColor.GOLD + "Z: " + Math.round(loc.getLocation().getZ()));
                    }
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    if(args.length >= 2){
                        if(args[1].equalsIgnoreCase("create")){
                            if(args.length <= 3){
                                String name = "";
                                if(args.length == 3){
                                    name = args[2];
                                }else{
                                    name = UUID.randomUUID().toString().substring(0, 5);
                                }
                                if(!main.locationsManager.playerHasLocationsListByName(p, name)){
                                    main.locationsManager.createLocationsList(p, name);
                                    p.sendMessage(ChatColor.GREEN + "Liste de locations enregistrée avec succès");
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "Vous avez déjà une liste de locations nommée ainsi");
                                return true;
                            }
                            p.sendMessage(ChatColor.RED + "/location list create [name]");
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("add")){
                            if(args.length == 4){
                                String listName = args[2];
                                String locationName = args[3];
                                if(main.locationsManager.playerHasLocationsListByName(p, listName)){
                                    if(main.locationsManager.playerHasLocationByName(p, locationName)){
                                        main.locationsManager.addLocationToList(p, listName, locationName);
                                        p.sendMessage(ChatColor.GREEN + "Location ajoutée à la liste avec succès");
                                        return true;
                                    }
                                    p.sendMessage(ChatColor.RED + "Vous n'avez pas de location nommée ainsi");
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "Vous n'avez pas de liste de locations nommée ainsi");
                                return true;
                            }
                            p.sendMessage(ChatColor.RED + "/location list add <list name> <location name>");
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("remove")){
                            if(args.length == 4){
                                String listName = args[2];
                                String locationName = args[3];
                                if(main.locationsManager.playerHasLocationsListByName(p, listName)){
                                    for(UUID locUUID : main.locationsManager.getPlayerLocationsListByName(p, listName).getLocations()) {
                                        if (main.locationsManager.getLocation(locUUID).getName().equalsIgnoreCase(locationName)){
                                            main.locationsManager.removeLocationFromName(p, listName, locationName);
                                            p.sendMessage(ChatColor.GREEN + "Location supprimée de la liste avec succès");
                                            return true;
                                        }
                                    }
                                    p.sendMessage(ChatColor.RED + "Cette liste ne contient pas cette location");
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "Vous n'avez pas de liste de locations nommée ainsi");
                                return true;
                            }
                            p.sendMessage(ChatColor.RED + "/location list remove <list name> <location name>");
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("delete")){
                            if(args.length == 3){
                                String name = args[2];
                                if(main.locationsManager.playerHasLocationsListByName(p, name)){
                                    main.locationsManager.removePlayerLocactionsList(p, name);
                                    p.sendMessage(ChatColor.GREEN + "Liste de locations supprimée avec succès");
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "Vous n'avez pas de liste de locations nommée ainsi");
                                return true;
                            }
                            p.sendMessage(ChatColor.RED + "/location list delete <name>");
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("view")){
                            p.sendMessage(ChatColor.DARK_GREEN + "======= Vos listes de locations =======");
                            for(GameLocationsList list : main.locationsManager.getPlayerLocationsLists(p)){
                                p.sendMessage(ChatColor.LIGHT_PURPLE + list.getName() + ") " + ChatColor.GOLD + list.getLocations().size() + " locations");
                            }
                            return true;
                        }
                        if(args[1].equalsIgnoreCase("infos")){
                            if(args.length == 3){
                                String name = args[2];
                                if(main.locationsManager.playerHasLocationsListByName(p, name)){
                                    p.sendMessage(ChatColor.DARK_GREEN + "======= Locations de la liste " + name + " =======");
                                    for(UUID loc : main.locationsManager.getPlayerLocationsListByName(p, name).getLocations()){
                                        GameLocation l = main.locationsManager.getLocation(loc);
                                        p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + l.getName());
                                    }
                                    return true;
                                }
                                p.sendMessage(ChatColor.RED + "Vous n'avez pas de liste de locations nommée ainsi");
                                return true;
                            }
                            p.sendMessage(ChatColor.RED + "/location list infos <name>");
                            return true;
                        }
                    }
                    p.sendMessage(ChatColor.RED + "/location list <create/delete/add/remove/view/infos>");
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "/location <add/remove/view/list>");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être connecté sur le serveur pour faire celà");
        return false;
    }
}

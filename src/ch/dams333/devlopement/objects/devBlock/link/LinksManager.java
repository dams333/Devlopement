package ch.dams333.devlopement.objects.devBlock.link;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LinksManager {

    private List<Link> inCreation;
    private List<Link> links;

    private Devlopement main;

    public LinksManager(Devlopement main) {
        inCreation = new ArrayList<>();
        links = new ArrayList<>();
        this.main = main;
    }

    public void serialize(){

        File linksFile = new File(main.getDataFolder(), "links/links.yml");
        YamlConfiguration linksConfig = YamlConfiguration.loadConfiguration(linksFile);

        for(Link link : this.links){
            ConfigurationSection sec = linksConfig.createSection(link.getUuid().toString());
            sec.set("Player", link.getPlayer().toString());
            sec.set("Condition", link.getCondition().toString());
            sec.set("Start", link.getStart().toString());
        }

        try {
            linksConfig.save(linksFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize(){

        File linksFile = new File(main.getDataFolder(), "links/links.yml");
        YamlConfiguration linksConfig = YamlConfiguration.loadConfiguration(linksFile);

        for(String linkUUID : linksConfig.getKeys(false)){
            ConfigurationSection sec = linksConfig.getConfigurationSection(linkUUID);
            UUID player = UUID.fromString(sec.getString("Player"));
            UUID condition = UUID.fromString(sec.getString("Condition"));
            UUID start = UUID.fromString(sec.getString("Start"));
            Link link = new Link(UUID.fromString(linkUUID), condition, start, player);
            this.links.add(link);
        }

    }

    public boolean isInLink(Player p) {
        for(Link link : this.inCreation){
            if(link.getPlayer().equals(p.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    public void startLink(Player p) {
        Link link = new Link(p.getUniqueId());
        this.inCreation.add(link);
    }

    public boolean needToAddCondition(Player p) {
        for(Link link : this.inCreation){
            if(link.getPlayer().equals(p.getUniqueId())){
                return link.getCondition() == null;
            }
        }
        return false;
    }

    public boolean needToAddStart(Player p) {
        for(Link link : this.inCreation){
            if(link.getPlayer().equals(p.getUniqueId())){
                return link.getStart() == null;
            }
        }
        return false;
    }

    public void setStart(Player p, Block block) {
        for(Link link : this.inCreation){
            if(link.getPlayer().equals(p.getUniqueId())){
                this.inCreation.remove(link);
                for(DevBlock devBlock : main.getDevBlocks()){
                    if(devBlock.compareToSelfLoc(block.getLocation())){
                        link.setStart(devBlock.getUuid());
                        this.checkLink(link);
                        return;
                    }
                }
            }
        }
    }

    public void setCondition(Player p, Block block) {
        for(Link link : this.inCreation){
            if(link.getPlayer().equals(p.getUniqueId())){
                this.inCreation.remove(link);
                for(DevBlock devBlock : main.getDevBlocks()){
                    if(devBlock.compareToSelfLoc(block.getLocation())){
                        link.setCondition(devBlock.getUuid());
                        this.checkLink(link);
                        return;
                    }
                }
            }
        }
    }

    private void checkLink(Link link) {
        if(link.getStart() == null || link.getCondition() == null){
            this.inCreation.add(link);
        }else{
            this.links.add(link);
            Bukkit.getPlayer(link.getPlayer()).sendMessage(ChatColor.DARK_GREEN + "Lien enregistré");
            Bukkit.getPlayer(link.getPlayer()).getInventory().setItemInMainHand(null);
        }
    }

    public boolean isLinked(UUID uuid) {
        for(Link link : this.links){
            if(link.getCondition().equals(uuid) || link.getStart().equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public void breakLink(UUID uuid) {
        for(Link link : this.links){
            if(link.getCondition().equals(uuid) || link.getStart().equals(uuid)){
                this.links.remove(link);
                return;
            }
        }
    }

    public void startLineLinked(UUID uuid, Player linePlayer) {
        for(Link link : this.links){
            if(link.getCondition().equals(uuid)){
                for(DevBlock devBlock : main.getDevBlocks()){
                    if(devBlock.getUuid().equals(link.getStart())){
                        new CodeExecutor(main, devBlock.getLoc().clone(), linePlayer);
                    }
                }
            }
        }
    }
}

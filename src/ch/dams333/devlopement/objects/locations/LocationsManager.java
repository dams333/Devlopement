package ch.dams333.devlopement.objects.locations;

import ch.dams333.devlopement.Devlopement;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LocationsManager {

    private Devlopement main;

    private List<GameLocation> gameLocations;
    private List<GameLocationsList> gameLocationsLists;

    public LocationsManager(Devlopement main) {
        this.main = main;
        this.gameLocations = new ArrayList<>();
        this.gameLocationsLists = new ArrayList<>();
    }

    public void deserialize() {

        File locationsFile = new File(main.getDataFolder(), "locations/locations.yml");
        YamlConfiguration locationsConfig = YamlConfiguration.loadConfiguration(locationsFile);

        File locationsListFile = new File(main.getDataFolder(), "locations/list.yml");
        YamlConfiguration locationsListConfig = YamlConfiguration.loadConfiguration(locationsListFile);

        for(String locUUID : locationsConfig.getKeys(false)){
            ConfigurationSection sec = locationsConfig.getConfigurationSection(locUUID);
            UUID playerUUId = UUID.fromString(sec.getString("Player"));
            String name = sec.getString("Name");
            ConfigurationSection l = sec.getConfigurationSection("Location");
            World world = Bukkit.getWorld(l.getString("World"));
            Double x = l.getDouble("X");
            Double y = l.getDouble("Y");
            Double z = l.getDouble("Z");
            Float yaw = Float.parseFloat(l.getString("Yaw"));
            Float pitch = Float.parseFloat(l.getString("Pitch"));

            GameLocation loc = new GameLocation(UUID.fromString(locUUID), playerUUId, name, new Location(world, x, y, z, yaw, pitch));
            this.gameLocations.add(loc);
        }

        for(String listUUID : locationsListConfig.getKeys(false)) {
            ConfigurationSection sec = locationsListConfig.getConfigurationSection(listUUID);
            UUID playerUUId = UUID.fromString(sec.getString("Player"));
            String name = sec.getString("Name");
            List<UUID> locations = new ArrayList<>();
            for(String locUUId : sec.getStringList("Locations")){
                locations.add(UUID.fromString(locUUId));
            }
            GameLocationsList list = new GameLocationsList(UUID.fromString(listUUID), playerUUId, name, locations);
            this.gameLocationsLists.add(list);
        }

    }

    public void serialize() {

        File locationsFile = new File(main.getDataFolder(), "locations/locations.yml");
        YamlConfiguration locationsConfig = YamlConfiguration.loadConfiguration(locationsFile);

        File locationsListFile = new File(main.getDataFolder(), "locations/list.yml");
        YamlConfiguration locationsListConfig = YamlConfiguration.loadConfiguration(locationsListFile);


        for(GameLocation loc : this.gameLocations){
            ConfigurationSection sec = locationsConfig.createSection(loc.getUuid().toString());
            sec.set("Player", loc.getPlayer().toString());
            sec.set("Name", loc.getName());
            ConfigurationSection l = sec.createSection("Location");
            l.set("World", loc.getLocation().getWorld().getName());
            l.set("X", loc.getLocation().getX());
            l.set("Y", loc.getLocation().getY());
            l.set("Z", loc.getLocation().getZ());
            l.set("Yaw", String.valueOf(loc.getLocation().getYaw()));
            l.set("Pitch", String.valueOf(loc.getLocation().getPitch()));
        }

        for(GameLocationsList list : this.gameLocationsLists){
            ConfigurationSection sec = locationsListConfig.createSection(list.getUuid().toString());
            sec.set("Player", list.getPlayer().toString());
            sec.set("Name", list.getName());
            List<String> uuids = new ArrayList<>();
            for(UUID loc : list.getLocations()){
                uuids.add(loc.toString());
            }
            sec.set("Locations", uuids);
        }


        try {
            locationsConfig.save(locationsFile);
            locationsListConfig.save(locationsListFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<GameLocation> getPlayerLocations(Player p){
        List<GameLocation> list = new ArrayList<>();
        for(GameLocation loc : this.gameLocations){
            if(loc.getPlayer().equals(p.getUniqueId())){
                list.add(loc);
            }
        }
        return list;
    }

    public boolean playerHasLocationByName(Player p, String name) {
        for(GameLocation loc : getPlayerLocations(p)){
            if(loc.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public void createLocation(Player p, String name, Location location) {
        GameLocation gameLocation = new GameLocation(p.getUniqueId(), name, location);
        this.gameLocations.add(gameLocation);
    }

    public void removePlayerLoc(Player p, String name) {
        for(GameLocation loc : getPlayerLocations(p)){
            if(loc.getName().equalsIgnoreCase(name)){
                this.gameLocations.remove(loc);
            }
        }
    }

    public List<GameLocationsList> getPlayerLocationsLists(Player p){
        List<GameLocationsList> list = new ArrayList<>();
        for(GameLocationsList loc : this.gameLocationsLists){
            if(loc.getPlayer().equals(p.getUniqueId())){
                list.add(loc);
            }
        }
        return list;
    }

    public boolean playerHasLocationsListByName(Player p, String name) {
        for(GameLocationsList list : getPlayerLocationsLists(p)){
            if(list.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public GameLocationsList getPlayerLocationsListByName(Player p, String name) {
        for(GameLocationsList list : getPlayerLocationsLists(p)){
            if(list.getName().equalsIgnoreCase(name)){
                return list;
            }
        }
        return null;
    }

    public void createLocationsList(Player p, String name) {
        GameLocationsList gameLocation = new GameLocationsList(p.getUniqueId(), name);
        this.gameLocationsLists.add(gameLocation);
    }

    public void removePlayerLocactionsList(Player p, String name) {
        for(GameLocationsList loc : getPlayerLocationsLists(p)){
            if(loc.getName().equalsIgnoreCase(name)){
                this.gameLocationsLists.remove(loc);
            }
        }
    }

    private GameLocation getPlayerLocationByName(Player p, String locationName) {
        for(GameLocation loc : getPlayerLocations(p)){
            if(loc.getName().equalsIgnoreCase(locationName)){
                return loc;
            }
        }
        return null;
    }

    public GameLocation getLocation(UUID uuid) {
        for(GameLocation loc : this.gameLocations){
            if(loc.getUuid().equals(uuid)){
                return loc;
            }
        }
        return null;
    }

    public void addLocationToList(Player p, String listName, String locationName) {
        GameLocationsList list = getPlayerLocationsListByName(p, listName);
        int index = this.gameLocationsLists.indexOf(list);
        GameLocation gameLocation = getPlayerLocationByName(p, locationName);
        list.addLocation(gameLocation.getUuid());
        this.gameLocationsLists.set(index, list);
    }


    public void removeLocationFromName(Player p, String listName, String locationName) {
        GameLocationsList list = getPlayerLocationsListByName(p, listName);
        int index = this.gameLocationsLists.indexOf(list);
        GameLocation gameLocation = getPlayerLocationByName(p, locationName);
        list.removeLocation(gameLocation.getUuid());
        this.gameLocationsLists.set(index, list);
    }


    public void loadLocationsInventory(Player p, int page, UUID selected){

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Choix de la location > Page " + page);

        inv.setItem(45, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page précédente"));
        inv.setItem(53, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page suivante"));


        int messagePerPage = 5 * 9;

        int start = (page - 1) * messagePerPage;


        for(int i = 0; i < messagePerPage; i++){

            if((getPlayerLocations(p).size() - 1) >= start){
                GameLocation loc = getPlayerLocations(p).get(start);
                ItemStack it = main.API.itemStackManager.create(Material.PAPER, getLocation(loc.getUuid()).getName());
                if(selected != null && loc.getUuid().equals(selected)){
                    it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemMeta itM = it.getItemMeta();
                    itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itM.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
                    it.setItemMeta(itM);
                }

                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                tag.setString("uuid", loc.getUuid().toString());
                stack.setTag(tag);
                it = CraftItemStack.asCraftMirror(stack);

                inv.setItem(i, it);
            }else{
                break;
            }
            start++;
        }

        p.openInventory(inv);

    }

    public void loadLocationsListsInventory(Player p, int page, UUID selected){

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Choix de la liste de locations > Page " + page);

        inv.setItem(45, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page précédente"));
        inv.setItem(53, main.API.itemStackManager.create(Material.ARROW, ChatColor.GRAY + "Page suivante"));


        int messagePerPage = 5 * 9;

        int start = (page - 1) * messagePerPage;


        for(int i = 0; i < messagePerPage; i++){

            if((getPlayerLocationsLists(p).size() - 1) >= start){
                GameLocationsList loc = getPlayerLocationsLists(p).get(start);
                ItemStack it = main.API.itemStackManager.create(Material.PAPER, getLocationsList(loc.getUuid()).getName());
                if(selected != null && loc.getUuid().equals(selected)){
                    it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    ItemMeta itM = it.getItemMeta();
                    itM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itM.setLore(Arrays.asList(ChatColor.GREEN + "Sélectionné"));
                    it.setItemMeta(itM);
                }

                net.minecraft.server.v1_15_R1.ItemStack stack = CraftItemStack.asNMSCopy(it);
                NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();
                tag.setString("uuid", loc.getUuid().toString());
                stack.setTag(tag);
                it = CraftItemStack.asCraftMirror(stack);

                inv.setItem(i, it);
            }else{
                break;
            }
            start++;
        }

        p.openInventory(inv);

    }

    public GameLocationsList getLocationsList(UUID uuid) {
        for(GameLocationsList list : this.gameLocationsLists){
            if(list.getUuid().equals(uuid)){
                return list;
            }
        }
        return null;
    }

    private Map<UUID, Location> last = new HashMap<>();

    public void tp(Player p, Location location) {
        last.put(p.getUniqueId(), location);
    }

    public boolean hasLastTP(Player p) {
        return this.last.containsKey(p.getUniqueId());
    }

    public void teleportToLast(Player p) {
        p.teleport(this.last.get(p.getUniqueId()));
    }
}

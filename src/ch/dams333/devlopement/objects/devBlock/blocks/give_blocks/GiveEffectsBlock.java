package ch.dams333.devlopement.objects.devBlock.blocks.give_blocks;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.DevBlock;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import ch.dams333.devlopement.objects.executor.CodeExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveEffectsBlock extends DevBlock{

    private boolean all;

    public GiveEffectsBlock(Devlopement main, Location loc) {
        super(main, loc);
        super.setType(BlockType.GIVE_EFFECTS);
        all = true;
    }

    public GiveEffectsBlock(File file, Devlopement main) {
        super(file, main);
        super.setType(BlockType.GIVE_EFFECTS);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        all = config.getBoolean("All");

    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GIVE_EFFECTS;
    }

    @Override
    public void serialize() {
        File blockFile = new File(super.getMain().getDataFolder(), "blocks/give/give_effects/" + super.getUuid().toString() + ".yml");
        YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blockFile);
        for (String key : blockConfig.getKeys(false)) {
            blockConfig.set(key, null);
        }


        blockConfig = super.saveLocation(blockConfig, super.getLoc());

        blockConfig.set("All", this.all);

        try {
            blockConfig.save(blockFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clickOn(PlayerInteractEvent e) {
        if(e.getPlayer().getPose() == Pose.SNEAKING) {
            e.setCancelled(true);
            getMain().enterModif(e.getPlayer(), this);
            this.loadInventory(e.getPlayer());
        }
    }

    @Override
    public void inventoryOn(InventoryClickEvent e) {
        if(e.getCurrentItem().getType() == Material.REDSTONE){
            this.all = true;
        }
        if(e.getCurrentItem().getType() == Material.EMERALD){
            this.all = false;
        }
        getMain().updateModif(this);
        loadInventory((Player) e.getWhoClicked());
        getMain().enterModif((Player) e.getWhoClicked(), this);
    }

    @Override
    public void execute(CodeExecutor codeExecutor) {

        BrewingStand brewingStand = (BrewingStand) getLoc().getBlock().getState();
        List<PotionEffect> potionEffects = new ArrayList<>();
        for(ItemStack it : brewingStand.getInventory()){
            if(it != null && it.getType() == Material.POTION) {
                PotionMeta potionMeta = (PotionMeta) it.getItemMeta();
                PotionData potionData = potionMeta.getBasePotionData();
                potionEffects.add(fromPotionData(potionData));
            }
        }

        if(this.all){
            for(Player p : Bukkit.getOnlinePlayers()){
                for(PotionEffect potionEffect : potionEffects){
                    p.addPotionEffect(potionEffect);
                }
            }
        }else{
            for(PotionEffect potionEffect : potionEffects){
                codeExecutor.getLinePlayer().addPotionEffect(potionEffect);
            }
        }

        codeExecutor.moveHead();
    }


    private void loadInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "A qui donner les effets");

        if(this.all){
            inv.setItem(4, getMain().API.itemStackManager.create(Material.EMERALD, ChatColor.GREEN + "Tous les joueurs", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }else{
            inv.setItem(4, getMain().API.itemStackManager.create(Material.REDSTONE, ChatColor.RED + "Joueur de la ligne", Arrays.asList(ChatColor.GRAY + "Clique droit pour changer")));
        }

        p.openInventory(inv);
    }



    private PotionEffect fromPotionData(PotionData data) {
        PotionEffectType type = data.getType().getEffectType();
        if (type == PotionEffectType.HEAL || type == PotionEffectType.HARM) {
            return new PotionEffect(type, 1, data.isUpgraded() ? 2 : 1);
        } else if (type == PotionEffectType.REGENERATION || type == PotionEffectType.POISON) {
            if (data.isExtended()) {
                return new PotionEffect(type, 1800, 1);
            } else if (data.isUpgraded()) {
                return new PotionEffect(type, 440, 2);
            } else {
                return new PotionEffect(type, 900, 1);
            }
        } else if (type == PotionEffectType.NIGHT_VISION || type == PotionEffectType.INVISIBILITY
                || type == PotionEffectType.FIRE_RESISTANCE || type == PotionEffectType.WATER_BREATHING) {
            return new PotionEffect(type, data.isExtended() ? 9600 : 3600, 1);
        } else if (type == PotionEffectType.WEAKNESS || type == PotionEffectType.SLOW) {
            return new PotionEffect(type, data.isExtended() ? 4800 : 1800, 1);
        } else if (data.isExtended()) {
            return new PotionEffect(type, 9600, 1);
        } else if (data.isUpgraded()) {
            return new PotionEffect(type, 1800, 2);
        } else {
            return new PotionEffect(type, 3600, 1);
        }
    }

}

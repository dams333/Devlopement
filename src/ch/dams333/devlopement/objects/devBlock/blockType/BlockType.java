package ch.dams333.devlopement.objects.devBlock.blockType;


import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum BlockType {
    EVENT_START("event_start", ChatColor.GOLD + "Démarrer une ligne après un évennement", Arrays.asList(ChatColor.GRAY +"Démarre une ligne lorsq'un évennement se produit", ChatColor.GRAY + "(Clique droit pour définir lequel)"), Material.DIAMOND_BLOCK, (byte)0),
    DEV_START("dev_start", ChatColor.GOLD + "Démarrer une ligne de test", Arrays.asList(ChatColor.GRAY +"Démarre une ligne en faisant clique droit"), Material.LAPIS_BLOCK, (byte)0),

    GIVE_ITEMS("give_items", ChatColor.GOLD + "Donner des items au joueur", Arrays.asList(ChatColor.GRAY +"Donne au joueur les items présents dans le coffre", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui donner"), Material.CHEST, (byte)0),
    GIVE_EFFECTS("give_effects", ChatColor.GOLD + "Donner des effets au joueur", Arrays.asList(ChatColor.GRAY +"Donne au joueur les effets des potions présentes dans l'alambic", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui donner"), Material.BREWING_STAND, (byte)0),

    ONE_SECOND("one_second", ChatColor.GOLD + "Attendre 1 seconde", Arrays.asList(ChatColor.GRAY + "Quand la ligne passe sur ce bloc", ChatColor.GRAY + "elle attend 1 seconde avant d'avancer"), Material.QUARTZ_SLAB, (byte)0),
    FIVE_SECOND("five_second", ChatColor.GOLD + "Attendre 5 seconde", Arrays.asList(ChatColor.GRAY + "Quand la ligne passe sur ce bloc", ChatColor.GRAY + "elle attend 5 seconde avant d'avancer"), Material.BRICK_SLAB, (byte)0),
    TEN_SECOND("ten_second", ChatColor.GOLD + "Attendre 10 seconde", Arrays.asList(ChatColor.GRAY + "Quand la ligne passe sur ce bloc", ChatColor.GRAY + "elle attend 10 seconde avant d'avancer"), Material.STONE_BRICK_SLAB, (byte)0),
    THIRTY_SECOND("thirty_second", ChatColor.GOLD + "Attendre 30 seconde", Arrays.asList(ChatColor.GRAY + "Quand la ligne passe sur ce bloc", ChatColor.GRAY + "elle attend 30 seconde avant d'avancer"), Material.PURPUR_SLAB, (byte)0);


    private String text;
    private String name;
    private List<String> description;
    private Material material;
    private byte data;

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }

    BlockType(String text, String name, List<String> description, Material material, byte data) {

        this.text = text;
        this.name = name;
        this.description = description;
        this.material = material;
        this.data = data;
    }

    public String toString(){
        return text;
    }

    public static BlockType getFromString(String text){
        for(BlockType blockType : BlockType.values()){
            if(blockType.text.equals(text)){
                return blockType;
            }
        }
        return null;
    }
}

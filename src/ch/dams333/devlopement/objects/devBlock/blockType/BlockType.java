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

    CLEAR_ITEMS("clear_items", ChatColor.GOLD + "Clear les objets", Arrays.asList(ChatColor.GRAY +"Vide entièrement l'inventaire du joueur", ChatColor.GRAY + "Clique droit pour choisir qui vider"), Material.WHITE_STAINED_GLASS, (byte)0),
    CLEAR_EFFECTS("clear_effects", ChatColor.GOLD + "Clear les effets", Arrays.asList(ChatColor.GRAY +"Stope tous les effets en cours du joueur", ChatColor.GRAY + "Clique droit pour choisir qui stoper"), Material.YELLOW_STAINED_GLASS, (byte)0),

    LOCATION_TP("location_tp", ChatColor.GOLD + "Téléporter le joueur", Arrays.asList(ChatColor.GRAY +"Téléporte le joueur à une location définie", ChatColor.GRAY + "SHIFT + clique droit pour choisir qui téléporter"), Material.END_STONE, (byte)0),
    LIST_TP("list_tp", ChatColor.GOLD + "Téléporter à une liste de location", Arrays.asList(ChatColor.GRAY +"Téléporte les joueurs selon une liste de locations", ChatColor.GRAY + "SHIFT + clique droit pour choisir qui téléporter"), Material.END_PORTAL_FRAME, (byte)0),
    LAST_TP("last_tp", ChatColor.GOLD + "Téléporter à la dérnière location", Arrays.asList(ChatColor.GRAY +"Téléporte à la dérnière location à laquelle le joueur a été TP", ChatColor.GRAY + "SHIFT + clique droit pour choisir qui téléporter"), Material.SLIME_BLOCK, (byte)0),

    CHAT_MESSAGE("chat_message", ChatColor.GOLD + "Message dans le chat", Arrays.asList(ChatColor.GRAY +"Envoie un message dans le chat du joueur", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui envoyer"), Material.SEA_LANTERN, (byte)0),
    TITLE_MESSAGE("title_message", ChatColor.GOLD + "Message en Title", Arrays.asList(ChatColor.GRAY +"Envoie un message en Title au joueur", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui envoyer"), Material.PRISMARINE, (byte)0),
    SUBTITLE_MESSAGE("subtitle_message", ChatColor.GOLD + "Message en SubTitle", Arrays.asList(ChatColor.GRAY +"Envoie un message en SubTitle au joueur", ChatColor.GRAY + "(Demande un Title au préalable)", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui envoyer"), Material.DARK_PRISMARINE, (byte)0),
    ACTIONBAR_MESSAGE("actionbar_message", ChatColor.GOLD + "Message dans l'actionbar", Arrays.asList(ChatColor.GRAY +"Envoie un message dans l'actionbar du joueur", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui envoyer"), Material.PRISMARINE_BRICKS, (byte)0),

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

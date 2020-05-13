package ch.dams333.devlopement.objects.devBlock.blockType;


import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum BlockType {
    EVENT_START("event_start", ChatColor.GOLD + "Démarrer une ligne après un évennement", Arrays.asList(ChatColor.GRAY +"Démarre une ligne lorsq'un évennement se produit", ChatColor.GRAY + "(Clique droit pour définir lequel)"), Material.DIAMOND_BLOCK, (byte)0),
    DEV_START("dev_start", ChatColor.GOLD + "Démarrer une ligne de test", Arrays.asList(ChatColor.GRAY +"Démarre une ligne en faisant clique droit"), Material.LAPIS_BLOCK, (byte)0),
    ELSE_START("else_start", ChatColor.GOLD + "Démarrer une ligne 'si non'", Arrays.asList(ChatColor.GRAY +"Démarre une ligne quand la condition linkée n'est pas réalisé", ChatColor.GRAY + "/link pour lier à une condition", ChatColor.GRAY + "Le joueur de la ligne est celui de la condition"), Material.COAL_BLOCK, (byte)0),

    PLAYER_CONDITION("player_condition", ChatColor.GOLD + "Teste le nombre de joueurs en ligne", Arrays.asList(ChatColor.GRAY +"Teste si le nombre de joueurs est", ChatColor.GRAY + "égale, inférieur ou supérieur à un nombre", ChatColor.GRAY + "Ne comptabilise pas les joueurs en spectateur"), Material.HOPPER, (byte)0),

    BOOLEAN_MODIFY_VARIABLE("boolean_modify_variable", ChatColor.GOLD + "Modifier une variable boolean", Arrays.asList(ChatColor.GRAY +"Permet de modifier une variable boolean en", ChatColor.GRAY + "la passant sur false, true ou en la swichant"), Material.EMERALD_BLOCK, (byte)0),
    INTEGER_MODIFY_VARIABLE("intger_modify_variable", ChatColor.GOLD + "Modifier une variable integer", Arrays.asList(ChatColor.GRAY +"Permet de modifier une variable integer selon un nombre en", ChatColor.GRAY + "le soustrayant, l'additionnant ou le définissant comme valeur"), Material.IRON_BLOCK, (byte)0),

    BOOLEAN_TEST_VARIABLE("boolean_test_variable", ChatColor.GOLD + "Teste une variable boolean", Arrays.asList(ChatColor.GRAY +"Permet de tester l'ètât d'une variable boolean"), Material.LIME_STAINED_GLASS, (byte)0),
    INTEGER_TEST_VARIABLE("integer_test_variable", ChatColor.GOLD + "Teste une variable integer", Arrays.asList(ChatColor.GRAY +"Permet de comparer l'ètât d'une variable integer à un nombre"), Material.WHITE_STAINED_GLASS, (byte)0),

    GIVE_ITEMS("give_items", ChatColor.GOLD + "Donner des items au joueur", Arrays.asList(ChatColor.GRAY +"Donne au joueur les items présents dans le coffre", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui donner"), Material.CHEST, (byte)0),
    GIVE_EFFECTS("give_effects", ChatColor.GOLD + "Donner des effets au joueur", Arrays.asList(ChatColor.GRAY +"Donne au joueur les effets des potions présentes dans l'alambic", ChatColor.GRAY + "SHIFT + clique droit pour choisir à qui donner"), Material.BREWING_STAND, (byte)0),

    CLEAR_ITEMS("clear_items", ChatColor.GOLD + "Clear les objets", Arrays.asList(ChatColor.GRAY +"Vide entièrement l'inventaire du joueur", ChatColor.GRAY + "Clique droit pour choisir qui vider"), Material.BLACK_STAINED_GLASS, (byte)0),
    CLEAR_EFFECTS("clear_effects", ChatColor.GOLD + "Clear les effets", Arrays.asList(ChatColor.GRAY +"Stope tous les effets en cours du joueur", ChatColor.GRAY + "Clique droit pour choisir qui stoper"), Material.YELLOW_STAINED_GLASS, (byte)0),

    GAMEMODE("gamemode", ChatColor.GOLD + "Change le GameMode", Arrays.asList(ChatColor.GRAY +"Définit le mode de jeu du joueur", ChatColor.GRAY + "Clique droit pour choisir qui changer"), Material.REDSTONE_BLOCK, (byte)0),
    PVP("pvp", ChatColor.GOLD + "Modifie le PVP", Arrays.asList(ChatColor.GRAY +"Active ou désactive le combat entre joueurs"), Material.GOLD_BLOCK, (byte)0),

    START_TIMER("start_timer", ChatColor.GOLD + "Démarre un Timer", Arrays.asList(ChatColor.GRAY +"Démarre un Timer selon le nombre où il est", ChatColor.GRAY + "(0 par défaut)"), Material.OAK_LOG, (byte)0),
    PAUSE_TIMER("pause_timer", ChatColor.GOLD + "Stop un Timer", Arrays.asList(ChatColor.GRAY +"Met en pause un Timer", ChatColor.GRAY + "Celui-ci reste sur cette valeur"), Material.BIRCH_LOG, (byte)0),
    RESET_TIMER("reset_timer", ChatColor.GOLD + "Reset un Timer", Arrays.asList(ChatColor.GRAY +"Remet le Timer à 0"), Material.ACACIA_LOG, (byte)0),

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

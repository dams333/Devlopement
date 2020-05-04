package ch.dams333.devlopement.objects.devBlock.blockType;


import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum BlockType {
    ONE_SECOND("one_second", "Attendre 1 seconde", Arrays.asList("Quand la ligne passe sur ce bloc", "elle attend 1 seconde avant d'avancer"), Material.QUARTZ_SLAB, (byte)0);


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

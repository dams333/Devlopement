package ch.dams333.devlopement.commands.admin;

import ch.dams333.devlopement.Devlopement;
import ch.dams333.devlopement.objects.devBlock.blockType.BlockType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DevItemsCommand implements CommandExecutor {
    Devlopement main;
    public DevItemsCommand(Devlopement devlopement) {
        this.main = devlopement;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(main.isInDevMod(p)){
                Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Items de développement");

                ItemStack separ = main.API.itemStackManager.create(Material.BLACK_STAINED_GLASS_PANE, " ");

                inv.setItem(4, separ);
                inv.setItem(13, separ);
                inv.setItem(22, separ);
                inv.setItem(31, separ);
                inv.setItem(40, separ);
                inv.setItem(49, separ);

                setItem(inv, 0, BlockType.DEV_START);
                setItem(inv, 9, BlockType.ONE_SECOND);

                p.openInventory(inv);
                return true;
            }
            p.sendMessage(ChatColor.RED + "Vous devez être en mode de développement pour faire celà");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Vous devez être connecté sur le serveur pour faire celà");
        return false;
    }

    private void setItem(Inventory inv, int slot, BlockType blockType){
        inv.setItem(slot, main.API.itemStackManager.create(blockType.getMaterial(), blockType.getData(), blockType.getName(), blockType.getDescription()));
    }

}

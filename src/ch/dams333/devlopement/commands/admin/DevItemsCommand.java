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
                setItem(inv, 1, BlockType.EVENT_START);
                setItem(inv, 2, BlockType.ELSE_START);

                setItem(inv, 9, BlockType.ONE_SECOND);
                setItem(inv, 10, BlockType.FIVE_SECOND);
                setItem(inv, 11, BlockType.TEN_SECOND);
                setItem(inv, 12, BlockType.THIRTY_SECOND);

                setItem(inv, 18, BlockType.GIVE_ITEMS);
                setItem(inv, 19, BlockType.GIVE_EFFECTS);
                setItem(inv, 20, BlockType.CLEAR_ITEMS);
                setItem(inv, 21, BlockType.CLEAR_EFFECTS);

                setItem(inv, 27, BlockType.CHAT_MESSAGE);
                setItem(inv, 28, BlockType.TITLE_MESSAGE);
                setItem(inv, 29, BlockType.SUBTITLE_MESSAGE);
                setItem(inv, 30, BlockType.ACTIONBAR_MESSAGE);

                setItem(inv, 36, BlockType.LOCATION_TP);
                setItem(inv, 37, BlockType.LIST_TP);
                setItem(inv, 38, BlockType.LAST_TP);

                setItem(inv, 45, BlockType.GAMEMODE);
                setItem(inv, 46, BlockType.PVP);

                setItem(inv, 5, BlockType.PLAYER_CONDITION);
                setItem(inv, 6, BlockType.BOOLEAN_TEST_VARIABLE);
                setItem(inv, 7, BlockType.INTEGER_TEST_VARIABLE);

                setItem(inv, 14, BlockType.BOOLEAN_MODIFY_VARIABLE);
                setItem(inv, 15, BlockType.INTEGER_MODIFY_VARIABLE);

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

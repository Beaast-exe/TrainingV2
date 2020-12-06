package com.weswaas.training.command;

import com.weswaas.training.Main;
import com.weswaas.training.manager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 08/01/2017.
 */
public class InventoryCommand implements CommandExecutor{

    private PlayerManager plmanager;

    public InventoryCommand(PlayerManager plmanager){
        this.plmanager = plmanager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 1){
                String arg = args[0];
                if(!plmanager.invs.containsKey(arg)){
                    p.sendMessage(Main.PREFIX + "§cThis inventory isn't available anymore.");
                    return true;
                }

                p.openInventory(plmanager.invs.get(arg));

            }
        }
        return false;
    }
}

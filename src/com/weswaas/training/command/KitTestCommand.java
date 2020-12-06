package com.weswaas.training.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 01/12/2016.
 */
public class KitTestCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        Player p = (Player) sender;

        if(args.length == 1){

            OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);

        }else{
            p.sendMessage(ChatColor.RED + "False args length");
        }

        return false;
    }

}

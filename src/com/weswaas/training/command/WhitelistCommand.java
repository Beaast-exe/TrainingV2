package com.weswaas.training.command;

import com.weswaas.training.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 29/04/2020.
 */
public class WhitelistCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(p.hasPermission("training.whitelister")){
                if(args.length == 1){
                    String target = args[0];
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + target + " group set TrainingWhitelisted");
                    p.sendMessage(Main.PREFIX + target + " has been added to the whitelist.");
                }else{
                    p.sendMessage(Main.PREFIX + "§cInvalid synthax. Please try with /whitelist <player>");
                }
            }

        }

        return false;
    }

}

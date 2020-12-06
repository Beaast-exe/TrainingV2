package com.weswaas.training.command;

import com.weswaas.training.Main;
import com.weswaas.training.gui.guis.MatchGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 24/12/2016.
 */
public class DuelCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null && target.isOnline()){
                    if(target.getName() != p.getName()){
                        new MatchGUI(false, true, target, false).open(p);
                    }else{
                        p.sendMessage(Main.PREFIX + "§cYou cannot duel yourself.");
                    }
                }else{
                    p.sendMessage(Main.PREFIX + ChatColor.RED + args[0] + " is not online.");
                }
            }else{
                p.sendMessage(Main.PREFIX + "§cUsage: /duel <player>");
            }
        }
        return false;
    }

}

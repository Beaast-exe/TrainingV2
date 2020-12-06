package com.weswaas.training.command.admin;

import com.weswaas.training.Main;
import com.weswaas.training.ServerMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 25/04/2020.
 */
public class StatusCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;

            if(p.hasPermission("training.manage")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("online")){
                        if(ServerMode.getServerMode() != ServerMode.ONLINE){
                            ServerMode.setServerMode(ServerMode.ONLINE);
                            p.sendMessage(Main.PREFIX + "The server is now in online mode.");
                        }else{
                            p.sendMessage("§cThe server is already in online mode.");
                        }
                    }else if(args[0].equalsIgnoreCase("offline")){
                        if(ServerMode.getServerMode() != ServerMode.OFFLINE){
                            ServerMode.setServerMode(ServerMode.OFFLINE);
                            p.sendMessage(Main.PREFIX + "The server is now in offline mode.");
                        }else{
                            p.sendMessage("§cThe server is already in offline mode.");
                        }
                    }else if(args[0].equalsIgnoreCase("maintenance")){
                        if(ServerMode.getServerMode() != ServerMode.MAINTENANCE){
                            ServerMode.setServerMode(ServerMode.MAINTENANCE);
                            p.sendMessage(Main.PREFIX + "The server is now in maintenance mode.");
                        }else{
                            p.sendMessage("§cThe server is already in maintenance mode.");
                        }
                    }
                }else{
                    p.sendMessage("§cWrong synthax.");
                }
            }

        }
        return false;
    }

}

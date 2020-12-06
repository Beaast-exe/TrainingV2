package com.weswaas.training.command.admin;

import com.weswaas.training.Main;
import com.weswaas.training.manager.SpectatorManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 30/04/2020.
 */
public class TeleportCommand implements CommandExecutor{

    private SpectatorManager spec;

    public TeleportCommand(SpectatorManager spec){
        this.spec = spec;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;

            if(p.hasPermission("training.manage")){
                if(args.length == 1){
                    OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                    if(op.isOnline()){
                        Player target = op.getPlayer();
                        if(spec.isSpectator(p)){
                            spec.teleport(p, target);
                        }else{
                            p.sendMessage(Main.PREFIX + "§cYou have to be spectator to teleport to other players.");
                        }
                    }else{
                        p.sendMessage(Main.PREFIX + ChatColor.RED + args[0] + " is not online.");
                    }
                }else{
                    p.sendMessage("§cWrong synthax. Please try with /tp <player>");
                }
            }

        }
        return false;
    }

}

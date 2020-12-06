package com.weswaas.training.command.admin;

import com.weswaas.training.Main;
import com.weswaas.training.manager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 28/04/2020.
 */
public class SpectatorCommand implements CommandExecutor{

    private PlayerManager pm;

    public SpectatorCommand(PlayerManager pm){
        this.pm = pm;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player)sender;

            if(p.hasPermission("training.spectate")){
                if(args.length == 0){
                    if(pm.isSpectator(p)){
                        pm.removeSpectator(p);
                    }else{
                        pm.setSpectator(p);
                    }
                }else{
                    p.sendMessage(Main.PREFIX + "§cInvalid synthax. Just do /spec");
                }
            }

        }
        return false;
    }

}

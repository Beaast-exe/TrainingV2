package com.weswaas.training.command;

import com.weswaas.training.Main;
import com.weswaas.training.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 08/01/2017.
 */
public class RankedCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 0){
                PlayerData data = Main.getInstance().getPlayerDataManager().getPlayerData(p);
                int rankeds = data.getRankedsRemain();
                p.sendMessage(Main.PREFIX + "You have §b" + (rankeds == 69 ? " an infinity of" : rankeds) + " rankeds §3remain");
            }else{
                p.sendMessage(Main.PREFIX + "§cInvalid synthax. Please try with /ranked");
            }
        }
        return false;
    }
}

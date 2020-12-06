package com.weswaas.training.command;

import com.weswaas.training.Main;
import com.weswaas.training.data.PlayerData;
import com.weswaas.training.game.ladder.Ladder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Weswas on 28/12/2016.
 */
public class EloCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 0){

                int global = 1400;
                int count = 0;
                int total = 0;

                p.sendMessage(Main.PREFIX + p.getName() + "'s ratings:");
                PlayerData data = Main.getInstance().getPlayerDataManager().getPlayerData(p);
                for(Ladder ladder : Main.getInstance().getLadderManager().getLadders()){
                    int rating = data.getRating(ladder);
                    p.sendMessage("§b" + ladder.getName() + " §8» §3" + rating);
                    count = count + rating;
                    total++;
                }

                global = count / total;
                p.sendMessage("§b");
                p.sendMessage("§bGlobal Rating §8» §3" + global);

            }else{
                p.sendMessage(Main.PREFIX + "§cUsage: /elo");
            }
        }
        return false;
    }
}

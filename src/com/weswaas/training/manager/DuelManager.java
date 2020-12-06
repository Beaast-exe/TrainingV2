package com.weswaas.training.manager;

import com.weswaas.training.Main;
import com.weswaas.training.game.ladder.Ladder;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;

/**
 * Created by Weswas on 24/12/2016.
 */
public class DuelManager implements Listener{

    Packet packet;

    private HashMap<Player, Ladder> gamemode = new HashMap<>();
    public HashMap<Player, Player> reply = new HashMap<>();

    public void duel(Player p, Player target, Ladder ladder){
        p.sendMessage(Main.PREFIX + "You sent a duel request to §b" + target.getName() + " §3with kit §b" + ladder.getName());
        target.sendMessage(Main.PREFIX + ChatColor.AQUA + p.getName() + " §3sent you a duel request with kit §b" + ladder.getName());
        sendRawMessage(target, p);
        this.gamemode.put(p, ladder);
        this.reply.put(p, target);
    }

    public void acceptDuel(Player p, Player target){
        Ladder ladder = this.gamemode.get(p);
        if(ladder == null){
            for(Player pls : new Player[]{p, target}){
                pls.sendMessage(Main.PREFIX + "§cInternal error occured");
            }
            return;
        }
        Main.getInstance().getMatchManager().startNewMatch(p, target, ladder, false);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(e.getInventory().getName().contains("Duel") && this.gamemode.containsKey(e.getPlayer())){
            this.gamemode.remove(e.getPlayer());
        }
    }

    private void sendRawMessage(Player p, Player base){
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + Main.PREFIX + "§8» §3Click §bHERE §3to accept\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/accept " + base.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Click me to accept\",\"color\":\"aqua\"}]}}}"));
        connection.sendPacket(packet);
    }

}

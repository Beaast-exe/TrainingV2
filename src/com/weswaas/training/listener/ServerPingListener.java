package com.weswaas.training.listener;

import com.weswaas.training.Main;
import com.weswaas.training.ServerMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weswas on 25/04/2020.
 */
public class ServerPingListener implements Listener{

    @EventHandler
    public void on(ServerListPingEvent e){

        List<String> motd = new ArrayList<>();


        motd.add("" + ServerMode.getServerModeToString());
        motd.add("" + Main.maxPlayers);


        StringBuilder list = new StringBuilder("");
        for(String s : motd){
            list.append(s + "}");
        }

        e.setMotd(list.toString().trim());

    }

}

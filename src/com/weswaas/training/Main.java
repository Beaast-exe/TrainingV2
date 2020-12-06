package com.weswaas.training;

import com.weswaas.training.command.*;
import com.weswaas.training.command.admin.SpectatorCommand;
import com.weswaas.training.command.admin.StatusCommand;
import com.weswaas.training.command.admin.TeleportCommand;
import com.weswaas.training.data.Database;
import com.weswaas.training.data.PlayerDataManager;
import com.weswaas.training.data.SQLType;
import com.weswaas.training.game.arena.ArenaManager;
import com.weswaas.training.game.ladder.Ladder;
import com.weswaas.training.game.ladder.LadderManager;
import com.weswaas.training.game.match.Match;
import com.weswaas.training.game.match.MatchManager;
import com.weswaas.training.kit.KitEditor;
import com.weswaas.training.kit.KitManager;
import com.weswaas.training.listener.InventoryListener;
import com.weswaas.training.listener.PlayerListener;
import com.weswaas.training.listener.ServerPingListener;
import com.weswaas.training.manager.DuelManager;
import com.weswaas.training.manager.PlayerManager;
import com.weswaas.training.manager.SpectatorManager;
import com.weswaas.training.util.ConfigUtil;
import com.weswaas.training.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Created by Weswas on 01/12/2016.
 */
public class Main extends JavaPlugin{

    private PlayerManager playerm;
    private LocationUtil loc;
    private LadderManager ladder;
    private KitManager kit;
    private DuelManager duel;
    private MatchManager match;
    private ArenaManager arena;
    private PlayerDataManager data;
    private KitEditor kiteditor;
    private Database database;
    private SpectatorManager spec;

    private static Main instance;

    public static int maxPlayers = 150;

    public static String PREFIX = ChatColor.AQUA + "Oniziac" + ChatColor.DARK_GRAY + " » " + ChatColor.DARK_AQUA;

    @Override
    public void onEnable() {

        instances();
        listeners();
        ConfigUtil.registerConfig();
        kit.register();
        arena.registerArenas();
        LocationUtil.register();
        database.connect();
        getCommand("kit").setExecutor(new KitTestCommand());
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("elo").setExecutor(new EloCommand());
        getCommand("inv").setExecutor(new InventoryCommand(playerm));
        getCommand("rankeds").setExecutor(new RankedCommand());
        getCommand("status").setExecutor(new StatusCommand());
        getCommand("spec").setExecutor(new SpectatorCommand(playerm));
        getCommand("whitelist").setExecutor(new WhitelistCommand());
        getCommand("teleport").setExecutor(new TeleportCommand(spec));

        HashMap<String, SQLType> map = new HashMap<>();
        map.put("uuid", SQLType.STRING);
        map.put("name", SQLType.STRING);
        map.put("rankeds", SQLType.INTEGER);
        map.put("day", SQLType.INTEGER);
        for(Ladder ladder : getLadderManager().getLadders()){
            map.put(ladder.getName().toLowerCase(), SQLType.STRING);
        }

        HashMap<String, SQLType> m = new HashMap<>();
        m.put("uuid", SQLType.STRING);
        for(Ladder ladder : getLadderManager().getLadders()){
            m.put(ladder.getName().toLowerCase(), SQLType.STRING);
        }

        database.createTable("datas", map);
        database.createTable("kits", m);
        match.chunkLoader();

        data.cache();

    }

    @Override
    public void onDisable(){
        for(Match matches : match.getCurrentMatches()){
            matches.clear();
        }
    }

    private void instances(){
        instance = this;
        spec = new SpectatorManager();
        playerm = new PlayerManager(spec);
        spec.setPlayerManager(playerm);
        loc = new LocationUtil();
        kit = new KitManager();
        ladder = new LadderManager(kit);
        duel = new DuelManager();
        arena = new ArenaManager();
        match = new MatchManager(arena, ladder);
        data = new PlayerDataManager();
        kiteditor = new KitEditor();
        database = new Database("jdbc:mysql://", "localhost", "practice", "weswas", "wqa159");
    }

    private void listeners(){

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new DuelManager(), this);
        pm.registerEvents(data, this);
        pm.registerEvents(kiteditor, this);
        pm.registerEvents(playerm, this);
        pm.registerEvents(new ServerPingListener(), this);
        pm.registerEvents(spec, this);
    }

    public LadderManager getLadderManager(){
        return this.ladder;
    }

    public PlayerManager getPlayerManager(){
        return this.playerm;
    }

    public KitManager getKitManager(){
        return this.kit;
    }

    public DuelManager getDuelManager(){
        return this.duel;
    }

    public MatchManager getMatchManager(){
        return this.match;
    }

    public PlayerDataManager getPlayerDataManager(){
        return this.data;
    }

    public KitEditor getKitEditor(){
        return this.kiteditor;
    }

    public SpectatorManager getSpec(){
        return this.spec;
    }

    public Database getSQL(){
        return database;
    }

    public static Main getInstance(){
        return instance;
    }
}

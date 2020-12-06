package com.weswaas.training.data;

import com.weswaas.training.game.ladder.Ladder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Weswas on 28/12/2016.
 */
public class Database {

    private String urlbase, host, user, pass, database;
    private Connection connection;

    public Database(String urlbase, String host, String database, String user, String pass) {
        this.urlbase = urlbase;
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.database = database;
    }

    public void connect(){
        try {
            System.out.println("Connecting to database...");
            this.connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
            System.out.println("Sucessfully connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isConnected(){
        try{
            if((connection == null) || connection.isClosed() || (!connection.isValid(5))){
                return false;
            }else{
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() {
        if (!isConnected()) {
            connect();
        }

        return this.connection;
    }

    public void createTable(String name, HashMap<String, SQLType> types){

        StringBuilder list = new StringBuilder("");
        int i = 1;

        for(String s : types.keySet()){

            SQLType type = types.get(s);

            if(i == 1){
                list.append(s + " " + type.getSynthax());
            }else{
                list.append(", " + s + " " + type.getSynthax());
            }

            i++;
        }

        try{
            PreparedStatement sts = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + name + " (" + list.toString().trim() + ")");
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void insert(String table, HashMap<String, Object> map){
        StringBuilder list = new StringBuilder("");
        int i = 1;

        for(String s : map.keySet()){
            if(i == 1){
                list.append(s);
            }else{
                list.append(", " + s);
            }
            i++;
        }

        StringBuilder list2 = new StringBuilder("");
        int i2 = 1;

        for(Object s : map.values()){
            if(i2 == 1){
                list2.append("'" + s + "'");
            }else{
                list2.append(", '" + s + "'");
            }

            i2++;
        }

        try{
            PreparedStatement sts = getConnection().prepareStatement("INSERT INTO " + table + " (" + list.toString().trim() + ") VALUES (" + list2.toString().trim() + ")");
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void update2(String table, String what, String newValue, String where, String answer){
        try{
            PreparedStatement sts = getConnection().prepareStatement("UPDATE " + table + " SET " + what + " = '" + newValue + "' WHERE " + where + " = '" + answer + "'");
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void update(String table, HashMap<String, Object> map, String condition, String answer){
        StringBuilder list = new StringBuilder("");
        int i = 1;

        for(String s : map.keySet()){
            if(i == 1){
                list.append(s + "= '" + map.get(s) + "'");
            }else{
                list.append(", " + s + "= '" + map.get(s) + "'");
            }
            i++;
        }

        try{
            String s = "UPDATE " + table + " SET " + list.toString().trim() + " WHERE " + condition + " = '" + answer + "'";
            PreparedStatement sts = getConnection().prepareStatement(s);
            sts.executeUpdate();
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public Integer getInteger(String table, String what, String condition, String answer){
        int o = 1400;

        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT " + what + " FROM " + table + " WHERE " + condition + " = '" + answer + "'");
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                o = rs.getInt(what);
            }
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return o;
    }

    public String getString(String table, String what, String condition, String answer){
        String o = "";

        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT " + what + " FROM " + table + " WHERE " + condition + " = '" + answer + "'");
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                o = rs.getString(what);
            }
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return o;
    }

    public ArrayList<Integer> getList(String table, String what){
        ArrayList<Integer> list = new ArrayList<>();
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT " + what + " FROM " + table);
            ResultSet rs = sts.executeQuery();
            while(rs.next()){
                list.add(rs.getInt(what));
            }
            sts.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean contains(String table, String check, String condition, String answer){
        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT " + check + " FROM " + table + " WHERE " + condition + "='" + answer + "'");
            ResultSet rs = sts.executeQuery();
            boolean contains = rs.next();
            sts.close();
            return contains;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public HashMap<String, Integer> getAllRatings(Ladder ladder){
        HashMap<String, Integer> map = new HashMap<>();

        ArrayList<Integer> ratings = getList("datas", ladder.getName().toLowerCase());

        for(int i : ratings){
            String uuid = getString("datas", "uuid", ladder.getName().toLowerCase(), String.valueOf(i));
            map.put(uuid, i);
        }

        return map;

    }

}

package com.weswaas.training;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Weswas on 25/04/2020.
 */
public enum ServerMode {

    ONLINE, MAINTENANCE, OFFLINE;

    public static ServerMode serverMode = ServerMode.MAINTENANCE;


    public static void setServerMode(ServerMode mode){
        serverMode = mode;
    }

    public static ServerMode getServerMode(){
        return serverMode;
    }

    public static String getServerModeToString(){
        return StringUtils.capitalize(getServerMode().toString());
    }

}

package com.weswaas.training.data;

/**
 * Created by Weswas on 28/12/2016.
 */
public enum SQLType {

        INTEGER("INTEGER NOT NULL DEFAULT 1400"),
        STRING("VARCHAR (555)");

        private String synthax;

        SQLType(String synthax){
            this.synthax = synthax;
        }

        public String getSynthax(){
            return this.synthax;
        }

}

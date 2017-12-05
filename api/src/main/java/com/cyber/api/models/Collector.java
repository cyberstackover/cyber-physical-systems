package com.cyber.api.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Collector {

    private String database;

    private String table;

    private List<Map> data;

    public Collector(){
        
    }
    
    public Collector(String database, String table, List<Map> data) {
        this.database = database;
        this.table = table;
        this.data = data;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }

    public static String toJson(Collector data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }
    
    public static Collector fromJson(String json) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Collector.class);
    }
}

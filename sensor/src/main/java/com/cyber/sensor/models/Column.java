package com.cyber.sensor.models;

public class Column {
 
    private String name;
    
    private String type;
    
    private String nullValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    @Override
    public String toString() {
        return "Column{" + "name=" + name + ", type=" + type + ", nullValue=" + nullValue + '}';
    }
    
    
}

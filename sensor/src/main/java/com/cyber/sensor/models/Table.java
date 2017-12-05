package com.cyber.sensor.models;

import java.util.List;
import java.util.Map;

public class Table {

    private String filename;

    private String driver;

    private String database;

    private String tableName;

    private String columnSeparator;

    private String lineSeparator;

    private List<Column> columns;

    private List<Map> data;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnSeparator() {
        return columnSeparator;
    }

    public void setColumnSeparator(String columnSeparator) {
        this.columnSeparator = columnSeparator;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Map> getData() {
        return data;
    }

    public void setData(List<Map> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Table{" + "filename=" + filename + ", driver=" + driver + ", database=" + database + ", tableName=" + tableName + ", columnSeparator=" + columnSeparator + ", lineSeparator=" + lineSeparator + ", columns=" + columns + "}\n" + data;
    }

}

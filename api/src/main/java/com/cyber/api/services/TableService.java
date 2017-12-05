package com.cyber.api.services;

import com.cyber.api.repository.HiveRepository;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    @Autowired
    private HiveRepository hiveRepository;

    public List findAll(String database) {
        return hiveRepository.getAllTables(database);
    }

    /**
     *
     * @param database
     * @param table
     * @return
     */
    public List<Map> describe(String database, String table) {
        return hiveRepository.describe(database, table);
    }

    /**
     *
     * @param database
     * @param table
     * @return
     */
    public List<Map> getColumns(String database, String table) {
        return hiveRepository.getColumn(database, table);
    }

    /**
     * create table
     *
     * @param table
     */
    public void create(Table table) throws SerDeException {
        hiveRepository.createTable(table);
    }

    /**
     * drop table
     *
     * @param database
     * @param table
     */
    public void drop(String database, String table) {
        hiveRepository.dropTable(database, table);
    }

}

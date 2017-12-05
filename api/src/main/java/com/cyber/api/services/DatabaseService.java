package com.cyber.api.services;

import com.cyber.api.repository.HiveRepository;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hive.metastore.api.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private HiveRepository hiveRepository;
    
    public String create(Database database){
        hiveRepository.createDatabase(database.getName());
        return database.getName();
    }

    
    public void drop(String name){
        hiveRepository.dropDatabase(name);
    }

    public List<Map> findAll(){
        return hiveRepository.getAllDatabases();
    }
}

package com.cyber.api.services;

import com.cyber.api.services.TableService;
import com.cyber.api.services.DatabaseService;
import com.cyber.api.Application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.hadoop.hive.metastore.api.AlreadyExistsException;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
public class TableServiceTest {

    //@Autowired
    TableService instance;

    //@Autowired
    DatabaseService databaseService;

    public TableServiceTest() throws ClassNotFoundException, SQLException {
//        Connection conn;
//        Properties prop = new Properties();
//        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
//        conn = DriverManager.getConnection("jdbc:phoenix:hadoop-master");
//        System.out.println("got connection");
//        ResultSet rst = conn.createStatement().executeQuery("select * from WARGA order by nama_lgkp limit 10");
//        while (rst.next()) {
//            System.out.println(rst.getString(1) + " " + rst.getString(2) + " " + rst.getString(3));
//        }
        
     }

    /**
     * Test of findAll method, of class TableService.
     */
    //@Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        String database = "matagaruda_test";
        Database db = new Database();
        db.setName(database);
        databaseService.create(db);
        List<Map> result = instance.findAll(database);
        for (Map result1 : result) {
            System.out.println("table : " + result1);
        }
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of findOne method, of class TableService.
     */
    //@Test
    public void testFindOne() throws Exception {
        String database = "default";
        String table = "employees";
        List<Map> result = instance.describe(database, table);
        System.out.println("find one : " + result);
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of create method, of class TableService.
     */
    //@Test
    public void testCreate() throws Exception {
        System.out.println("create");
        String database = "matagaruda_test";
        Database db = new Database();
        db.setName(database);
        databaseService.create(db);
        Table table = new Table();
        List<FieldSchema> cols = new ArrayList<>();
        cols.add(new FieldSchema("id", "int", ""));
        cols.add(new FieldSchema("name", "string", ""));

        StorageDescriptor sd = new StorageDescriptor();
        sd.setCols(cols);
        
        table.setDbName(database);
        table.setTableName("tabelku");
        table.setSd(sd);
        table.putToParameters("CONSTRAINT PK PRIMARY KEY", "id");
        instance.create(table);
        instance.drop(database, "tabelku");
        databaseService.drop(database);
        // TODO review the generated test code and remove the default call to fail.
    }

}

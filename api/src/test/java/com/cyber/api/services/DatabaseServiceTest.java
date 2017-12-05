package com.cyber.api.services;

import com.cyber.api.Application;
import com.cyber.api.services.DatabaseService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.AlreadyExistsException;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.thrift.TException;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
public class DatabaseServiceTest {

//    @Autowired
//    private DatabaseService databaseService;
//
//    @Autowired
//    HiveMetaStoreClient hiveMetaStoreClient;
//
//    @Autowired
//    HBaseDatabase hbaseDatabase;
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @Before
//    public void setUp() {
//
//    }
//
//    @Test
//    public void testFindAll() throws MetaException, IOException {
//        System.out.println("find all hive database");
//        List<String> databases = hiveMetaStoreClient.getAllDatabases();
//        System.out.println(databases.toString());
//        assertNotNull(databases);
//        
//        hbaseDatabase.describeTable();
//    }
//
//    @Test
//    public void testCreate() throws MetaException, AlreadyExistsException, TException {
//        try {
//            Database schema = new Database();
//            schema.setName("matagaruda");
//
//            String name = databaseService.create(schema);
//
//            databaseService.drop(name);
//        } catch (DbException ex) {
//            Logger.getLogger(DatabaseServiceTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DatabaseServiceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

}

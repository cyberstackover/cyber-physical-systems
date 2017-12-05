package com.cyber.sensor;

import com.cyber.sensor.Utils;
import com.cyber.sensor.models.Column;
import com.cyber.sensor.models.Table;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class UtilsTest {

    public UtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of readYaml method, of class Utils.
     */
    @Test
    public void testReadYaml() throws Exception {

        Table table = new Table();
        table.setDriver("csv");
        table.setTableName("bro");
        table.setFilename("test.csv");
        table.setDatabase("matagaruda");
        table.setLineSeparator("\n");
        table.setColumnSeparator("\t");

        Column column = new Column();
        column.setName("id");
        column.setType("String");
        column.setNullValue("-");

        Column column1 = new Column();
        column.setName("name");
        column.setType("String");
        column.setNullValue("-");

        List<Column> col = new ArrayList<Column>();
        col.add(column);
        col.add(column1);
        table.setColumns(col);

        Table table2 = new Table();
        table2.setDriver("csv");
        table2.setTableName("bro");
        table2.setFilename("test.csv");
        table2.setDatabase("matagaruda");
        table2.setLineSeparator("\n");
        table2.setColumnSeparator("\t");
        Column column3 = new Column();
        column.setName("id");
        column.setType("String");
        column.setNullValue("-");

        Column column4 = new Column();
        column.setName("name");
        column.setType("String");
        column.setNullValue("-");
        table2.setColumns(col);

        List<Column> col2 = new ArrayList<Column>();
        col2.add(column3);
        col2.add(column4);
        table2.setColumns(col2);

        List<Table> tabs = new ArrayList<Table>();
        tabs.add(table);
        tabs.add(table2);

        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(tabs, writer);
        System.out.println(writer.toString());

        assertNotNull(writer.toString());
    }

    @Test
    public void read() throws IOException {
        System.out.println("read from yaml file");
        String doc = Utils.readFromFile("table.yaml");
        Yaml yaml = new Yaml();
        List<Table> object = (List<Table>) yaml.load(doc);
        System.out.println(object.toString());
        assertNotNull(object);
    }

   

    /**
     * Test of truncateFile method, of class Utils.
     */
    @Test
    public void testTruncateFile() throws Exception {
        System.out.println("truncateFile");
        String filename = "truncate.csv";
        Utils.truncateFile(filename);
    }

 
}

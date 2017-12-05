package com.cyber.sensor;

import com.cyber.sensor.DataProsessing;
import com.cyber.sensor.models.Column;
import com.cyber.sensor.models.Table;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataPreparationTest {

    /**
     * Test of getTablesConfiguration method, of class DataPreparation.
     */
    @Test
    public void testGetTablesConfiguration() throws Exception {
        System.out.println("getTablesConfiguration");
        DataProsessing instance = new DataProsessing();
        List<Table> result = instance.getTablesConfiguration();
        System.out.println(result.toString());
        assertNotNull(result);
    }

    /**
     * Test of parseDataFromFile method, of class DataPreparation.
     */
    @Test
    public void testParseDataFromFile() throws Exception {
        System.out.println("parseDataFromFile");
        DataProsessing instance = new DataProsessing();
        List<Table> tab = instance.parseDataFromFile();
        
        System.out.println(tab.toString());
    }
}

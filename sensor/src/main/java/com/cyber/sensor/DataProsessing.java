package com.cyber.sensor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.cyber.sensor.models.Column;
import com.cyber.sensor.models.Table;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class DataProsessing {

    /**
     * 
     */
    public static final String TABLE_CONFIG = "table.yaml";

    /**
     * 
     * @return
     * @throws IOException 
     */
    public List<Table> getTablesConfiguration() throws IOException {
        
        Yaml yaml = new Yaml();
        
        InputStream in = Files.newInputStream(Paths.get(TABLE_CONFIG));
        
        List<Table> tables = (List<Table>) yaml.load(in);
        
        return tables;
    }

    /**
     * 
     * @return
     * @throws IOException 
     */
    public List<Table> parseDataFromFile() throws IOException {
        
        List<Table> tables = getTablesConfiguration();
        
        for (Table table : tables) {
        
            String filename = table.getFilename();
            String columnSeparator = table.getColumnSeparator();
            String lineSeparator = table.getLineSeparator();
            String content = Utils.readFromFile(filename);
            
            List result = parseCsvFile(content, columnSeparator, lineSeparator, table.getColumns());
        
            table.setData(result);
        }
        
        return tables;
    }

    public List parseCsvFile(String file, String columnSeparator, String lineSeparator, List<Column> columns) throws IOException {
        
        CsvSchema.Builder schema = new CsvSchema.Builder()
                .setColumnSeparator(columnSeparator.charAt(0))
                .setLineSeparator(lineSeparator).setUseHeader(true);

        for (Column value : columns) {
            schema.addColumn(value.getName());
        }

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

        ObjectReader map = mapper.reader(Map.class).with(schema.build());
        MappingIterator<Map> iter = map.readValues(file);

        return iter.readAll();
    }
}

package com.cyber.sensor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.yaml.snakeyaml.Yaml;

public class Utils {

    /**
     * convert java to json
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String convertToJson(Object data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }

    
    /**
     * truncate file
     * @param filename
     * @throws IOException 
     */
    public static void truncateFile(String filename) throws IOException {
        
        FileOutputStream fos = new FileOutputStream(new File(filename));
        byte[] b = {};
        
        fos.write(b);
        fos.flush();
        
    }

    /**
     * read file
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static String readFromFile(String filename) throws IOException {
        BufferedReader br = null;
        String content = "";
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(filename));

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.startsWith("#")) {
                    continue;
                }
                content += "\n";
                content += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return content;
    }

    
    /**
     * parse yaml file 
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static Object getConfigureYaml(String filename) throws FileNotFoundException, IOException {
        String doc = readFromFile(filename);
        Yaml yaml = new Yaml();
        Object object = yaml.load(doc);
        return object;
    }
}

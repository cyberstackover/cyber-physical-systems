package com.cyber.sensor;

import com.cyber.Matagaruda;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.sensor.models.Table;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class JobWriter {

    Logger LOG = Logger.getLogger(JobWriter.class.getName());

    @Autowired
    private Matagaruda matagaruda;

    /**
     * ingest snort to hadoop
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws MatagarudaException
     */
    @Scheduled(fixedRate = 50000)
    public void write() throws URISyntaxException, IOException, MatagarudaException {
        DataProsessing data = new DataProsessing();
        List<Table> tables = data.parseDataFromFile();

        for (Table table : tables) {
            if (!table.getData().isEmpty()) {
                String json = Utils.convertToJson(table.getData());
                String url = "/datacollector/" + table.getDatabase() + "/" + table.getTableName();
                Utils.truncateFile(table.getFilename());
                matagaruda.postJson(url, json);
            }
        }
    }

}

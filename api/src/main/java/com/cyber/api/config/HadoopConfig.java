package com.cyber.api.config;

import com.cyber.api.hadoop.fs.HDFSClient;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HadoopConfig {

    @Bean
    public HDFSClient hdfsClient() throws IOException {
        return new HDFSClient("core-site.xml", "hdfs-site.xml", "mapred-site.xml");
    }

}

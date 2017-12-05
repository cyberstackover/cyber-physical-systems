package com.cyber.api.controllers.v1.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.cyber.api.job.DataCollectorWorker;
import com.cyber.api.models.Collector;
import com.cyber.api.services.DatabaseService;
import com.cyber.api.utils.JsonResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/v1")
@RestController
public class DataCollectorController {

    @Autowired
    private StringRedisTemplate template;

    @RequestMapping(value = "/datacollector/{database}/{table}", method = RequestMethod.POST)
    public JsonResponse index(@RequestBody List<Map> body,
            @PathVariable String database,
            @PathVariable String table) throws JsonProcessingException {
        
        Collector data = new Collector(database, table, body);
        template.convertAndSend("datacollector", Collector.toJson(data));
        
        return JsonResponse.render(HttpStatus.OK, "");
    }

}

package com.cyber.api.controllers.v1.api;

import com.cyber.api.services.DatabaseService;
import com.cyber.api.utils.JsonResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.NoSuchObjectException;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/v1")
@RestController
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;
   
    @RequestMapping(value = "/databases")
    public JsonResponse index() {
        
        List<Map> databases = databaseService.findAll();
        
        return JsonResponse.render(HttpStatus.OK, databases);
    }

    @RequestMapping(value = "/databases", method = RequestMethod.POST)
    public JsonResponse store(@RequestBody Database database) throws IOException, MetaException, TException {
    
        databaseService.create(database);
        String message = "Database was successfully added.";
        
        return JsonResponse.render(HttpStatus.OK, message);
    }

    @RequestMapping(value = "/databases/{name}", method = RequestMethod.DELETE)
    public JsonResponse delete(@PathVariable String name) throws  IOException, NoSuchObjectException, TException {
        
        databaseService.drop(name);
        
        return JsonResponse.render(HttpStatus.OK, "Database dropped");
    }
}

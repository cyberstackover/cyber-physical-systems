package com.cyber.api.controllers.v1.api;

import com.cyber.api.services.TableService;
import com.cyber.api.utils.JsonResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.NoSuchObjectException;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/v1")
@RestController
public class TableController {

    @Autowired
    private TableService tableService;

    @RequestMapping(value = "/tables/{database}")
    public JsonResponse index(@PathVariable String database) {
        List<Map> tables = tableService.findAll(database);
        return JsonResponse.render(HttpStatus.OK, tables);
    }

    @RequestMapping(value = "/tables/{database}", method = RequestMethod.POST)
    public JsonResponse store(@PathVariable String database, @RequestBody Map<String, Object> data) throws
            IOException, MetaException, TException, SerDeException {

        Table table = new Table();
        StorageDescriptor sd = new StorageDescriptor();
        String primaryKeys = "";
        List<FieldSchema> cols = new ArrayList<>();
        List<Map<String, Object>> fields = (List<Map<String, Object>>) data.get("fields");
        boolean needsComma = false;

        for (Map<String, Object> field : fields) {
            String fieldName = field.get("name").toString();
            String fieldType = field.get("type").toString();
            cols.add(new FieldSchema(fieldName, fieldType, ""));
            boolean isPrimaryKey = false;

            if (field.containsKey("primary_key")) {
                isPrimaryKey = (boolean) field.get("primary_key");
            }

            if (needsComma && isPrimaryKey) {
                primaryKeys += ",";
            }

            if (isPrimaryKey) {
                primaryKeys += fieldName;
            }
            needsComma = true;
        }

        sd.setCols(cols);

        table.setDbName(database);
        table.setTableName(data.get("name").toString());
        table.setSd(sd);
        table.putToParameters("CONSTRAINT PK PRIMARY KEY", primaryKeys);
        tableService.create(table);
        String message = "Table was successfully added.";
        return JsonResponse.render(HttpStatus.OK, message);
    }

    @RequestMapping(value = "/tables/describe/{database}/{table}", method = RequestMethod.GET)
    public JsonResponse describe(@PathVariable String database, @PathVariable String table) {
        List<Map> member = tableService.describe(database, table);
        return JsonResponse.render(HttpStatus.OK, member);
    }

    @RequestMapping(value = "/tables/columns/{database}/{table}", method = RequestMethod.GET)
    public JsonResponse columns(@PathVariable String database, @PathVariable String table) {
        List<Map> member = tableService.getColumns(database, table);
        return JsonResponse.render(HttpStatus.OK, member);
    }

    @RequestMapping(value = "/tables/{database}/{table}", method = RequestMethod.DELETE)
    public JsonResponse delete(@PathVariable String database, @PathVariable String table) throws IOException, NoSuchObjectException, TException, NoSuchFieldException {
        tableService.drop(database, table);
        return JsonResponse.render(HttpStatus.OK, "Table dropped");
    }
}

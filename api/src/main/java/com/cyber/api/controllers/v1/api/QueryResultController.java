package com.cyber.api.controllers.v1.api;

import com.cyber.api.models.SavedQuery;
import com.cyber.api.models.QueryResult;
import com.cyber.api.repository.QueryResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyber.api.repository.SavedQueryRepository;
import com.cyber.api.services.QueryResultService;
import com.cyber.api.utils.JsonResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.http.HttpStatus;

@RequestMapping(value = "/v1")
@RestController
public class QueryResultController {

    @Autowired
    private QueryResultService queryResultService;

    @Autowired
    private SavedQueryRepository savedQueryRepository;

    @RequestMapping(value = "/query-result/{queryId}", method = RequestMethod.GET)
    public JsonResponse index(@PathVariable Integer queryId,
            @RequestParam("start") String starttime,
            @RequestParam("end") String endtime) throws ParseException {

        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        Date start = ft.parse(starttime);
        Date end = ft.parse(endtime);

        List<Map<String, Object>> result = queryResultService.findResultByDate(queryId, start, end);
        return JsonResponse.render(HttpStatus.OK, result);
    }

    @RequestMapping(value = "/query-result-by-name/{name}", method = RequestMethod.GET)
    public JsonResponse query(@PathVariable String name) throws ParseException {
        
        List<Map> data = new ArrayList<>();
        
        SavedQuery result = savedQueryRepository.findByName(name);
        
        for (QueryResult queryResult : result.getQueryResult()) {
            data.addAll(queryResult.getResult());
        }
        return JsonResponse.render(HttpStatus.OK, data);
    }

}

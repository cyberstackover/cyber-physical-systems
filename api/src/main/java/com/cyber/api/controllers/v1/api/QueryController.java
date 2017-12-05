package com.cyber.api.controllers.v1.api;

import com.cyber.api.models.SavedQuery;
import com.cyber.api.models.QueryResult;
import com.cyber.api.services.SavedQueryService;
import com.cyber.api.utils.JsonResponse;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cyber.api.repository.SavedQueryRepository;

@RequestMapping(value = "/v1")
@RestController
public class QueryController {

    @Autowired
    private SavedQueryService savedQueryService;

    @Autowired
    private SavedQueryRepository savedQueryRepository;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public QueryResult query(@RequestBody SavedQuery query) {
        QueryResult result = savedQueryService.execute(query);
        return result;
    }

    @RequestMapping(value = "/query/preview/{database}/{table}", method = RequestMethod.GET)
    public JsonResponse previewData(@PathVariable String database,
            @PathVariable String table,
            Pageable pageable) {
        
        SavedQuery query = new SavedQuery();
        
        String sql = String.format("SELECT * FROM %s.%s LIMIT %d", database, table, pageable.getPageSize());
        query.setSql(sql);
        
        QueryResult result = savedQueryService.execute(query);
        return JsonResponse.render(HttpStatus.OK, result.getResult());
    }

    @RequestMapping(value = "/query/saved", method = RequestMethod.GET)
    public Page<SavedQuery> savedQuery(Pageable pageable) {
        Page<SavedQuery> result = savedQueryRepository.findByNameIsNotNull(pageable);
        return result;
    }

    @RequestMapping(value = "/query/store", method = RequestMethod.POST)
    public JsonResponse store(@RequestBody SavedQuery query) {
        savedQueryService.storeQuery(query);
        return JsonResponse.render(HttpStatus.OK, "saved");
    }

    @RequestMapping(value = "/query/history", method = RequestMethod.GET)
    public Page<SavedQuery> historyQuery(Pageable pageable, Principal principal) {
        Page<SavedQuery> result = savedQueryRepository.findByNameIsNull(pageable);
        return result;
    }

    @RequestMapping(value = "query/{id}", method = RequestMethod.DELETE)
    public JsonResponse delete(@PathVariable Integer id) {
        savedQueryRepository.delete(id);
        return JsonResponse.render(HttpStatus.OK, "query deleted");
    }
}

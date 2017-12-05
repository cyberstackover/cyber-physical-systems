package com.cyber.api.controllers.v1.api;

import com.cyber.api.mahout.KMeansClustering;
import com.cyber.api.utils.JsonResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(value = "/v1")
@RestController
public class KmeansController {
/*
    private static final Logger LOG = LoggerFactory.getLogger(KmeansController.class);

    @RequestMapping(value = "kmeans", method = RequestMethod.GET)
    public List<Map> kmeans(@RequestParam(value = "number_of_k") int number_of_k) throws IOException, InterruptedException, Exception {
        List<Map> data = KMeansClustering.run(number_of_k);
        return data;
    }
*/
}

package com.cyber.api.controllers.v1.api;

import com.cyber.api.utils.JsonResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(value = "/v1")
@RestController
public class JobTrackerStatController {

    private static final Logger LOG = LoggerFactory.getLogger(JobTrackerStatController.class);

    @RequestMapping(value = "jobtracker/nodes", method = RequestMethod.GET)
    public JsonResponse nodes(String path) throws IOException, InterruptedException {
        return JsonResponse.render(HttpStatus.OK, "");
    }

}

package com.cyber.api.controllers.v1.api;

import com.cyber.api.models.Device;
import com.cyber.api.repository.DeviceRepository;
import com.cyber.api.services.DeviceService;
import com.cyber.api.utils.JsonResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/v1")
@RestController
public class DevicesController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "devices/client-credentials", method = RequestMethod.GET)
    public JsonResponse client() {
        
        List<Device> oauthClientDetails = deviceRepository.findByClientCredentials();
        
        return JsonResponse.render(HttpStatus.OK, oauthClientDetails);
    }
    
    @RequestMapping(value = "devices")
    public Page<Device> index(Pageable pageable) {
        Page<Device> oauthClientDetails = deviceRepository.findAll(pageable);
        return oauthClientDetails;
    }

    @RequestMapping(value = "devices", method = RequestMethod.POST)
    public JsonResponse store(@RequestBody Device device) {
        deviceService.save(device);
        String message = "devices was successfully added.";
        return JsonResponse.render(HttpStatus.OK, message);
    }

    @RequestMapping(value = "devices/{id}", method = RequestMethod.GET)
    public JsonResponse edit(@PathVariable String id) {
        Device oauthClientDetails = deviceRepository.findOne(id);
        return JsonResponse.render(HttpStatus.OK, oauthClientDetails);
    }

    @RequestMapping(value = "devices/{id}", method = RequestMethod.PUT)
    public JsonResponse update(@RequestBody Device device) {
        deviceService.save(device);
        return JsonResponse.render(HttpStatus.OK, device);
    }

    @RequestMapping(value = "devices/{id}", method = RequestMethod.DELETE)
    public JsonResponse delete(@PathVariable String id) {
        deviceRepository.delete(id);
        return JsonResponse.render(HttpStatus.OK, "OAuthClientDetails deleted");
    }
}

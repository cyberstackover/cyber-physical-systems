package com.cyber.api.controllers.v1.api;

import com.cyber.api.models.Activity;
import com.cyber.api.repository.OAuthAccessTokenRepository;
import com.cyber.api.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/v1")
@RestController
public class OAuthAccessTokenController {

    @Autowired
    private OAuthAccessTokenRepository oauthAccessTokenRepository;

    @RequestMapping(value = "oauth-access-token")
    public Page<Activity> index(Pageable pageable) {
        Page<Activity> oauthClientDetails = oauthAccessTokenRepository.findAll(pageable);
        return oauthClientDetails;
    }

    @RequestMapping(value = "oauth-access-token/store", method = RequestMethod.POST)
    public JsonResponse store(@ModelAttribute Activity oauthClientDetails) {
        oauthAccessTokenRepository.save(oauthClientDetails);
        String message = "oauthClientDetails was successfully added.";
        return JsonResponse.render(HttpStatus.OK, message);
    }

    @RequestMapping(value = "oauth-access-token/{id}", method = RequestMethod.GET)
    public JsonResponse edit(@PathVariable String id) {
        Activity oauthClientDetails = oauthAccessTokenRepository.findOne(id);
        return JsonResponse.render(HttpStatus.OK, oauthClientDetails);
    }

    @RequestMapping(value = "oauth-access-token/{id}", method = RequestMethod.POST)
    public JsonResponse update(@ModelAttribute Activity oauthClientDetails) {
        oauthAccessTokenRepository.save(oauthClientDetails);
        return JsonResponse.render(HttpStatus.OK, oauthClientDetails);
    }

    @RequestMapping(value = "oauth-access-token/delete/{id}", method = RequestMethod.GET)
    public JsonResponse delete(@PathVariable String id) {
        oauthAccessTokenRepository.delete(id);
        return JsonResponse.render(HttpStatus.OK, "OAuthAccessToken deleted");
    }
}

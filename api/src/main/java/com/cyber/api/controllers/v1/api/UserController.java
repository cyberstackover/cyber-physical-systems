package com.cyber.api.controllers.v1.api;

import com.cyber.api.models.Authority;
import com.cyber.api.models.User;
import com.cyber.api.repository.UserRepository;
import com.cyber.api.services.UserService;
import com.cyber.api.utils.JsonResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/v1")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object me(Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;

        return authentication.getUserAuthentication();
    }

    @RequestMapping(value = "/users")
    public Page<User> index(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public JsonResponse store(@RequestBody Map<String, Object> model) {
        User user = new User();
        user.setUsername(model.get("username").toString());
        user.setPassword(model.get("password").toString());
        user.setEnabled((Boolean) model.get("enabled"));
        List authorities = (ArrayList) model.get("authority");
        userService.save(user, authorities);

        String message = "User was successfully added.";
        return JsonResponse.render(HttpStatus.OK, message);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public JsonResponse edit(@PathVariable Integer id) {
        User user = userRepository.findOne(id);
        return JsonResponse.render(HttpStatus.OK, user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public JsonResponse update(@RequestBody User user, @RequestBody List authorities) {
        userService.save(user, authorities);
        return JsonResponse.render(HttpStatus.OK, user);
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
    public JsonResponse delete(@PathVariable Integer id) {
        userRepository.delete(id);
        return JsonResponse.render(HttpStatus.OK, "User deleted");
    }
}

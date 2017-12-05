package com.cyber.account.controllers;

import com.cyber.account.models.User;
import com.cyber.account.repository.UserRepository;
import com.cyber.account.utils.JsonResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("home");
    }
}

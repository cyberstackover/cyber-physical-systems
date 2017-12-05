package com.cyber.account.controllers;

import java.util.Optional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam Optional<String> error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        
        if (logout == null) {
            Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);
                return "redirect:/";
            }
        }
        model.addAttribute("error", error);
        return "login";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        return "resgiter";
    }

}

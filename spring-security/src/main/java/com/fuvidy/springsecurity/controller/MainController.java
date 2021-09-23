package com.fuvidy.springsecurity.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping("/main")
    public ModelAndView toMainPage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("main");
        view.addObject("username", userName);
        return view;
    }
    @GetMapping("/home")
    public String toHome() {
        return "home";
    }

    @GetMapping("/error")
    public String toErrorPage() {
        return "error/error";
    }
}

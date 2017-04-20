package com.video.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by kotabek on 4/20/17.
 */
@Controller
public class LoginController {

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public ModelAndView indexPage() {
        RedirectView rv = new RedirectView("/login");
        rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView(rv);
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        return new ModelAndView("index");
    }


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboardPage() {
        return new ModelAndView("dashboard");
    }
}

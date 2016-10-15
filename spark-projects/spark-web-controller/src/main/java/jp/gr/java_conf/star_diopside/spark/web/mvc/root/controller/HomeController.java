package jp.gr.java_conf.star_diopside.spark.web.mvc.root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("home")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String show() {
        return "home";
    }
}

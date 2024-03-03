package com.t4a;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {
    @GetMapping("/")
    public ModelAndView indexHTML(ModelMap model) {
        model.addAttribute("attribute", "forwardWithForwardPrefix");
        return new ModelAndView("forward:/index.html", model);
    }
}

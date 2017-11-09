package com.tiago.cooHomeless.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index")
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
    public String Index(Model model) {
        String mensagem = "Coo Homeless Web";
        
        model.addAttribute("mensagem", mensagem);
        
        return "/template/index.html";
    }
}

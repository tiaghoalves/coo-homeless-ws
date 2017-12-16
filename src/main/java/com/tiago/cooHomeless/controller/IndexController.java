package com.tiago.cooHomeless.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@RequestMapping(value =" /index", produces = MediaType.TEXT_HTML_VALUE)
    public String index(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        String mensagem = "Coo Homeless Web";
        
        model.addAttribute("mensagem", mensagem);
        model.addAttribute("name", name);
        return "index";
    }
}

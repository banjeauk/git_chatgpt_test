package com.chatgpt_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@GetMapping("/cgt")
	@ResponseBody
	public String index() {
        return "되네";
    }
}

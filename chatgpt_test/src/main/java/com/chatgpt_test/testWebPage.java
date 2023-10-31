package com.chatgpt_test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testWebPage {
	@GetMapping("/webtest")
    @ResponseBody
    public String webtest() {
		return "호출 완료";
	}

}

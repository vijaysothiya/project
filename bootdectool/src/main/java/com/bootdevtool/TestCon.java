package com.bootdevtool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestCon {
	@RequestMapping("/test")
	@ResponseBody
	public String test()
	{
     return "ready";
     }
}

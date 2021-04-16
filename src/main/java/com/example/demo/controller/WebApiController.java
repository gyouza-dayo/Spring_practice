package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("api")
public class WebApiController {

	@RequestMapping("hello")
	public String hello() {
		return "Hello! Spring!!";
	}

	@RequestMapping("/test/{param}")
	private String testPathVariable(@PathVariable("param") String param) {
		return "受け取ったパラメータ" + param;
	}
	
	//↓ @PathVariableの引数を省略した場合、仮引数名と同じバウントド数名を指定したのと同じ事になる。
	//同名なので省略したパターン
//	@RequestMapping("/test/{param}")
//	private String testPathVariable(@PathVariable String param) {
//		return "受け取ったパラメータ" + param;
//	}
	
	//別名なので指定したパターン
//	@RequestMapping("/test/{name}")
//	private String testPathVariable(@PathVariable("name") String param) {
//		return "受け取ったパラメータ" + param;
//	}
	
}

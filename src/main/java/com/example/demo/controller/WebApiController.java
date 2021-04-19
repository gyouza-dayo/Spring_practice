package com.example.demo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class WebApiController {

	// http://localhost:8080/api/helloというリクエストが送られると実行され、
	// 戻り値のHello! Spring!!がWebページに表示される。
	@RequestMapping(value = "hello")
	public String hello() {
		return "Hello! Spring!!";
	}

	@RequestMapping(value = "/testdayo")
	public String test() {
		return "Hello! test!!";
	}
	
	// http://localhost:8080//test/●●というリクエストが送られると実行され、
	// 戻り値の受け取ったパラメータ●●がWebページに表示される。
	@RequestMapping(value = "/test/{param}")
	private String testPathVariable(@PathVariable("param") String param) {
		return "PathVariable 受け取ったパラメータ " + param;
	}

	// contestroot/test?param=xxxxというリクエストが送られると実行され、
	// 戻り値にURLで受け取ったパラメータを付与した値がWebページに表示される。
	//Mappingは/testだが、 ?param=xxxx の情報が無いと呼び出されない。
	@RequestMapping(value = "/test")
	private String testRequestParam(@RequestParam("param") String param) {
		return "RequestParam 受け取ったパラメータ " + param;
	}

	//POST形式でJSONなどの値を受け取る事が出来る
	//ブラウザ上でPOST送信が出来るアプリを使って動作確認をした。
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	private String testRequestBody(@RequestBody String body) {
		return "受け取ったボディ " + body;
	}
	
	
}

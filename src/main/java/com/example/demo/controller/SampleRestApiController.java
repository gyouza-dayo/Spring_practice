package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;


@RestController
@RequestMapping("/sample/api")
public class SampleRestApiController {
	private static final Logger log = LoggerFactory.getLogger(SampleRestApiController.class);

	@RequestMapping("/test/{param}")
	private String testPathVariable(@PathVariable("param") String param) {
		log.info(param);
		return "受け取ったパラメータ" + param;
	}
	
	@RequestMapping("/test")
	private String testRequestParam(@RequestParam("param") String param) {
		log.info(param);
		return "受け取ったパラメータ：" + param;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	private String testRequestBody(@RequestBody String body) {
		log.info(body);
		return "受け取ったリクエストボディ：" + body;
	}
	
	//登録：CRUDでいう <b>C:CREATE</b> を行うAPI
	@RequestMapping(value="/resource" , method=RequestMethod.POST)
	private String create(@RequestBody String data) {
		return "登録だよ";
	}
	
	//参照：CRUDでいう <b>R:READ</b> を行うAPI。
	@RequestMapping(value="/resource/{id}" , method=RequestMethod.GET)
	private String read(@PathVariable("id") String id) {
		return "参照だよ";
	}
	
	@RequestMapping(value="/resource/{id}" , method=RequestMethod.DELETE)
	private String delete(@PathVariable("id") String id) {
		return "削除だよ";
	}
	
	@RequestMapping(value="/resource/{id}" , method=RequestMethod.PUT)
	private String update(@PathVariable("id") String id, @RequestBody String data) {
		return "更新だよ";
	}
	
//	@ExceptionHandler
//	private ResponseEntity<String> onError( Exception ex ){
//		log.error(ex.getMessage(), ex);
//		
//		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//		
//		Map<String, Object> jsonMap = new HashMap<>();
//		jsonMap.put("message", "API エラー");
//		jsonMap.put("detail", ex.getMessage());
//		jsonMap.put("status", status.value());
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		String json = "";
//		try {
//			json = objectMapper.writeValueAsString(jsonMap);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//				
////		String json = JsonMapper.map()
////				.put("message", "API エラー")
////				.put("detail", ex.getMessage())
////				.put("status", status.value()) 
////				.stringify();
//		
//		return new ResponseEntity<String> (json, status);
//	}
	
	//onErrorメソッドの動作確認用メソッド
	@RequestMapping("test/ex")
	public String testException() {
		throw new RuntimeException("エラー発生");
	}
}

package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * https://weather.tsukumijima.net/api/forecast が元のURI
 * cityパラメータ/地域のid を加えてアクセスして使用する
 */

@RestController("/api")
public class SampleApiController {
	
	@RequestMapping(value="/weather/sagami",
			produces=MediaType.APPLICATION_JSON_VALUE,
			method=RequestMethod.GET)
	public String call() {
		
		
		
		
		
		return null;
	}
}

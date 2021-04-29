package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
		
		RestTemplate rest = new RestTemplate();
		final String cityCode = "140020";
		final String endPoint = "https://weather.tsukumijima.net/api/forecast";
		
		
		return null;
	}
}

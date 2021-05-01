package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
		
		RestTemplate rest = new RestTemplate(); //APIを使う為のインスタンス
		final String cityCode = "140020"; //今回のAPI用の、URIに追加する文字列
		final String endPoint = "https://weather.tsukumijima.net/api/forecast"; //今回のAPIの基本URI

		//https://weather.tsukumijima.net/api/forecast?city=140020 となる
		final String url = endPoint + "?city=" + cityCode;
		
		//RestTemplateインスタンスの .getForEntityメソッドに作成したURIを渡し、
		//その結果を ResponseEntity<E> オブジェクトに受け取る。
		ResponseEntity<String> response = rest.getForEntity(url, String.class);
		
		
		
		return null;
	}
}

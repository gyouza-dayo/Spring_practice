package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyErrorController implements ErrorController{
	
	//Controllerの処理に入る前に起きたエラーを対応してくれる
	@RequestMapping("/error")
	public ResponseEntity<String> handleError(){
		return new ResponseEntity<String> ("メソッドの実行が上手く行ってないよ ;)", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
}

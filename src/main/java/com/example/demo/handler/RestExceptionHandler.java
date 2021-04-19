package com.example.demo.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.controller.SampleRestApiController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class RestExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(SampleRestApiController.class);

	@ExceptionHandler
	private ResponseEntity<String> onError(Exception ex){
		log.error(ex.getMessage(), ex);
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("message", "API エラー");
		jsonMap.put("detail", ex.getMessage());
		jsonMap.put("status", status.value());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "";
		try {
			json = objectMapper.writeValueAsString(jsonMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String> (json, status);
	}
}

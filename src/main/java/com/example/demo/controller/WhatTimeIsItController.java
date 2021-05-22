package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ui.Model;

@RequestMapping("view/what-time-is-it")
@Controller	//@Controllerでのアノテート…画面のコントローラ、ViewControllerの役割。
public class WhatTimeIsItController {
	
	//ViewControllerは基本的にStringで遷移したいView名を返すメソッドを実装する
	//引数に受け取りたい物を持たせておくと、Springが渡せる物を渡してくれる
	
	//HTTPメソッドの GET に対応したMappingアノテーション
	@GetMapping()
	public String view(Model model) {
		//時刻を操作するライブラリLocalDateTimeから、現在時刻を取得
		String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
	
		//引数のmodelに"datetime"をkeyとして先ほど取得した現在時刻を埋め込む。
		model.addAttribute("datetime", now);
		
		// view名を返す ('wtii.html'のwtii)
		return "wtii";
	}
}

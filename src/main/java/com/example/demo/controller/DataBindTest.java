package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@RestController
@RequestMapping("/bind")
public class DataBindTest {

	// 動作確認用
	@GetMapping("test")
	public String test() {
		
		// 現在時刻を取得する
		LocalDateTime now = LocalDateTime.now();

		// "●時"として現在時刻から時間を取り出す
		String hour = now.getHour() + "時";
		// 現在時刻をフォーマットした文字列を取得する
		String timestamp = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(now);

//		return "こんにちは";
		return process(hour, timestamp);
	}

	// 本題のロジック
	private String process(String hour, String timestamp) {
		// テンプレートリゾルバ「テンプレートファイルを解決する為のもの」
		// → テンプレートファイルの置き場所などを設定するオブジェクト
		// Resolverは英語で"解決するもの"
		var resolver = new ClassLoaderTemplateResolver();

		// テンプレートモードを設定。他にHTML、XML、CSS、RAWなどが指定出来る。
		resolver.setTemplateMode(TemplateMode.TEXT);

		// Prefixは英語で接頭辞、敬称など "前に付けるもの" の意味
		// →src からの相対パス。
		resolver.setPrefix("templates/messages/"); // src/main/resources/templates/messages

		// Suffixは英語で接尾辞、"後ろに付けるもの" の意味
		// →拡張子
		resolver.setSuffix(".message");

		// 文字エンコーディングの指定
		resolver.setCharacterEncoding("UTF-8");

		// キャッシュの有効/無効
		resolver.setCacheable(true);

		// テンプレートエンジンに作成したResolverをセット
		// 与えられたResolverの設定に従い、テンプレートファイルを探しに行ってくれる
		var engine = new SpringTemplateEngine();
		engine.setTemplateResolver(resolver);

		// コンテキスト自体の意味は 環境、文脈 だが、ここではパラメータと認識。
		// テンプレートファイルに置いた仮引数(プレースホルダ)に対応して実データを渡している。
		var context = new Context();
		context.setVariable("hour", hour);					// [[${hour}]]
		context.setVariable("timestamp", timestamp); // [[${timestamp}]]

		//SpringTemplateEngineインスタンスにテンプレート名と、パラメータ(context)を渡して
		//.processメソッドを実行すると、
		//Resolverを使って指定されたテンプレートファイル(=プレフィックス+第一引数+サフィックス)を探し
		//第二引数のパラメータでテンプレート処理した結果を返してくれる。
		final String message = engine.process("templateFile", context);
		return message;
	}

}

package com.example.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/*
 * https://weather.tsukumijima.net/api/forecast が元のURI
 * cityパラメータ/地域のid を加えてアクセスして使用する
 */

@RestController
@RequestMapping("/api")
public class SampleApiController {
	// 外部のAPIをコールするHTTPクライアント RestTemplate をインスタンス化
	private final RestTemplate rest;
	{
		//使用するRestTemplateインスタンスのErrorHandlerプロパティに自前のエラーハンドラを設定
		this.rest = new RestTemplate();
		this.rest.setErrorHandler(new QuietlyHandler());
	}
	
	@RequestMapping(value = "/weather/sagami", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String call() {
		// APIの共通URI
		final String endPoint = "https://weather.tsukumijima.net/api/forecast";
		// URIのパラメータ(city)に指定するコード。 欲しいリソースによって変わる
		final String cityCode = "140020";
		
		// https://weather.tsukumijima.net/api/forecast?city=140020 となる
		final String url = endPoint + "?city=" + cityCode;

		// RestTemplateインスタンスの .getForEntityメソッドに作成したURIを渡し、
		// その結果を ResponseEntity<E> オブジェクトに受け取る。
		// 第二引数にStringで内容を受け取るように指定しているが、Beanを指定して直接マップも可能！
		ResponseEntity<String> response = rest.getForEntity(url, String.class);

		// responseの中身をJSON形式に変換
		String json = response.getBody();

		return decode(json);
	}
	
	//これを使用しなかった場合、JSON文字列に \n を含んだそのまま出力されるが、
	//StringEscapeUtilsを用いると \n を改行して出力してくれた。
	private static String decode(String string) {
		return StringEscapeUtils.unescapeJava(string);
	}
	
	/**
	* POST実装例.
	* 
	* 指定したエンドポイントに対して {@code json} データをPOSTし、結果を返す。
	* 
	* @param url     エンドポイント
	* @param headers リクエストヘッダ
	* @param json    送信するJSON文字列
	* @return 正常に通信出来た場合はレスポンスのJSON文字列を、<br>
	*         正常に通信出来なかった場合は {@code null} を返す。 
	*/
	public String post(String url, Map<String, String> headers, String json) {
		//リクエストエンティティに詳細な情報を設定出来るBodyBuilderインスタンスを生成
		//RequestEntityでは、各HTTPメソッドに対応したBodyBuilderの生成APIが提供されている
		RequestEntity.BodyBuilder builder = RequestEntity.post(uri(url));
		
		//引数に受け取ったヘッダ情報をBodyBuilderインスタンスにセットする
		for(String name : headers.keySet()) {	//name に keyを取得
			String header = headers.get(name);	//header に valueを取得
			builder.header(name,header);			//BodyBuilderインスタンスに一行分のヘッダ情報を追加
		}
		
		//作成したBodyBuilderインスタンスから、RequestEntityインスタンスを生成
		RequestEntity<String> request = builder
				.contentType(MediaType.APPLICATION_JSON_UTF8)	//MediaTypeをセット
				.body(json);															//送信するJSONクエリをセット
		
		//RestTemplate.exchange()に、作成したRequestEntityとresponseTypeを指定して
		//ResponseEntityインスタンスを生成
		ResponseEntity<String> response = this.rest.exchange(
				request,
				String.class);
		
		//レスポンスからHTTPステータスコードを取得し、
		//is2xxSuccessfulで通信の成功を示す200番台のコードであるか？の判定を行う。
		//判定がtrueで通信に成功していれば取得したデータ本体、失敗していればnullを返す。
		return response.getStatusCode().is2xxSuccessful() ? response.getBody() : null;
	}
	
	//URL文字列をURIオブジェクトに変換する
	private static final URI uri(String url) {
		try {
			return new URI(url);
		}catch(Exception ex) {
			throw new RuntimeException( ex );
		}
	}
	
	private static class QuietlyHandler extends DefaultResponseErrorHandler{
		@Override
		public void handleError(ClientHttpResponse response) throws IOException{
			//4xx系、5xx系のステータスコードであっても何もしない(NOP処理)
		}		
	}
	

}

package com.example.demo.bean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api")
public class HogeMogeBean {
	private String hoge;
	private int moge;

	public HogeMogeBean(String hoge, int moge) {
		this.hoge = hoge;
		this.moge = moge;
	}

	public HogeMogeBean(String hoge) {
		this(hoge, 1);
	}

	public HogeMogeBean(int moge) {
		this("noName", moge);
	}

	public HogeMogeBean() {
		this("noName", 0);
	}

	public String getHoge() {
		return this.hoge;
	}

	public int getMoge() {
		return this.moge;
	}

	@RequestMapping("/hogemoge")
	public HogeMogeBean hogemoge() {
		return new HogeMogeBean("ほげ", 1234);
	}

	// StringでJSONを返す際、ヘッダの設定を行いたい場合は以下のような実装になる。
	@RequestMapping(value = "/hogemoge2", produces = MediaType.APPLICATION_JSON_VALUE)
	public String string() throws Exception {
		HogeMogeBean bean = new HogeMogeBean("もげ", 315);
		String json = new ObjectMapper().writeValueAsString(bean);
		return json;
	}

	//Beanを用いず、Mapをそのまま返してJSON形式で出力させる方法
	@RequestMapping("/hogemoge3")
	public Map<String, Object> map() {
		Map<String, Object> map = new HashMap<>();
		map.put("hoge", "ぴよ");
		map.put("moge", 999);
		return map;
	}
	
	//画像など、ファイルを送信してWebに表示させる方法
	@RequestMapping(value="/hogemoge4", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public Resource file() {
		return new FileSystemResource(new File("C:\\Users\\Owner\\freedom\\everydays01.gif"));
	}
}

//package com.example.demo.controller;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Properties;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.YouTube.Search;
//import com.google.api.services.youtube.model.ResourceId;
//import com.google.api.services.youtube.model.SearchListResponse;
//import com.google.api.services.youtube.model.SearchResult;
//import com.google.api.services.youtube.model.Thumbnail;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//
//@RestController
//@RequestMapping("/youtube")
//public class ApiCallController {
//	/** Global instance properties filename. */
//	private static String PROPERTIES_FILENAME = "youtube.properties";
//
//	/** Global instance of the HTTP transport. */
//	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
//
//	/** Global instance of the JSON factory. */
//	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
//
//	/**
//	 * Global instance of the max number of videos we want returned (50 = upper
//	 * limit per page).
//	 */
//	private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
//
//	/** Global instance of Youtube object to make all API requests. */
//	private static YouTube youtube;
//
//	@RequestMapping(value = "/youtube/")
//	private String call() {
//		return "";
//	}
//
//	/**
//	 * Initializes YouTube object to search for videos on YouTube
//	 * (Youtube.Search.List). The program then prints the names and thumbnails of
//	 * each of the videos (only first 50 videos).
//	 *
//	 * @param args command line args.
//	 */
//	public static void main(String[] args) {
//		// youtube.propertiesから開発者キーを読む
//		Properties properties = new Properties();
//		try {
//			InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
//			properties.load(in);
//
//		} catch (IOException e) {
//			System.err.println(
//					"There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause() + " : " + e.getMessage());
//			System.exit(1);
//		}
//
//		try {
//			/*
//			 * すべてのAPIリクエストには、YouTubeオブジェクトが使用されます。
//			 * 最後の引数は必須ですが、HttpRequestの初期化時に何も初期化する必要がないため、
//			 * インターフェースをオーバーライドして、no-op関数(何もしない)を提供しています。
//			 */
//			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
//				public void initialize(HttpRequest request) throws IOException {
//				}
//			}).setApplicationName("youtube-cmdline-search-sample").build();
//
//			// ユーザーからのクエリ用語(検索文字列)の取得.
//			String queryTerm = getInputQuery();
//
//			/*
//			 * youtube.search().list("id,snippet")のエラー
//			 * The method list(List<String>) in the type YouTube.Search is not applicable for the arguments (String)
//			 * の解消の為に、一時的にList<String>を引数に渡してみる
//			 */
//			List<String> idSnippetList = new ArrayList<>();
//			idSnippetList.add("id");
//			idSnippetList.add("snippet");
//			YouTube.Search.List search = youtube.search().list(idSnippetList);
//			
//			/*
//			 * 認証されていないリクエストでは、
//			 * Google Developer Console で API キーを設定することが重要です
//			 * (次のリンクの Credentials タブにあります: console.developers.google.com/)
//			 * これは良い習慣であり、あなたのクォータを増加させます。
//			 */
//			String apiKey = properties.getProperty("youtube.apikey");
//			search.setKey(apiKey);
//			search.setQ(queryTerm);
//			
//			/*
//			 * We are only searching for videos (not playlists or channels). If we were
//			 * searching for more, we would add them as a string like this:
//			 * "video,playlist,channel".
//			 */
//			search.setType("video");
//			/*
//			 * This method reduces the info returned to only the fields we need and makes
//			 * calls more efficient.
//			 */
//			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
//			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
//			SearchListResponse searchResponse = search.execute();
//
//			List<SearchResult> searchResultList = searchResponse.getItems();
//
//			if (searchResultList != null) {
//				prettyPrint(searchResultList.iterator(), queryTerm);
//			}
//		} catch (GoogleJsonResponseException e) {
//			System.err.println(
//					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
//		} catch (IOException e) {
//			System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
//		} catch (Throwable t) {
//			t.printStackTrace();
//		}
//	}
//
//	/*
//	 * 端末を介してユーザーが入力したクエリ用語（文字列）を返す
//	 * サンプルではコンソールに入力を受け付ける。
//	 */
//	private static String getInputQuery() throws IOException {
//
//		String inputQuery = "";
//
//		System.out.print("Please enter a search term: ");
//		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
//		inputQuery = bReader.readLine();
//
//		if (inputQuery.length() < 1) {
//			// 何も入力されていない場合は、デフォルトで "YouTube Developers Live "と表示されます。
//			inputQuery = "YouTube Developers Live";
//		}
//		return inputQuery;
//	}
//
//	/*
//	 * Prints out all SearchResults in the Iterator. Each printed line includes
//	 * title, id, and thumbnail.
//	 *
//	 * @param iteratorSearchResults Iterator of SearchResults to print
//	 *
//	 * @param query Search query (String)
//	 */
//	private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
//
//		System.out.println("\n=============================================================");
//		System.out.println("   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
//		System.out.println("=============================================================\n");
//
//		if (!iteratorSearchResults.hasNext()) {
//			System.out.println(" There aren't any results for your query.");
//		}
//
//		while (iteratorSearchResults.hasNext()) {
//
//			SearchResult singleVideo = iteratorSearchResults.next();
//			ResourceId rId = singleVideo.getId();
//
//			// Double checks the kind is video.
//			if (rId.getKind().equals("youtube#video")) {
//				Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().get("default");
//
//				System.out.println(" Video Id" + rId.getVideoId());
//				System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
//				System.out.println(" Thumbnail: " + thumbnail.getUrl());
//				System.out.println("\n-------------------------------------------------------------\n");
//			}
//		}
//	}
//}

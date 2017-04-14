package com.es.study;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;

/**
 * ES索引使用REST方式操作的工具类
 * 
 * @author wyshenfei
 * 
 */
public class ESOperateRestUtils {

	/**
	 * 索引的地址
	 */
	private static final String ES_INDEX_URL = "localhost:9200";

	/**
	 * 索引的scheme
	 */
	private static final String ES_SCHEME_HTTP = "http";

	/**
	 * 保存数据到索引
	 * 
	 * @param indexName
	 *            <li>索引名称</li>
	 * @param typeName
	 *            <li>类型名称</li>
	 * @param id
	 *            <li>索引ID</li>
	 * @param content
	 *            <li>索引内容</li>
	 * @throws URISyntaxException
	 */
	public static HttpResponse saveToIndex(String indexName, String typeName, int id, Object indexContent) {
		HttpResponse response = null;
		HttpClient client = HttpClients.createDefault();
		try {
			// 设置类型
			HttpPut httpPut = new HttpPut(getURI(indexName, typeName, String.valueOf(id)));
			StringEntity stringEntity = new StringEntity(JSON.toJSONString(indexContent), "UTF-8");
			stringEntity.setContentType("application/x-www-form-urlencoded");
			// 设置请求的数据
			httpPut.setEntity(stringEntity);
			response = client.execute(httpPut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 从索引中获取数据
	 * 
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 */
	public static String getFromIndex(String indexName, String typeName, int id) {
		String result = "";
		HttpClient client = HttpClients.createDefault();
		try {
			// 设置类型
			HttpGet httpGet = new HttpGet(getURI(indexName, typeName, String.valueOf(id)));
			HttpResponse response = client.execute(httpGet);
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer(512);
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 从索引中搜索数据 (由于GET不支持请求体，可以直接使用POST`方式来实现)
	 * 
	 * @return
	 */
	public static String searchFromIndex(String indexName, String typeName, String searchText) {
		String result = "";
		HttpClient client = HttpClients.createDefault();
		try {
			// 设置类型
			String url = getURI(indexName, typeName, "_search");
			HttpPost httpPost = new HttpPost(url);
			StringEntity requestEntity = new StringEntity(searchText, "UTF-8");
			requestEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(requestEntity);
			HttpResponse response = client.execute(httpPost);
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer(512);
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取URI地址
	 * 
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 * @throws URISyntaxException
	 */
	private static String getURI(String indexName, String typeName, String customer) throws URISyntaxException {
		StringBuilder pathBuilder = new StringBuilder(64);
		pathBuilder.append("/").append(indexName).append("/").append(typeName).append("/").append(customer);
		URIBuilder builder = new URIBuilder(ES_INDEX_URL);
		builder.setScheme(ES_SCHEME_HTTP);
		builder.setHost(ES_INDEX_URL);
		builder.setPath(pathBuilder.toString());
		return builder.toString();
	}

}
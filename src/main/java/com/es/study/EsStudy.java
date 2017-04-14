package com.es.study;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.es.study.domain.Employee;

/**
 * ES各种使用场景
 * 
 * @author wyshenfei
 * 
 */
public class EsStudy {

	/**
	 * 索引名称
	 */
	private static final String INDEX_NAME = "jdjr-cdyfzx";

	/**
	 * 类型名称
	 */
	private static final String TYPE_NAME = "employee";

	/**
	 * 保存数据到ES
	 */
	@Test
	public void testSaveToEs() {
		Employee employee1 = new Employee("李刚", 50, "男");
		employee1.setAbout("我爸是李刚!");
		ESOperateRestUtils.saveToIndex(INDEX_NAME, TYPE_NAME, 1, employee1);
		Employee employee2 = new Employee("李妹", 25, "女");
		employee2.setAbout("你妹!");
		ESOperateRestUtils.saveToIndex(INDEX_NAME, TYPE_NAME, 2, employee2);
		Employee employee3 = new Employee("王哥", 33, "男");
		employee3.setAbout("我叫帅哥王!");
		ESOperateRestUtils.saveToIndex(INDEX_NAME, TYPE_NAME, 3, employee3);
	}

	/**
	 * 从ES中读取数据
	 */
	@Test
	public void testGetFromES() {
		String resultData = ESOperateRestUtils.getFromIndex(INDEX_NAME, TYPE_NAME, 1);
		JSONObject jsonObject = JSONObject.parseObject(resultData);
		String source = jsonObject.getString("_source");
		Employee employee = JSON.parseObject(source, Employee.class);
		// System.out.println(employee);
	}

	/**
	 * 从ES中搜索数据
	 * 
	 */
	@Test
	public void testSearchFromES() {
		String searchText = "{\"query\" : {\"match\" : {\"name\" : \"王\"}}}";
		String result = ESOperateRestUtils.searchFromIndex(INDEX_NAME, TYPE_NAME, searchText);
		System.out.println(result);
	}

}

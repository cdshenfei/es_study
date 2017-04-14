package com.es.study.domain;

import java.io.Serializable;

/**
 * 员工实体对象
 * 
 * @author wyshenfei
 * 
 */
public class Employee implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 姓名
	 */
	private java.lang.String name;

	/**
	 * 年龄
	 */
	private java.lang.Integer age;

	/**
	 * 性别
	 */
	private java.lang.String gender;

	/**
	 * 个人描述
	 */
	private java.lang.String about;

	/**
	 * 无参构造
	 */
	public Employee() {
	}

	/**
	 * 构造方法
	 * 
	 * @param name
	 * @param age
	 * @param gender
	 */
	public Employee(String name, Integer age, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getAge() {
		return age;
	}

	public void setAge(java.lang.Integer age) {
		this.age = age;
	}

	public java.lang.String getGender() {
		return gender;
	}

	public void setGender(java.lang.String gender) {
		this.gender = gender;
	}

	public java.lang.String getAbout() {
		return about;
	}

	public void setAbout(java.lang.String about) {
		this.about = about;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", gender=" + gender + ", about=" + about + "]";
	}

}
package com.hubena.entity;

import java.io.Serializable;

/**
 * 返回实体类
 *
 * @author 曾谢波
 * @since 2018年11月3日
 */
public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Response [name=" + name + "]";
	}
	
}

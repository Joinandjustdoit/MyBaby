package com.example.mybaby.entity;

import java.io.Serializable;

public class BaseRequestParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 时间戳
	private String jsonrpc;// rpc版本
	private String method;// 请求方法

	public long timestamp; // 请求时间戳
	public String code;
	private String appRequest;
	public String channel;// 请求渠道

	private String timeOut;

	private String token;
	private String formToken;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public BaseRequestParams(String method, String jsonrpc) {
		super();
		this.jsonrpc = jsonrpc;
		this.method = method;
	}

	public BaseRequestParams() {
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAppRequest() {
		return appRequest;
	}

	public void setAppRequest(String appRequest) {
		this.appRequest = appRequest;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFormToken() {
		return formToken;
	}

	public void setFormToken(String formToken) {
		this.formToken = formToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}

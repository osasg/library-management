
package com.quangdat.model;

import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseModel {

	@JsonView(View.Public.class)
	String msg;
	
	@JsonView(View.Public.class)
	String code;
	
	@JsonView(View.Public.class)
	String result;

	public AjaxResponseModel() {
	}
	
	public AjaxResponseModel(String msg, String code, String result) {
		this.msg = msg;
		this.code = code;
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}

package com.vo;

public class SolrArgVo {

	private String condition;
	private String keyWord;
	private String channeCode;
	
	
	public SolrArgVo(String condition, String ketWord , String channeCode) {
		super();
		this.condition = condition.toLowerCase();
		this.keyWord = ketWord;
		this.channeCode = channeCode;
	}
	
	public String getChanneCode() {
		return channeCode;
	}

	public void setChanneCode(String channeCode) {
		this.channeCode = channeCode;
	}

	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getKetWord() {
		return keyWord;
	}
	public void setKetWord(String ketWord) {
		this.keyWord = ketWord;
	}
	
}

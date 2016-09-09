package com.tiantanhehe.yidongchafang.chafangoverview;

import java.util.ArrayList;

public class Xitong {
	private String id;
	private String rentixitong_name;
	private String rentixitong_pinyin;
	private String keyword;
	private String type;
	private String bianjie_tag;
	private String data_scource;
	private String title_tag;
	private String orderby;
	private String conten_type_tag;
	private ArrayList<JianchaResult> lis_result;
	private String shifo_yichang;
	@Override
	public String toString() {
		return "Xitong [id=" + id + ", rentixitong_name=" + rentixitong_name
				+ ", rentixitong_pinyin=" + rentixitong_pinyin + ", keyword="
				+ keyword + ", type=" + type + ", bianjie_tag=" + bianjie_tag
				+ ", data_scource=" + data_scource + ", title_tag=" + title_tag
				+ ", orderby=" + orderby + ", conten_type_tag="
				+ conten_type_tag + ", lis_result=" + lis_result
				+ ", shifo_yichang=" + shifo_yichang + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRentixitong_name() {
		return rentixitong_name;
	}
	public void setRentixitong_name(String rentixitong_name) {
		this.rentixitong_name = rentixitong_name;
	}
	public String getRentixitong_pinyin() {
		return rentixitong_pinyin;
	}
	public void setRentixitong_pinyin(String rentixitong_pinyin) {
		this.rentixitong_pinyin = rentixitong_pinyin;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBianjie_tag() {
		return bianjie_tag;
	}
	public void setBianjie_tag(String bianjie_tag) {
		this.bianjie_tag = bianjie_tag;
	}
	public String getData_scource() {
		return data_scource;
	}
	public void setData_scource(String data_scource) {
		this.data_scource = data_scource;
	}
	public String getTitle_tag() {
		return title_tag;
	}
	public void setTitle_tag(String title_tag) {
		this.title_tag = title_tag;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getConten_type_tag() {
		return conten_type_tag;
	}
	public void setConten_type_tag(String conten_type_tag) {
		this.conten_type_tag = conten_type_tag;
	}
	public ArrayList<JianchaResult> getLis_result() {
		return lis_result;
	}
	public void setLis_result(ArrayList<JianchaResult> lis_result) {
		this.lis_result = lis_result;
	}
	public String getShifo_yichang() {
		return shifo_yichang;
	}
	public void setShifo_yichang(String shifo_yichang) {
		this.shifo_yichang = shifo_yichang;
	}
	
}

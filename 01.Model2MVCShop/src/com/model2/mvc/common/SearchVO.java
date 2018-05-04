package com.model2.mvc.common;


public class SearchVO { // 리스트 보기
	
	private int page;
	String searchCondition;
	String searchKeyword;
	int pageUnit;
	int wantPage;
	
	public SearchVO(){
	}
	
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchCondition() { // 검색 조건
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() { //검색 내용
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getWantPage() {
		return wantPage;
	}

	public void setWantPage(int wantPage) {
		this.wantPage = wantPage;
	}
}
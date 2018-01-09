package com.sinosoft.message;


public class Category {

	private int categoryID;
	private String categoryName;
	private String categoryComments;
	private long relatedPosts;
	private String url;
	

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Category()
	{
		
	}
	public Category(String categoryName, String categoryComments) {
		this.categoryName = categoryName;
		this.categoryComments = categoryComments;
	}
	
	public Category(String categoryName, String categoryComments, int relatedPosts) {
		this.categoryName = categoryName;
		this.categoryComments = categoryComments;
		this.relatedPosts = relatedPosts;
	}

	public long getRelatedPosts() {
		return relatedPosts;
	}
	public void setRelatedPosts(long relatedPosts) {
		this.relatedPosts = relatedPosts;
	}
	
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryComments() {
		return categoryComments;
	}
	public void setCategoryComments(String categoryComments) {
		this.categoryComments = categoryComments;
	}

}

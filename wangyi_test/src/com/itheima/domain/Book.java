package com.itheima.domain;

import java.io.Serializable;

public class Book implements Serializable {
	private Integer id;
	private String name;
	private String author;
	private float price;
	private String path;//���ͼƬ����Ŀ¼
	private String filename;//�ļ�����Ψһ
	private String description;
	//�鼮�ͷ����Ƕ��һ�Ĺ�ϵ
	private Category category;//�鼮��������
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author
				+ ", price=" + price + ", path=" + path + ", filename="
				+ filename + ", description=" + description + "]";
	}
	
}

package com.models.am1;

import java.io.Serializable;

public class Question implements Serializable{
	private String course = "";
	private String subject = "";
	private  String content = "";
	
	public Question(String course, String subject , String content){
		this.course = course;
		this.subject = subject;
		this.content = content;
	}

	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


}

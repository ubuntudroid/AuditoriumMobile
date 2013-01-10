package com.models.am1;

import java.io.Serializable;
/**
 * Question
 * This class is necessary to be implemented Serializable in order to use the Adapter.
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 */
public class Question implements Serializable{
	private String course = "";
	private String subject = "";
	private  String content = "";
	/**
	 * Constructor of the object Question.
	 * @param course String
	 * @param subject String
	 * @param content String
	 */
	public Question(String course, String subject , String content){
		this.course = course;
		this.subject = subject;
		this.content = content;
	}
	/**
	 * This method permits to get Course.
	 * @return String
	 */
	public String getCourse() {
		return course;
	}
	/**
	 * This method permits to set Course.
	 * @param course String
	 */
	public void setCourse(String course) {
		this.course = course;
	}
	/**
	 * This method permits to get Subject.
	 * @return String
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * This method permits to get Subject.
	 * @param subject String
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * This method permits to get Content.
	 * @return String
	 */
	public String getContent() {
		return content;
	}
	/**
	 * This method permits to set Content.
	 * @param content String
	 */
	public void setContent(String content) {
		this.content = content;
	}


}

package com.models.am1;

import java.io.Serializable;
/**
 * Question
 * This class is necessary to be implemented Serializable in order to use the Adapter.
 * Created on November 18, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * updated by Bernardo Plaza
 * last updated Januar 17th, 2013
 */
public class Question implements Serializable{
	private int index = 0;
	private String course = "";
	private String subject = "";
	private String content = "";
	private String author="";
	private String date = "";
	private int numberOfRates=0;
	private int privacy=0;
	/**
	 * Constructor of the object Question.
	 * @param course String
	 * @param subject String
	 * @param content String
	 * @param author String
	 * @param numberOfRates int
	 */
	public Question(int index, String course, String subject , String content, String author, String date, int numberOfRates, int privacy){
		this.index = index;
		this.course = course;
		this.subject = subject;
		this.content = content;
		this.author = author;
		this.date = date;
		this.numberOfRates = numberOfRates;
		this.privacy = privacy;
		
	}
	/**
	 * This method permits to get index.
	 * @return int index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * This method permits to set Course.
	 * @param index int
	 */
	public void setCourse(int index) {
		this.index = index;
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
	
	/**
	 * This method permits to get Author.
	 * @return String
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * This method permits to set Author.
	 * @param author String
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * This method permits to get Date.
	 * @return String date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * This method permits to set Date.
	 * @param date String
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * This method permits to get numberOfRates.
	 * @return int
	 */
	public int getNumberOfRates() {
		return numberOfRates;
	}
	/**
	 * This method permits to set numberOfRates.
	 * @param numberOfRates int
	 */
	public void setnumberOfRates(int numberOfRates) {
		this.numberOfRates = numberOfRates;
	}
	
	/**
	 * This method permits to get Privacy.
	 * @return int
	 */
	public int getPrivacy() {
		return privacy;
	}
	/**
	 * This method permits to set Privacy.
	 * @param Privacy int
	 */
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	

}

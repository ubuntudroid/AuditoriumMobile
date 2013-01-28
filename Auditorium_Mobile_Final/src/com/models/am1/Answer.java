package com.models.am1;

import java.io.Serializable;
/**
 * ListAnswerAdapter
 * Created on November 26, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 * Edited by Bernardo Plaza
 * Last edited January, 15th
 */
public class Answer implements Serializable{
	private int index = 0;
	private String answer = "";
	private String author="";
	private String date = "";
	private int numberOfRates=0;
	/**
	 * Constructor of the object Answer.
	 * @param answer String
	 */
	public Answer(int index, String answer,String author,String date,int numberOfRates){
		this.index = index;
		this.answer = answer;
		this.author = author;
		this.date = date;
		this.numberOfRates = numberOfRates;
	}
	
	/**
	 * This method permits to get answer.
	 * @return int
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * This method permits to set answer.
	 * @param course String
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * This method permits to get answer.
	 * @return String
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * This method permits to set answer.
	 * @param course String
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	/**
	 * This method permits to get Author.
	 * @return String author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * This method permits to set author.
	 * @param author String
	 */
	public void setAuthor(String author) {
		this.author = answer;
	}
	
	/**
	 * This method permits to get Date.
	 * @return String date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * This method permits to set date.
	 * @param date String
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * This method permits to get numberOfRates.
	 * @return int numberOfRates
	 */
	public int getNumberOfRates() {
		return numberOfRates;
	}
	/**
	 * This method permits to set answer.
	 * @param course String
	 */
	public void setNumberOfRates(int numberOfRates) {
		this.numberOfRates = numberOfRates;
	}
	
}
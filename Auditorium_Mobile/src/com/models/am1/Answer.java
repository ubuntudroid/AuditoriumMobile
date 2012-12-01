package com.models.am1;

import java.io.Serializable;
/**
 * ListAnswerAdapter
 * Created on November 26, 2012
 * @author Valentina Pontillo <a href =  mailto : v.pontillo@studenti.unina.it">v.pontillo@studenti.unina.it</a>
 */
public class Answer implements Serializable{
	private String answer = "";
	/**
	 * Constructor of the object Answer.
	 * @param answer String
	 */
	public Answer(String answer){
		this.answer = answer;
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
}
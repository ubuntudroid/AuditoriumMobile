package de.dresden.mobilisauditorium.android.client;
/**
 * This class holds information about the user that is currently logged in
 * @author MARCHELLO
 */

public class User {
	
	public static String emailAddress = null;
	public static String password = null;
	public String nickName = null;
	public String secondName = null;
	public int accountType = -1;
	
	/**
	 * Getters and setters
	 * @return
	 */
	public String getNickname(){
		return this.nickName;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getEmailAddress(){
		return this.emailAddress;
	}
	
	public void setNickname(String nickName){
		this.nickName = nickName;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}
	
	public void setAccountType(int accountType){
		this.accountType = accountType;
	}
	
//	public void addInterestingQuestion(long question_index){
//		this.interesting_questions.add(question_index);
//	}
}

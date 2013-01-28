package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPInfo;

import java.util.List;import java.util.ArrayList;

public class Answer implements XMPPInfo {

	private String answer = null;
	private String date = null;
	private String user = null;
	private int rate = Integer.MIN_VALUE;
	private int answer_index = Integer.MIN_VALUE;
	private int question_index = Integer.MIN_VALUE;


	public Answer( String answer, String date, String user, int rate, int answer_index, int question_index ) {
		super();
		this.answer = answer;
		this.date = date;
		this.user = user;
		this.rate = rate;
		this.answer_index = answer_index;
		this.question_index = question_index;
	}

	public Answer(){}



	
	public void fromXML( XmlPullParser parser ) throws Exception {
		boolean done = false;
			
		do {
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				
				if (tagName.equals(getChildElement())) {
					parser.next();
				}
				else if (tagName.equals( "answer" ) ) {
					this.answer = parser.nextText();
				}
				else if (tagName.equals( "date" ) ) {
					this.date = parser.nextText();
				}
				else if (tagName.equals( "user" ) ) {
					this.user = parser.nextText();
				}
				else if (tagName.equals( "rate" ) ) {
					this.rate = Integer.parseInt( parser.nextText() );
				}
				else if (tagName.equals( "answer_index" ) ) {
					this.answer_index = Integer.parseInt( parser.nextText() );
				}
				else if (tagName.equals( "question_index" ) ) {
					this.question_index = Integer.parseInt( parser.nextText() );
				}
				else
					parser.next();
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals(getChildElement()))
					done = true;
				else
					parser.next();
				break;
			case XmlPullParser.END_DOCUMENT:
				done = true;
				break;
			default:
				parser.next();
			}
		} while (!done);
	}

	public static final String CHILD_ELEMENT = "Answer";

	
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "http://auditorium.interface/MobilisAuditorium#type:Answer";

	
	public String getNamespace() {
		return NAMESPACE;
	}

	
	public String toXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<answer>" )
			.append( this.answer )
			.append( "</answer>" );

		sb.append( "<date>" )
			.append( this.date )
			.append( "</date>" );

		sb.append( "<user>" )
			.append( this.user )
			.append( "</user>" );

		sb.append( "<rate>" )
			.append( this.rate )
			.append( "</rate>" );

		sb.append( "<answer_index>" )
			.append( this.answer_index )
			.append( "</answer_index>" );

		sb.append( "<question_index>" )
			.append( this.question_index )
			.append( "</question_index>" );

		return sb.toString();
	}



	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer( String answer ) {
		this.answer = answer;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate( String date ) {
		this.date = date;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser( String user ) {
		this.user = user;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate( int rate ) {
		this.rate = rate;
	}

	public int getAnswer_index() {
		return this.answer_index;
	}

	public void setAnswer_index( int answer_index ) {
		this.answer_index = answer_index;
	}

	public int getQuestion_index() {
		return this.question_index;
	}

	public void setQuestion_index( int question_index ) {
		this.question_index = question_index;
	}

}
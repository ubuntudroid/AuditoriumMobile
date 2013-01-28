package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPInfo;

import java.util.List;import java.util.ArrayList;

public class Question implements XMPPInfo {

	private String course = null;
	private String subject = null;
	private String content = null;
	private String author = null;
	private String date = null;
	private int rate = Integer.MIN_VALUE;
	private int security = Integer.MIN_VALUE;
	private int question_index = Integer.MIN_VALUE;


	public Question( String course, String subject, String content, String author, String date, int rate, int security, int question_index ) {
		super();
		this.course = course;
		this.subject = subject;
		this.content = content;
		this.author = author;
		this.date = date;
		this.rate = rate;
		this.security = security;
		this.question_index = question_index;
	}

	public Question(){}



	@Override
	public void fromXML( XmlPullParser parser ) throws Exception {
		boolean done = false;
			
		do {
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				
				if (tagName.equals(getChildElement())) {
					parser.next();
				}
				else if (tagName.equals( "course" ) ) {
					this.course = parser.nextText();
				}
				else if (tagName.equals( "subject" ) ) {
					this.subject = parser.nextText();
				}
				else if (tagName.equals( "content" ) ) {
					this.content = parser.nextText();
				}
				else if (tagName.equals( "author" ) ) {
					this.author = parser.nextText();
				}
				else if (tagName.equals( "date" ) ) {
					this.date = parser.nextText();
				}
				else if (tagName.equals( "rate" ) ) {
					this.rate = Integer.parseInt( parser.nextText() );
				}
				else if (tagName.equals( "security" ) ) {
					this.security = Integer.parseInt( parser.nextText() );
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

	public static final String CHILD_ELEMENT = "Question";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "http://auditorium.interface/MobilisAuditorium#type:Question";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<course>" )
			.append( this.course )
			.append( "</course>" );

		sb.append( "<subject>" )
			.append( this.subject )
			.append( "</subject>" );

		sb.append( "<content>" )
			.append( this.content )
			.append( "</content>" );

		sb.append( "<author>" )
			.append( this.author )
			.append( "</author>" );

		sb.append( "<date>" )
			.append( this.date )
			.append( "</date>" );

		sb.append( "<rate>" )
			.append( this.rate )
			.append( "</rate>" );

		sb.append( "<security>" )
			.append( this.security )
			.append( "</security>" );

		sb.append( "<question_index>" )
			.append( this.question_index )
			.append( "</question_index>" );

		return sb.toString();
	}



	public String getCourse() {
		return this.course;
	}

	public void setCourse( String course ) {
		this.course = course;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject( String subject ) {
		this.subject = subject;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent( String content ) {
		this.content = content;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor( String author ) {
		this.author = author;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate( String date ) {
		this.date = date;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate( int rate ) {
		this.rate = rate;
	}

	public int getSecurity() {
		return this.security;
	}

	public void setSecurity( int security ) {
		this.security = security;
	}

	public int getQuestion_index() {
		return this.question_index;
	}

	public void setQuestion_index( int question_index ) {
		this.question_index = question_index;
	}

}
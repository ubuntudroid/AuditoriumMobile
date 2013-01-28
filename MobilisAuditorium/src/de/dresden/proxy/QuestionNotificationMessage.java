package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class QuestionNotificationMessage extends XMPPBean {

	private Question question = new Question();
	private String text = null;


	public QuestionNotificationMessage( Question question, String text ) {
		super();
		this.question = question;
		this.text = text;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public QuestionNotificationMessage(){
		this.setType( XMPPBean.TYPE_RESULT );
	}


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
				else if (tagName.equals( Question.CHILD_ELEMENT ) ) {
					this.question.fromXML( parser );
				}
				else if (tagName.equals( "text" ) ) {
					this.text = parser.nextText();
				}
				else if (tagName.equals("error")) {
					parser = parseErrorAttributes(parser);
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

	public static final String CHILD_ELEMENT = "QuestionNotificationMessage";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:questionnotification";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		QuestionNotificationMessage clone = new QuestionNotificationMessage( question, text );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<" + this.question.getChildElement() + ">" )
			.append( this.question.toXML() )
			.append( "</" + this.question.getChildElement() + ">" );

		sb.append( "<text>" )
			.append( this.text )
			.append( "</text>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion( Question question ) {
		this.question = question;
	}

	public String getText() {
		return this.text;
	}

	public void setText( String text ) {
		this.text = text;
	}

}
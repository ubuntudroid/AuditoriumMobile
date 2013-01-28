package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class AnswerNotificationMessage extends XMPPBean {

	private Answer answer = new Answer();
	private String text = null;


	public AnswerNotificationMessage( Answer answer, String text ) {
		super();
		this.answer = answer;
		this.text = text;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public AnswerNotificationMessage(){
		this.setType( XMPPBean.TYPE_RESULT );
	}



	public void fromXML( XmlPullParser parser ) throws Exception {
		boolean done = false;
			
		do {
			switch (parser.getEventType()) {
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();
				
				if (tagName.equals(getChildElement())) {
					parser.next();
				}
				else if (tagName.equals( Answer.CHILD_ELEMENT ) ) {
					this.answer.fromXML( parser );
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

	public static final String CHILD_ELEMENT = "AnswerNotificationMessage";

	
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:answernotification";

	
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		AnswerNotificationMessage clone = new AnswerNotificationMessage( answer, text );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<" + this.answer.getChildElement() + ">" )
			.append( this.answer.toXML() )
			.append( "</" + this.answer.getChildElement() + ">" );

		sb.append( "<text>" )
			.append( this.text )
			.append( "</text>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public Answer getAnswer() {
		return this.answer;
	}

	public void setAnswer( Answer answer ) {
		this.answer = answer;
	}

	public String getText() {
		return this.text;
	}

	public void setText( String text ) {
		this.text = text;
	}

}
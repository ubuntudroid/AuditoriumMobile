package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class rateQuestionPositiveRequest extends XMPPBean {

	private String emailAddress = null;
	private int question_index = Integer.MIN_VALUE;


	public rateQuestionPositiveRequest( String emailAddress, int question_index ) {
		super();
		this.emailAddress = emailAddress;
		this.question_index = question_index;

		this.setType( XMPPBean.TYPE_SET );
	}

	public rateQuestionPositiveRequest(){
		this.setType( XMPPBean.TYPE_SET );
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
				else if (tagName.equals( "emailAddress" ) ) {
					this.emailAddress = parser.nextText();
				}
				else if (tagName.equals( "question_index" ) ) {
					this.question_index = Integer.parseInt( parser.nextText() );
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

	public static final String CHILD_ELEMENT = "rateQuestionPositiveRequest";


	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:ratequestionpositive";


	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		rateQuestionPositiveRequest clone = new rateQuestionPositiveRequest( emailAddress, question_index );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<emailAddress>" )
			.append( this.emailAddress )
			.append( "</emailAddress>" );

		sb.append( "<question_index>" )
			.append( this.question_index )
			.append( "</question_index>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress( String emailAddress ) {
		this.emailAddress = emailAddress;
	}

	public int getQuestion_index() {
		return this.question_index;
	}

	public void setQuestion_index( int question_index ) {
		this.question_index = question_index;
	}

}
package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class postQuestionResponse extends XMPPBean {

	private int question_index = Integer.MIN_VALUE;
	private int errortype = Integer.MIN_VALUE;


	public postQuestionResponse( int question_index, int errortype ) {
		super();
		this.question_index = question_index;
		this.errortype = errortype;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public postQuestionResponse(){
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
				else if (tagName.equals( "question_index" ) ) {
					this.question_index = Integer.parseInt( parser.nextText() );
				}
				else if (tagName.equals( "errortype" ) ) {
					this.errortype = Integer.parseInt( parser.nextText() );
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

	public static final String CHILD_ELEMENT = "postQuestionResponse";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:postquestion";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		postQuestionResponse clone = new postQuestionResponse( question_index, errortype );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<question_index>" )
			.append( this.question_index )
			.append( "</question_index>" );

		sb.append( "<errortype>" )
			.append( this.errortype )
			.append( "</errortype>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public int getQuestion_index() {
		return this.question_index;
	}

	public void setQuestion_index( int question_index ) {
		this.question_index = question_index;
	}

	public int getErrortype() {
		return this.errortype;
	}

	public void setErrortype( int errortype ) {
		this.errortype = errortype;
	}

}
package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class refreshAnswerListRequest extends XMPPBean {

	private int question_index = Integer.MIN_VALUE;


	public refreshAnswerListRequest( int question_index ) {
		super();
		this.question_index = question_index;

		this.setType( XMPPBean.TYPE_SET );
	}

	public refreshAnswerListRequest(){
		this.setType( XMPPBean.TYPE_SET );
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

	public static final String CHILD_ELEMENT = "refreshAnswerListRequest";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:refreshanswerlist";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		refreshAnswerListRequest clone = new refreshAnswerListRequest( question_index );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<question_index>" )
			.append( this.question_index )
			.append( "</question_index>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public int getQuestion_index() {
		return this.question_index;
	}

	public void setQuestion_index( int question_index ) {
		this.question_index = question_index;
	}

}
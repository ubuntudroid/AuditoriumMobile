package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class postAnswerResponse extends XMPPBean {

	private int answer_index = Integer.MIN_VALUE;
	private int errortype = Integer.MIN_VALUE;


	public postAnswerResponse( int answer_index, int errortype ) {
		super();
		this.answer_index = answer_index;
		this.errortype = errortype;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public postAnswerResponse(){
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
				else if (tagName.equals( "answer_index" ) ) {
					this.answer_index = Integer.parseInt( parser.nextText() );
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

	public static final String CHILD_ELEMENT = "postAnswerResponse";


	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:postanswer";


	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		postAnswerResponse clone = new postAnswerResponse( answer_index, errortype );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<answer_index>" )
			.append( this.answer_index )
			.append( "</answer_index>" );

		sb.append( "<errortype>" )
			.append( this.errortype )
			.append( "</errortype>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public int getAnswer_index() {
		return this.answer_index;
	}

	public void setAnswer_index( int answer_index ) {
		this.answer_index = answer_index;
	}

	public int getErrortype() {
		return this.errortype;
	}

	public void setErrortype( int errortype ) {
		this.errortype = errortype;
	}

}
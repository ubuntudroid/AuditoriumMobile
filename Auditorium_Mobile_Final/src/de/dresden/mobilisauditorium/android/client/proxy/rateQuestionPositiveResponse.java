package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class rateQuestionPositiveResponse extends XMPPBean {

	private int errortype = Integer.MIN_VALUE;


	public rateQuestionPositiveResponse( int errortype ) {
		super();
		this.errortype = errortype;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public rateQuestionPositiveResponse(){
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

	public static final String CHILD_ELEMENT = "rateQuestionPositiveResponse";

	
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:ratequestionpositive";


	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		rateQuestionPositiveResponse clone = new rateQuestionPositiveResponse( errortype );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<errortype>" )
			.append( this.errortype )
			.append( "</errortype>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public int getErrortype() {
		return this.errortype;
	}

	public void setErrortype( int errortype ) {
		this.errortype = errortype;
	}

}
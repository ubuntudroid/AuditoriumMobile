package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class sendNotificationServiceIDRequest extends XMPPBean {

	private String registrationID = null;


	public sendNotificationServiceIDRequest( String registrationID ) {
		super();
		this.registrationID = registrationID;

		this.setType( XMPPBean.TYPE_SET );
	}

	public sendNotificationServiceIDRequest(){
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
				else if (tagName.equals( "registrationID" ) ) {
					this.registrationID = parser.nextText();
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

	public static final String CHILD_ELEMENT = "sendNotificationServiceIDRequest";

	
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:sendnotificationserviceid";

	
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		sendNotificationServiceIDRequest clone = new sendNotificationServiceIDRequest( registrationID );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<registrationID>" )
			.append( this.registrationID )
			.append( "</registrationID>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public String getRegistrationID() {
		return this.registrationID;
	}

	public void setRegistrationID( String registrationID ) {
		this.registrationID = registrationID;
	}

}
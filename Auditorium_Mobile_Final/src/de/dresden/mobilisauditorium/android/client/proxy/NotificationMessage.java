package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class NotificationMessage extends XMPPBean {

	private String notification_message = null;


	public NotificationMessage( String notification_message ) {
		super();
		this.notification_message = notification_message;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public NotificationMessage(){
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
				else if (tagName.equals( "notification_message" ) ) {
					this.notification_message = parser.nextText();
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

	public static final String CHILD_ELEMENT = "NotificationMessage";

	
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:notification";

	
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		NotificationMessage clone = new NotificationMessage( notification_message );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<notification_message>" )
			.append( this.notification_message )
			.append( "</notification_message>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public String getNotification_message() {
		return this.notification_message;
	}

	public void setNotification_message( String notification_message ) {
		this.notification_message = notification_message;
	}

}
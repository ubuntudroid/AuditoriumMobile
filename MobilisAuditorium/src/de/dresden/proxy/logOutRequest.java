package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class logOutRequest extends XMPPBean {

	private String emailAddress = null;
	private String password = null;


	public logOutRequest( String emailAddress, String password ) {
		super();
		this.emailAddress = emailAddress;
		this.password = password;

		this.setType( XMPPBean.TYPE_SET );
	}

	public logOutRequest(){
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
				else if (tagName.equals( "emailAddress" ) ) {
					this.emailAddress = parser.nextText();
				}
				else if (tagName.equals( "password" ) ) {
					this.password = parser.nextText();
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

	public static final String CHILD_ELEMENT = "logOutRequest";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:logout";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		logOutRequest clone = new logOutRequest( emailAddress, password );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<emailAddress>" )
			.append( this.emailAddress )
			.append( "</emailAddress>" );

		sb.append( "<password>" )
			.append( this.password )
			.append( "</password>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress( String emailAddress ) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

}
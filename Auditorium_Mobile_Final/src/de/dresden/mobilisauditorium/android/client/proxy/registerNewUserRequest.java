package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class registerNewUserRequest extends XMPPBean {

	private String username = null;
	private String emailAddress = null;
	private String password = null;
	private int accountType = Integer.MIN_VALUE;
	private String registrationID = null;


	public registerNewUserRequest( String username, String emailAddress, String password, int accountType, String registrationID ) {
		super();
		this.username = username;
		this.emailAddress = emailAddress;
		this.password = password;
		this.accountType = accountType;
		this.registrationID = registrationID;

		this.setType( XMPPBean.TYPE_SET );
	}

	public registerNewUserRequest(){
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
				else if (tagName.equals( "username" ) ) {
					this.username = parser.nextText();
				}
				else if (tagName.equals( "emailAddress" ) ) {
					this.emailAddress = parser.nextText();
				}
				else if (tagName.equals( "password" ) ) {
					this.password = parser.nextText();
				}
				else if (tagName.equals( "accountType" ) ) {
					this.accountType = Integer.parseInt( parser.nextText() );
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

	public static final String CHILD_ELEMENT = "registerNewUserRequest";


	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:registernewuser";


	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		registerNewUserRequest clone = new registerNewUserRequest( username, emailAddress, password, accountType, registrationID );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<username>" )
			.append( this.username )
			.append( "</username>" );

		sb.append( "<emailAddress>" )
			.append( this.emailAddress )
			.append( "</emailAddress>" );

		sb.append( "<password>" )
			.append( this.password )
			.append( "</password>" );

		sb.append( "<accountType>" )
			.append( this.accountType )
			.append( "</accountType>" );

		sb.append( "<registrationID>" )
			.append( this.registrationID )
			.append( "</registrationID>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername( String username ) {
		this.username = username;
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

	public int getAccountType() {
		return this.accountType;
	}

	public void setAccountType( int accountType ) {
		this.accountType = accountType;
	}

	public String getRegistrationID() {
		return this.registrationID;
	}

	public void setRegistrationID( String registrationID ) {
		this.registrationID = registrationID;
	}

}
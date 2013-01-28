package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class refreshQuestionListRequest extends XMPPBean {

	public refreshQuestionListRequest(){
		this.setType( XMPPBean.TYPE_SET );
	}


	
	public void fromXML( XmlPullParser parser ) throws Exception {}

	public static final String CHILD_ELEMENT = "refreshQuestionListRequest";

	
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:refreshquestionlist";

	
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		refreshQuestionListRequest clone = new refreshQuestionListRequest(  );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() { return ""; }


}
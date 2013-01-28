package de.dresden.mobilisauditorium.android.client.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class postQuestionRequest extends XMPPBean {

	private Question question = new Question();


	public postQuestionRequest( Question question ) {
		super();
		this.question = question;

		this.setType( XMPPBean.TYPE_SET );
	}

	public postQuestionRequest(){
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
				else if (tagName.equals( Question.CHILD_ELEMENT ) ) {
					this.question.fromXML( parser );
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

	public static final String CHILD_ELEMENT = "postQuestionRequest";


	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:postquestion";


	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		postQuestionRequest clone = new postQuestionRequest( question );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<" + this.question.getChildElement() + ">" )
			.append( this.question.toXML() )
			.append( "</" + this.question.getChildElement() + ">" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion( Question question ) {
		this.question = question;
	}

}
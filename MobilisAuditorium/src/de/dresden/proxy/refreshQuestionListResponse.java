package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class refreshQuestionListResponse extends XMPPBean {

	private QuestionList questionlist = new QuestionList();
	private int errortype = Integer.MIN_VALUE;


	public refreshQuestionListResponse( QuestionList questionlist, int errortype ) {
		super();
		this.questionlist = questionlist;
		this.errortype = errortype;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public refreshQuestionListResponse(){
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
				else if (tagName.equals( QuestionList.CHILD_ELEMENT ) ) {
					this.questionlist.fromXML( parser );
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

	public static final String CHILD_ELEMENT = "refreshQuestionListResponse";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:refreshquestionlist";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		refreshQuestionListResponse clone = new refreshQuestionListResponse( questionlist, errortype );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<" + this.questionlist.getChildElement() + ">" )
			.append( this.questionlist.toXML() )
			.append( "</" + this.questionlist.getChildElement() + ">" );

		sb.append( "<errortype>" )
			.append( this.errortype )
			.append( "</errortype>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public QuestionList getQuestionlist() {
		return this.questionlist;
	}

	public void setQuestionlist( QuestionList questionlist ) {
		this.questionlist = questionlist;
	}

	public int getErrortype() {
		return this.errortype;
	}

	public void setErrortype( int errortype ) {
		this.errortype = errortype;
	}

}
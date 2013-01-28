package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class refreshAnswerListResponse extends XMPPBean {

	private Question question = new Question();
	private AnswerList answerlist = new AnswerList();
	private int errortype = Integer.MIN_VALUE;


	public refreshAnswerListResponse( Question question, AnswerList answerlist, int errortype ) {
		super();
		this.question = question;
		this.answerlist = answerlist;
		this.errortype = errortype;

		this.setType( XMPPBean.TYPE_RESULT );
	}

	public refreshAnswerListResponse(){
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
				else if (tagName.equals( Question.CHILD_ELEMENT ) ) {
					this.question.fromXML( parser );
				}
				else if (tagName.equals( AnswerList.CHILD_ELEMENT ) ) {
					this.answerlist.fromXML( parser );
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

	public static final String CHILD_ELEMENT = "refreshAnswerListResponse";

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
		refreshAnswerListResponse clone = new refreshAnswerListResponse( question, answerlist, errortype );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<" + this.question.getChildElement() + ">" )
			.append( this.question.toXML() )
			.append( "</" + this.question.getChildElement() + ">" );

		sb.append( "<" + this.answerlist.getChildElement() + ">" )
			.append( this.answerlist.toXML() )
			.append( "</" + this.answerlist.getChildElement() + ">" );

		sb.append( "<errortype>" )
			.append( this.errortype )
			.append( "</errortype>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion( Question question ) {
		this.question = question;
	}

	public AnswerList getAnswerlist() {
		return this.answerlist;
	}

	public void setAnswerlist( AnswerList answerlist ) {
		this.answerlist = answerlist;
	}

	public int getErrortype() {
		return this.errortype;
	}

	public void setErrortype( int errortype ) {
		this.errortype = errortype;
	}

}
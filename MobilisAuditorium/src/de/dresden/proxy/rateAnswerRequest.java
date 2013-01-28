package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

import java.util.List;import java.util.ArrayList;

public class rateAnswerRequest extends XMPPBean {

	private String nickName = null;
	private int question_index = Integer.MIN_VALUE;
	private int answer_index = Integer.MIN_VALUE;
	private int rate = Integer.MIN_VALUE;


	public rateAnswerRequest( String nickName, int question_index, int answer_index, int rate ) {
		super();
		this.nickName = nickName;
		this.question_index = question_index;
		this.answer_index = answer_index;
		this.rate = rate;

		this.setType( XMPPBean.TYPE_SET );
	}

	public rateAnswerRequest(){
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
				else if (tagName.equals( "nickName" ) ) {
					this.nickName = parser.nextText();
				}
				else if (tagName.equals( "question_index" ) ) {
					this.question_index = Integer.parseInt( parser.nextText() );
				}
				else if (tagName.equals( "answer_index" ) ) {
					this.answer_index = Integer.parseInt( parser.nextText() );
				}
				else if (tagName.equals( "rate" ) ) {
					this.rate = Integer.parseInt( parser.nextText() );
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

	public static final String CHILD_ELEMENT = "rateAnswerRequest";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "mobilis:iq:rateanswer";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public XMPPBean clone() {
		rateAnswerRequest clone = new rateAnswerRequest( nickName, question_index, answer_index, rate );
		clone.cloneBasicAttributes( clone );

		return clone;
	}

	@Override
	public String payloadToXML() {
		StringBuilder sb = new StringBuilder();

		sb.append( "<nickName>" )
			.append( this.nickName )
			.append( "</nickName>" );

		sb.append( "<question_index>" )
			.append( this.question_index )
			.append( "</question_index>" );

		sb.append( "<answer_index>" )
			.append( this.answer_index )
			.append( "</answer_index>" );

		sb.append( "<rate>" )
			.append( this.rate )
			.append( "</rate>" );

		sb = appendErrorPayload(sb);

		return sb.toString();
	}


	public String getNickName() {
		return this.nickName;
	}

	public void setNickName( String nickName ) {
		this.nickName = nickName;
	}

	public int getQuestion_index() {
		return this.question_index;
	}

	public void setQuestion_index( int question_index ) {
		this.question_index = question_index;
	}

	public int getAnswer_index() {
		return this.answer_index;
	}

	public void setAnswer_index( int answer_index ) {
		this.answer_index = answer_index;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate( int rate ) {
		this.rate = rate;
	}

}
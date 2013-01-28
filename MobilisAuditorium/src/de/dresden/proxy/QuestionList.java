package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPInfo;

import java.util.List;import java.util.ArrayList;

public class QuestionList implements XMPPInfo {

	private List< Question > question = new ArrayList< Question >();


	public QuestionList( List< Question > question ) {
		super();
		for ( Question entity : question ) {
			this.question.add( entity );
		}
	}

	public QuestionList(){}



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
					Question entity = new Question();

					entity.fromXML( parser );
					this.question.add( entity );
					
					parser.next();
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

	public static final String CHILD_ELEMENT = "QuestionList";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "http://auditorium.interface/MobilisAuditorium#type:QuestionList";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();

		for( Question entry : question ) {
			sb.append( "<" + Question.CHILD_ELEMENT + ">" );
			sb.append( entry.toXML() );
			sb.append( "</" + Question.CHILD_ELEMENT + ">" );
		}

		return sb.toString();
	}



	public List< Question > getQuestion() {
		return this.question;
	}

	public void setQuestion( List< Question > question ) {
		this.question = question;
	}

}
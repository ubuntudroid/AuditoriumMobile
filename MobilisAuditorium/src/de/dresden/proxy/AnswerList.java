package de.dresden.proxy;

import org.xmlpull.v1.XmlPullParser;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPInfo;

import java.util.List;import java.util.ArrayList;

public class AnswerList implements XMPPInfo {

	private List< Answer > answer = new ArrayList< Answer >();


	public AnswerList( List< Answer > answer ) {
		super();
		for ( Answer entity : answer ) {
			this.answer.add( entity );
		}
	}

	public AnswerList(){}



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
				else if (tagName.equals( Answer.CHILD_ELEMENT ) ) {
					Answer entity = new Answer();

					entity.fromXML( parser );
					this.answer.add( entity );
					
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

	public static final String CHILD_ELEMENT = "AnswerList";

	@Override
	public String getChildElement() {
		return CHILD_ELEMENT;
	}

	public static final String NAMESPACE = "http://auditorium.interface/MobilisAuditorium#type:AnswerList";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();

		for( Answer entry : answer ) {
			sb.append( "<" + Answer.CHILD_ELEMENT + ">" );
			sb.append( entry.toXML() );
			sb.append( "</" + Answer.CHILD_ELEMENT + ">" );
		}

		return sb.toString();
	}



	public List< Answer > getAnswer() {
		return this.answer;
	}

	public void setAnswer( List< Answer > answer ) {
		this.answer = answer;
	}

}
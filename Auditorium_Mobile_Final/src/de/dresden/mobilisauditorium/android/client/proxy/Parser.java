package de.dresden.mobilisauditorium.android.client.proxy;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import de.tudresden.inf.rn.mobilis.mxa.parcelable.XMPPIQ;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;


public class Parser {

	public static XMPPIQ beanToIQ(XMPPBean bean, boolean mergePayload) {
		// default XMPP IQ type
		int type = XMPPIQ.TYPE_GET;
		
		switch (bean.getType()) {
			case XMPPBean.TYPE_GET:    type = XMPPIQ.TYPE_GET; break;
			case XMPPBean.TYPE_SET:    type = XMPPIQ.TYPE_SET; break;
			case XMPPBean.TYPE_RESULT: type = XMPPIQ.TYPE_RESULT; break;
			case XMPPBean.TYPE_ERROR:  type = XMPPIQ.TYPE_ERROR; break;
		}
		
		XMPPIQ iq;
		
		if (mergePayload)
			iq = new XMPPIQ( bean.getFrom(), bean.getTo(), type, null, null, bean.toXML() );
		else
			iq = new XMPPIQ( bean.getFrom(), bean.getTo(), type,
					bean.getChildElement(), bean.getNamespace(), bean.payloadToXML() );
		
		iq.packetID = bean.getId();
		
		return iq;
	}
	
	/**
	 * Formats a Bean to a string.
	 *
	 * @param bean the bean
	 * @return the formatted string of the Bean
	 */
	public String beanToString(XMPPBean bean){
		String str = "XMPPBean: [NS="
			+ bean.getNamespace()
			+ " id=" + bean.getId()
			+ " from=" + bean.getFrom()
			+ " to=" + bean.getTo()
			+ " type=" + bean.getType()
			+ " payload=" + bean.payloadToXML();
		
		if(bean.errorCondition != null)
			str += " errorCondition=" + bean.errorCondition;
		if(bean.errorText != null)
			str += " errorText=" + bean.errorText;
		if(bean.errorType != null)
			str += " errorType=" + bean.errorType;
		
		str += "]";
		
		return str;
	}
	
	/**
	 * Convert XMPPIQ to XMPPBean to simplify the handling of the IQ using the 
	 * beanPrototypes.
	 *
	 * @param iq the XMPPIQ
	 * @return the related XMPPBean or null if something goes wrong
	 */
	public static XMPPBean convertXMPPIQToBean(XMPPIQ iq) {
		
		try {
			String childElement = iq.element;
			String namespace    = iq.namespace;
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(iq.payload));
			XMPPBean bean = null;
			
			
		
			if(childElement.equals(logInResponse.CHILD_ELEMENT)&&namespace.equals(logInResponse.NAMESPACE)){
				bean = new logInResponse();
			}
			if(childElement.equals(logOutResponse.CHILD_ELEMENT)&&namespace.equals(logOutResponse.NAMESPACE)){
				bean = new logOutResponse();
			}
			if(childElement.equals(registerNewUserResponse.CHILD_ELEMENT)&&namespace.equals(registerNewUserResponse.NAMESPACE)){
				bean = new registerNewUserResponse();
			}
			if(childElement.equals(postQuestionResponse.CHILD_ELEMENT)&&namespace.equals(postQuestionResponse.NAMESPACE)){
				bean = new postQuestionResponse();
				Log.i("PARSER", "The type of bean postQuesionResponse has benn created!");
			}
			if(childElement.equals(postAnswerResponse.CHILD_ELEMENT)&&namespace.equals(postAnswerResponse.NAMESPACE)){
				bean = new postAnswerResponse();
				Log.i("PARSER", "The type of bean postAnswerResponse has benn created!");
			}
			if(childElement.equals(rateQuestionPositiveResponse.CHILD_ELEMENT)&&namespace.equals(rateQuestionPositiveResponse.NAMESPACE)){
				bean = new rateQuestionPositiveResponse();
			}
			if(childElement.equals(rateQuestionNegativeResponse.CHILD_ELEMENT)&&namespace.equals(rateQuestionNegativeResponse.NAMESPACE)){
				bean = new rateQuestionNegativeResponse();
			}
			if(childElement.equals(NotificationMessage.CHILD_ELEMENT)&&namespace.equals(NotificationMessage.NAMESPACE)){
				bean = new NotificationMessage();
			}
			
			if(childElement.equals(AnswerNotificationMessage.CHILD_ELEMENT)&&namespace.equals(AnswerNotificationMessage.NAMESPACE)){
				bean = new AnswerNotificationMessage();
			}
			
			if(childElement.equals(QuestionNotificationMessage.CHILD_ELEMENT)&&namespace.equals(QuestionNotificationMessage.NAMESPACE)){
				bean = new QuestionNotificationMessage();
			}
			
			
			if(childElement.equals(rateAnswerResponse.CHILD_ELEMENT)&&namespace.equals(rateAnswerResponse.NAMESPACE)){
				bean = new rateAnswerResponse();
			}

			
			if(childElement.equals(refreshQuestionListResponse.CHILD_ELEMENT)&&namespace.equals(refreshQuestionListResponse.NAMESPACE)){
				bean = new refreshQuestionListResponse();
			}
			
			if(childElement.equals(refreshAnswerListResponse.CHILD_ELEMENT)&&namespace.equals(refreshAnswerListResponse.NAMESPACE)){
				bean = new refreshAnswerListResponse();
			}
			
			if(childElement.equals(sendNotificationServiceIDResponse.CHILD_ELEMENT)&&namespace.equals(sendNotificationServiceIDResponse.NAMESPACE)){
				bean = new sendNotificationServiceIDResponse();
			}
					bean.fromXML(parser);
					
					bean.setId(iq.packetID);
					bean.setFrom(iq.from);
					bean.setTo(iq.to);
					
					switch (iq.type) {
						case XMPPIQ.TYPE_GET: bean.setType(XMPPBean.TYPE_GET); break;
						case XMPPIQ.TYPE_SET: bean.setType(XMPPBean.TYPE_SET); break;
						case XMPPIQ.TYPE_RESULT: bean.setType(XMPPBean.TYPE_RESULT); break;
						case XMPPIQ.TYPE_ERROR: bean.setType(XMPPBean.TYPE_ERROR); break;
					}
					
					return bean;
				
			
		} catch (Exception e) {
			
		}
		
		return null;
	}
}

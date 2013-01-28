package de.dresden.mobilisauditorium.android.engine;
/**
 * @author MARCHELLO, Marian Seliuchenko, egur2006@yandex.ru
 * This class receives packets from service, converts them to a respective beans,
 * and send them to all handlers that are registered for reception. Also it holds 
 * general information which can be accessed be aimport java.util.ArrayList;
 * 
 */

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.activities.am1.LoginActivity;
import com.activities.am1.SimpleService;

import de.dresden.mobilisauditorium.android.client.User;
import de.dresden.mobilisauditorium.android.client.proxy.IMobilisAuditoriumOutgoing;
import de.dresden.mobilisauditorium.android.client.proxy.Parser;
import de.dresden.mobilisauditorium.android.client.proxy.logInResponse;
import de.dresden.mobilisauditorium.android.client.proxy.logOutResponse;
import de.dresden.mobilisauditorium.android.client.proxy.postAnswerResponse;
import de.dresden.mobilisauditorium.android.client.proxy.postQuestionResponse;
import de.dresden.mobilisauditorium.android.client.proxy.rateAnswerResponse;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionNegativeResponse;
import de.dresden.mobilisauditorium.android.client.proxy.rateQuestionPositiveResponse;
import de.dresden.mobilisauditorium.android.client.proxy.refreshAnswerListResponse;
import de.dresden.mobilisauditorium.android.client.proxy.refreshQuestionListResponse;
import de.dresden.mobilisauditorium.android.client.proxy.registerNewUserResponse;
import de.dresden.mobilisauditorium.android.client.proxy.sendNotificationServiceIDResponse;
import de.tudresden.inf.rn.mobilis.mxa.IXMPPService;
import de.tudresden.inf.rn.mobilis.mxa.MXAController;
import de.tudresden.inf.rn.mobilis.mxa.MXAListener;
import de.tudresden.inf.rn.mobilis.mxa.callbacks.IXMPPIQCallback;
import de.tudresden.inf.rn.mobilis.mxa.parcelable.XMPPIQ;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

/**
 * Implements MXAListener interface which specifies methods that have to be implemented 
 * to work with MXA library
 * @author MARCHELLO
 *
 */
public class Singleton implements MXAListener {
	
	/**
	 * intance holds a instance of this singleton object
	 * Online reflects if user logged in
	 * question_notification enables the notification messages in case a question was posted
	 * answer_notification enables the notification messages in case an answer was posted
	 * rate_notification enables the notification messages in case a question or answer was rated
	 * handlerList holds registered handlers for received beans
	 * userProfile contains information of user that is currently logged
	 */
	private static Singleton instance;
	public static boolean Online = false;
	public static boolean question_notification = true;
	public static boolean answer_notification = true;
	public static boolean rate_notification = true;
	public static ArrayList<Handler> handlerList = new ArrayList<Handler>();
	public User userProfile = new User();

	
	
	private Singleton(){}
	
	/**
	 * Registers a handler
	 * @param handler
	 */
	public void registerHandler(Handler handler){
		if(!handlerList.contains(handler)){
			handlerList.add(handler);
		}
	}
	
	/**
	 * Unregisters a handler
	 * @param handler
	 */
	public void unregisterHandler(Handler handler){
		if(handlerList.contains(handler)){
			handlerList.remove(handler);
		}
	}
	
	/**
	 * Sends the bean to all registered handlers
	 * @param bean
	 */
	public void sendToAllHandlers(XMPPBean bean){
		Log.i("Singleton", "Sending messages to handlers!");
		Message msg = new Message();
		msg.obj = bean;
		Log.i("Singleton", "Handlers count:"+String.valueOf( handlerList.size()));
		
		Iterator<Handler> it = handlerList.iterator();
		
		while(it.hasNext()){
			it.next().sendMessage(msg);
		}
	}
	
	
	
	
	
	public MXAController mMXAController = null;
	private static IXMPPService xmppS = null;
	private boolean isXMPPConnected = false;
	public String registrationID = null;
	
	

	
	/**
	 * Check if XMPPService is available
	 * @return
	 */
	public boolean isXMPPConnected(){
		if(isXMPPConnected==true){
			isXMPPConnected=false;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a instance of this class
	 * @return
	 */
	public static synchronized Singleton getInstance(){
		if(instance==null){
			return new Singleton();
		}
		return instance;
	}
	
	
	/**
	 * Returns reference to XMPP service
	 * @return
	 */
	public IXMPPService getIXMPPService(){
		return xmppS;
	}
	
	/**
	 * Contains a method that processes incoming packets
	 */
	public IXMPPIQCallback IQCallback = new IXMPPIQCallback.Stub(){
	
		public void processIQ(XMPPIQ arg0) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i("Singleton.Callback", "IQ to bean starting...");
			 XMPPBean b = Parser.convertXMPPIQToBean(arg0);
			 Log.i("Singleton.Callback", "IQ to bean done!");
			    if (b instanceof postQuestionResponse){
			    	postQuestionResponse response = (postQuestionResponse)b;
			    	sendToAllHandlers(response);
			    	
			    	}
			    	
			    
			    if(b instanceof postAnswerResponse){
			    	postAnswerResponse response = (postAnswerResponse)b;
			    		sendToAllHandlers(response);
			    	}  
			    
			    if(b instanceof refreshQuestionListResponse){
			    	refreshQuestionListResponse response = (refreshQuestionListResponse)b;
			   		sendToAllHandlers(response);
			
			    }
			    
			    if(b instanceof refreshAnswerListResponse){
			    	refreshAnswerListResponse response = (refreshAnswerListResponse)b;
			   		sendToAllHandlers(response);
			
			    }
			    
			    if(b instanceof logInResponse){
			    	logInResponse response = (logInResponse)b;
			    	sendToAllHandlers(response);

			    }
			    
			    if(b instanceof rateQuestionPositiveResponse){
			    	rateQuestionPositiveResponse response = (rateQuestionPositiveResponse)b;
			    	sendToAllHandlers(response);

			    }
			    
			    if(b instanceof rateQuestionNegativeResponse){
			    	rateQuestionNegativeResponse response = (rateQuestionNegativeResponse)b;
			    	sendToAllHandlers(response);

			    }
			    
			    if(b instanceof rateAnswerResponse){
			    	rateAnswerResponse response = (rateAnswerResponse)b;
			    	sendToAllHandlers(response);

			    }
			    
			    if(b instanceof registerNewUserResponse){
			    	registerNewUserResponse response = (registerNewUserResponse)b;
			    	sendToAllHandlers(response);
			    }			    
			  
		}
		
	};
	
	/**
	 * Contains method for sending packets out.
	 */
	public IMobilisAuditoriumOutgoing OutStub = new IMobilisAuditoriumOutgoing(){

		public void sendXMPPBean(XMPPBean out) {
			// TODO Auto-generated method stub
			try {
				Messenger ack = null;
				Messenger ack2 = null;
				out.setTo("mobilis@marchello-pc/MobilisAuditorium_v1#1");
				out.setType(XMPPBean.TYPE_SET);
				Log.i("Singleton", "Sending out...");
				if(xmppS==null){
					Log.i("Singleton", "xmppS = NULL");	
				}
				xmppS.sendIQ(ack, ack2, 1,Parser.beanToIQ(out, true));
				
				Log.i("Singleton", "Sent!");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	};
	
	
	
	/**
	 * This method is invoked when MXA library has connected to XMPP server
	 */
	
	public void onMXAConnected() {
		// TODO Auto-generated method stub
		xmppS = mMXAController.getXMPPService();
		Messenger acknowledgement = null;
		try{
			xmppS.connect(acknowledgement);
			Log.i("Singleton", "xmppS.connected!");
			registerCallbacks();
			isXMPPConnected = true;
			
		}catch(RemoteException e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method registers IQCallback object for receiveng and processing beans
	 */
	public void registerCallbacks(){
    	try{
    		
    		//write equal lines for all beans you want to receive
    		
			xmppS.registerIQCallback(IQCallback, logInResponse.CHILD_ELEMENT, logInResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, registerNewUserResponse.CHILD_ELEMENT, registerNewUserResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, refreshQuestionListResponse.CHILD_ELEMENT, refreshQuestionListResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, postQuestionResponse.CHILD_ELEMENT, postQuestionResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, postAnswerResponse.CHILD_ELEMENT, postAnswerResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, rateQuestionPositiveResponse.CHILD_ELEMENT, rateQuestionPositiveResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, rateQuestionNegativeResponse.CHILD_ELEMENT, rateQuestionNegativeResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, rateAnswerResponse.CHILD_ELEMENT, rateAnswerResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, refreshAnswerListResponse.CHILD_ELEMENT, refreshAnswerListResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, sendNotificationServiceIDResponse.CHILD_ELEMENT, sendNotificationServiceIDResponse.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, logOutResponse.CHILD_ELEMENT, logOutResponse.NAMESPACE);
			
			
			Log.i("Singleton", "Callback registered!");
		}
    	catch(Exception e)
		{

		}
    	
    }
	
	
	/**
	 * This method is invoked when MXA library has connected to XMPP server
	 */
	public void onMXADisconnected() {
		// TODO Auto-generated method stub
		isXMPPConnected = false;
		Log.i("Singleton", "DISCONNECTED!");
	}

}

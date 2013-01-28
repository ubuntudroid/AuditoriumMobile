package com.activities.am1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.activities.am1.R;

import de.dresden.mobilisauditorium.android.client.proxy.AnswerNotificationMessage;
import de.dresden.mobilisauditorium.android.client.proxy.NotificationMessage;
import de.dresden.mobilisauditorium.android.client.proxy.Parser;
import de.dresden.mobilisauditorium.android.client.proxy.QuestionNotificationMessage;
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
import de.dresden.mobilisauditorium.android.engine.Singleton;
import de.tudresden.inf.rn.mobilis.mxa.IXMPPService;
import de.tudresden.inf.rn.mobilis.mxa.MXAController;
import de.tudresden.inf.rn.mobilis.mxa.MXAListener;
import de.tudresden.inf.rn.mobilis.mxa.callbacks.IXMPPIQCallback;
import de.tudresden.inf.rn.mobilis.mxa.parcelable.XMPPIQ;
import de.tudresden.inf.rn.mobilis.xmpp.beans.XMPPBean;

public class SimpleService extends Service implements MXAListener{

	NotificationManager nm = null;
	public MXAController mMXAController = null;
	private static IXMPPService xmppS = null;
	Singleton mSingleton = null;


	@Override
	public void onCreate() {
		super.onCreate();

		Log.i("simpleSERVICE", "Service started!");
		NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

		Messenger acknowledgement = null;
		try{
			mMXAController = MXAController.get();
			mMXAController.connectMXA(getApplicationContext(), this);
			xmppS = mMXAController.getXMPPService();
			xmppS.connect(acknowledgement);
			Log.i("SimpleSERVICE", "xmppS.connected!");


		}catch(RemoteException e){
			e.printStackTrace();
		}

		try{
			
			xmppS.registerIQCallback(IQCallback, NotificationMessage.CHILD_ELEMENT, NotificationMessage.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, AnswerNotificationMessage.CHILD_ELEMENT, AnswerNotificationMessage.NAMESPACE);
			xmppS.registerIQCallback(IQCallback, QuestionNotificationMessage.CHILD_ELEMENT, QuestionNotificationMessage.NAMESPACE);

			Log.i("simpleSERVICE", "Callback registered!");
		}
		catch(Exception e)
		{

		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		try{
			xmppS.unregisterIQCallback(IQCallback, NotificationMessage.CHILD_ELEMENT, NotificationMessage.NAMESPACE);
			xmppS.unregisterIQCallback(IQCallback, AnswerNotificationMessage.CHILD_ELEMENT, AnswerNotificationMessage.NAMESPACE);
			xmppS.unregisterIQCallback(IQCallback, QuestionNotificationMessage.CHILD_ELEMENT, QuestionNotificationMessage.NAMESPACE);
			
			Log.i("Singleton", "Callback registered!");
		}
		catch(Exception e)
		{

		}
	}

	public IXMPPIQCallback IQCallback = new IXMPPIQCallback.Stub(){

		public void processIQ(XMPPIQ arg0) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i("SimpleSERVICE.Callback", "IQ to bean starting...");
			XMPPBean b = Parser.convertXMPPIQToBean(arg0);
			Log.i("SimpleSERVICE.Callback", "IQ to bean done!");
			if(mSingleton.Online==true){
				if(b instanceof QuestionNotificationMessage){
					if(mSingleton.question_notification==true){
						generateNotification(b);
					}
				}
				if(b instanceof AnswerNotificationMessage){
					if(mSingleton.answer_notification==true){
						generateNotification(b);
					}
				}
				
				
			}else{
				startActivity(new Intent(getApplicationContext(),LoginActivity.class));
			}
			
			









		}

	};





	private void generateNotification(XMPPBean b) {
		int icon = R.drawable.ic_stat_gcm;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = null;
		Intent notificationIntent = null;
		String title = "Auditorium Notification";
		String message = null;

		if(b instanceof NotificationMessage){
			NotificationMessage response = (NotificationMessage)b;
			notification = new Notification(icon, response.getNotification_message(), when);

		}
		if(b instanceof AnswerNotificationMessage){
			AnswerNotificationMessage response = (AnswerNotificationMessage)b;
			message = response.getText();
			notification = new Notification(icon, message, when);
			notificationIntent = new Intent(getApplicationContext(), ShowAnswerActivity.class);
			notificationIntent.putExtra("Answer_Showed", response.getAnswer());

		}
		if(b instanceof QuestionNotificationMessage){
			QuestionNotificationMessage response = (QuestionNotificationMessage)b;
			message = response.getText();
			notification = new Notification(icon, message, when);
			notificationIntent = new Intent(getApplicationContext(), ShowQuestionActivity.class);
			notificationIntent.putExtra("Question_Showed", response.getQuestion());

		}




		Log.i("notification function", "activity problem");
		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
				Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent =
				PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
		Log.i("notification function", "some other problems");
		notification.setLatestEventInfo(getApplicationContext(), title, message , intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Play default notification sound
		notification.defaults |= Notification.DEFAULT_SOUND;

		//	  // Vibrate if vibrate is enabled
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify("OK",0, notification);

	}

	public void onMXAConnected() {
		// TODO Auto-generated method stub

	}

	public void onMXADisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}

package com.google.android.gcm.demo.app;

import static com.google.android.gcm.demo.app.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.google.android.gcm.demo.app.CommonUtilities.SENDER_ID;
import static com.google.android.gcm.demo.app.CommonUtilities.SERVER_URL;
import static com.google.android.gcm.demo.app.CommonUtilities.EXTRA_MESSAGE;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MYGCM {
	
	Context context = null;
	AsyncTask<Void, Void, Void> mRegisterTask;

	public MYGCM(Context ctx){
		this.context = ctx;
	        GCMRegistrar.checkDevice(context);
	        GCMRegistrar.checkManifest(context);
	        context.registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
	        final String regId = GCMRegistrar.getRegistrationId(context);
	        if (regId.equals("")) {
	            // Automatically registers application on startup.
	            GCMRegistrar.register(context, SENDER_ID);
	        } else {
	            // Device is already registered on GCM, check server.
	            if (GCMRegistrar.isRegisteredOnServer(context)) {
	                // Skips registration.
	                //mDisplay.append(getString(R.string.already_registered) + "\n");
	            } else {
	                // Try to register again, but not in the UI thread.
	                // It's also necessary to cancel the thread onDestroy(),
	                // hence the use of AsyncTask instead of a raw thread.
	                final Context context1 = context;
	                mRegisterTask = new AsyncTask<Void, Void, Void>() {

	                    @Override
	                    protected Void doInBackground(Void... params) {
	                        boolean registered = ServerUtilities.register(context, regId);

	                        if (!registered) {
	                            GCMRegistrar.unregister(context);
	                        }
	                        return null;
	                    }

	                    @Override
	                    protected void onPostExecute(Void result) {
	                        mRegisterTask = null;
	                    }

	                };
	                mRegisterTask.execute(null, null, null);
	            }
	        }
	}
	
	 

	    private final BroadcastReceiver mHandleMessageReceiver =
	            new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
	            Toast.makeText(context, "BroadCast Message Received!",Toast.LENGTH_SHORT);
	          //  mDisplay.append(newMessage + "\n");
	        }
	    };
	
}

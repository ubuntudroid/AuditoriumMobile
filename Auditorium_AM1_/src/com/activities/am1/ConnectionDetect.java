package com.activities.am1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
 
/*This class detects the Internet connection status in order to display a popup message 
 * if there is no Internet access on the device.
 * 
 *   isConnectingToInternet() will check at any moment the connection status */

public class ConnectionDetect {
 
    private Context _context;
 
    public ConnectionDetect(Context context){
        this._context = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
}
package pk.movietracker.Receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import pk.movietracker.Service.TimerService;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class WifiReceiver extends BroadcastReceiver {

    TimerService timerServiceObj;
    boolean isBound;
    private Context mContext;

    public WifiReceiver(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null && info.isConnected()) {
            // Do your work.
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isConnected() || mobile.isConnected()){
                // Metoda uruchamiająca usługę przy włączonym wifi/sieci komórkowej
                Intent i = null;
                i = new Intent(context, TimerService.class);
                context.bindService(i, boundServiceConnection, Context.BIND_AUTO_CREATE);

            }
            else {
                context.unbindService(boundServiceConnection);
                // Metoda zatrzymująca usługę przy włączonym wifi/sieci komórkowej

            }
        }
    }

    public void stop() {
        mContext.unbindService(boundServiceConnection);

    }

    private ServiceConnection boundServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimerService.BoundServiceBinder binder = (TimerService.BoundServiceBinder) service;
            timerServiceObj = binder.getService();
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
}
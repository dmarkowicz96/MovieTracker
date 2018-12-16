package pk.movietracker.activity;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pk.movietracker.R;
import pk.movietracker.Receiver.WifiReceiver;
import pk.movietracker.db.DatabaseHelper;
import pk.movietracker.fragment.Menu;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    WifiReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFragment();

        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());

        receiver = new WifiReceiver(getBaseContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(receiver , intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.stop();
        unregisterReceiver(receiver);
    }

    private void initializeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainMenuFrame, new Menu())
                .commit();
    }
}

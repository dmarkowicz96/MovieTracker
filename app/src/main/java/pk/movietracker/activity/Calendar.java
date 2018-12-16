package pk.movietracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;


import pk.movietracker.R;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView= findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int day) {

                Intent intent = new Intent(getApplicationContext(), Movies.class);
                Bundle bundle = new Bundle();
                bundle.putInt("year",year);
                bundle.putInt("month",month);
                bundle.putInt("day",day);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

package pk.movietracker.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import pk.movietracker.R;
import pk.movietracker.adapter.MovieAdapter;
import pk.movietracker.db.dao.MovieDAO;
import pk.movietracker.model.Movie;

public class Movies extends AppCompatActivity {

    MovieAdapter arrayAdapter;
    private Date receivedDate;
    MovieDAO movieDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        movieDAO = new MovieDAO();

        initializeMovieListView();
        initializeMovieRefreshView();
    }

    private void initializeMovieListView() {
        receivedDate = getReceivedDate();

        ArrayList<Movie> movieList =  movieDAO.getMovies(receivedDate);

        final ListView listView = findViewById(R.id.movieListView);
        arrayAdapter = new MovieAdapter(this, movieList);

        listView.setAdapter(arrayAdapter);
    }

    private void initializeMovieRefreshView(){
        final ImageView refreshButton = findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                long refreshTime = prefs.getLong("refreshTime",0);
                long time = prefs.getLong("time",-1);
                if(time != refreshTime) {
                    prefs.edit().putLong("refreshTime", time).apply();

                    ArrayList<Movie> movieList = movieDAO.getMovies(receivedDate);
                    arrayAdapter.refresh(movieList);
                    Toast.makeText(getApplicationContext(),"Zakutalizowano", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Dane są aktualne", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private Date getReceivedDate() {
        Bundle bundle = getIntent().getExtras();

        Date date = new Date();
        if(bundle != null) {
            int year = bundle.getInt("year");
            int month = bundle.getInt("month");
            int day = bundle.getInt("day");

            date = new GregorianCalendar(year, month,day).getTime();
        }

        return date;
    }

}

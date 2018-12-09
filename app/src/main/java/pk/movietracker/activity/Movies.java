package pk.movietracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import pk.movietracker.R;
import pk.movietracker.adapter.MovieAdapter;
import pk.movietracker.model.Movie;

public class Movies extends AppCompatActivity {

    ArrayAdapter<Movie> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        initializeMovieListView();
    }

    private void initializeMovieListView() {

        ArrayList<Movie> movieList = testList();

        final ListView listView = findViewById(R.id.movieListView);
        arrayAdapter = new MovieAdapter(this, movieList);

        listView.setAdapter(arrayAdapter);
    }


    private ArrayList<pk.movietracker.model.Movie> testList() {
        ArrayList<pk.movietracker.model.Movie> movieList = new ArrayList<>();
        pk.movietracker.model.Movie movie = new pk.movietracker.model.Movie();
        movie.setDate(new Date());
        movie.setName("Film1");
        movie.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie);

        pk.movietracker.model.Movie movie2 = new pk.movietracker.model.Movie();
        movie2.setDate(new Date());
        movie2.setName("Film2");
        movie2.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie2);

        pk.movietracker.model.Movie movie3 = new pk.movietracker.model.Movie();
        movie3.setDate(new Date());
        movie3.setName("Film3");
        movie3.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie3);

        pk.movietracker.model.Movie movie4 = new pk.movietracker.model.Movie();
        movie4.setDate(new Date());
        movie4.setName("Film4");
        movie4.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie4);

        pk.movietracker.model.Movie movie5 = new pk.movietracker.model.Movie();
        movie5.setDate(new Date());
        movie5.setName("Film5");
        movie5.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie5);

        pk.movietracker.model.Movie movie6 = new Movie();
        movie6.setDate(new Date());
        movie6.setName("Film6");
        movie6.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie6);
        movieList.add(movie6);
        movieList.add(movie6);
        movieList.add(movie6);
        movieList.add(movie6);
        movieList.add(movie6);
        movieList.add(movie6);
        movieList.add(movie6);


        return movieList;
    }
}

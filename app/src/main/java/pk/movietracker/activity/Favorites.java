package pk.movietracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import pk.movietracker.R;
import pk.movietracker.adapter.FavoriteAdapter;
import pk.movietracker.model.Movie;

public class Favorites extends AppCompatActivity {

    ArrayAdapter<Movie> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initializeFavoritesListView();
    }

    private void initializeFavoritesListView() {

        ArrayList<Movie> movieList = testList();

        final ListView listView = findViewById(R.id.favoriteListView);
        arrayAdapter = new FavoriteAdapter(this, movieList);

        listView.setAdapter(arrayAdapter);
    }

    private ArrayList<Movie> testList() {
        ArrayList<Movie> movieList = new ArrayList<>();
        Movie movie = new Movie();
        movie.setDate(new Date());
        movie.setName("Film1");
        movie.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie);

        Movie movie2 = new Movie();
        movie2.setDate(new Date());
        movie2.setName("Film2");
        movie2.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie2);

        Movie movie3 = new Movie();
        movie3.setDate(new Date());
        movie3.setName("Film3");
        movie3.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie3);

        Movie movie4 = new Movie();
        movie4.setDate(new Date());
        movie4.setName("Film4");
        movie4.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie4);

        Movie movie5 = new Movie();
        movie5.setDate(new Date());
        movie5.setName("Film5");
        movie5.setDescription("AAAAAAAAA dDDDDDDDDDD");
        movieList.add(movie5);

        Movie movie6 = new Movie();
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

package pk.movietracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


import pk.movietracker.R;
import pk.movietracker.adapter.FavoriteAdapter;
import pk.movietracker.db.dao.MovieDAO;
import pk.movietracker.model.Movie;

public class Favorites extends AppCompatActivity {

    FavoriteAdapter arrayAdapter;
    MovieDAO movieDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        this.movieDAO = new MovieDAO();
        initializeFavoritesListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayAdapter.refresh();
    }

    private void initializeFavoritesListView() {

        ArrayList<Movie> movieList = movieDAO.getFavoriteMovies();

        final ListView listView = findViewById(R.id.favoriteListView);
        arrayAdapter = new FavoriteAdapter(this, movieList);

        listView.setAdapter(arrayAdapter);
    }

}

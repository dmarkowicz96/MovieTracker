package pk.movietracker.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pk.movietracker.R;
import pk.movietracker.db.dao.MovieDAO;

public class Movie extends AppCompatActivity {

    private pk.movietracker.model.Movie movie;
    private MovieDAO movieDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieDAO = new MovieDAO();

        initializeMovie();

        initializeText();

        initializeFavorite();
    }

    private void initializeMovie() {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            long id = bundle.getLong("movieId");
            movie = movieDAO.getMovie(id);
        } else {
            finish();
        }

    }

    private void initializeText() {
        TextView name = findViewById(R.id.textNameMovie);
        name.setText(movie.getName());

        TextView desc = findViewById(R.id.textDescMovie);
        desc.setText(movie.getDescription());
    }

    private void initializeFavorite() {
        final ImageView imageView = findViewById(R.id.setFavoriteBtn);

        updateImage(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movie.isFavorite()) {
                    movie.setFavorite(false);
                } else {
                    movie.setFavorite(true);
                }

                movieDAO.updateState(movie);
                updateImage(imageView);
            }
        });
    }

    private void updateImage(ImageView imageView) {
        if(movie.isFavorite()) {
            imageView.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            imageView.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}

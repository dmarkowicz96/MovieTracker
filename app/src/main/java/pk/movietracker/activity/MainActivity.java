package pk.movietracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;
import java.util.List;

import pk.movietracker.R;
import pk.movietracker.parser.asynctask.AsyncTaskCallback;
import pk.movietracker.parser.asynctask.AsyncTaskGetCinemaDates;
import pk.movietracker.parser.asynctask.AsyncTaskGetCinemaEvents;
import pk.movietracker.parser.asynctask.AsyncTaskGetCinemaMovies;
import pk.movietracker.parser.asynctask.AsyncTaskGetCinemas;
import pk.movietracker.parser.asynctask.paramwrapper.ParamCinemaDate;
import pk.movietracker.parser.model.ExternalCinema;
import pk.movietracker.parser.model.ExternalEvent;
import pk.movietracker.parser.model.ExternalMovie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exampleGetCinemaEvents();
    }

    public void startFavorites(View view) {
        Intent intent = new Intent(MainActivity.this, Favorites.class);
        startActivity(intent);
    }


    public void startShowMovies(View view) {
        Intent intent = new Intent(MainActivity.this, Movies.class);
        startActivity(intent);
    }



    // Pobiera wszystkie kina Cinema City w polsce.
    private void exampleGetCinemas() {
        AsyncTaskGetCinemas task = new AsyncTaskGetCinemas(new AsyncTaskCallback<List<ExternalCinema>>() {
            @Override
            public void onSuccess(List<ExternalCinema> cinemas) {
                for (ExternalCinema cinema : cinemas) {
                    System.out.println(cinema);
                }
            }

            @Override
            public void onFailure(String message) {
                System.out.println("exampleGetCinemas failure");
            }
        });
        task.execute();
    }

    // Pobiera wszystkie filmy dla danego kina i dla danej daty, w tym przypadku krakow-plaza, dzisiaj
    private void exampleGetCinemaMovies() {

        // Mock
        ExternalCinema krakowPlazaCinema = new ExternalCinema("1063", "Kraków - Galeria Plaza", "al. Pokoju 44, 31-546, Kraków");
        ParamCinemaDate parameters = new ParamCinemaDate(krakowPlazaCinema, new Date());

        AsyncTaskGetCinemaMovies task = new AsyncTaskGetCinemaMovies(new AsyncTaskCallback<List<ExternalMovie>>() {
            @Override
            public void onSuccess(List<ExternalMovie> movies) {
                for (ExternalMovie movie : movies) {
                    System.out.println(movie.toString());
                }
            }

            @Override
            public void onFailure(String message) {
                System.out.println("exampleGetCinemaMovies failure");
            }
        });
        task.execute(parameters);
    }

    // Pobiera liste dat, dla ktorych mozna sprawdzic repertuar dla danego kina - w tym przypadku krakow plaza
    // Uwaga! tu nie chodzi o seanse tylko o to na jaki dzien jest juz repertuar ustalony
    private void exampleGetCinemaDates() {

        // Mock
        ExternalCinema krakowPlazaCinema = new ExternalCinema("1063", "Kraków - Galeria Plaza", "al. Pokoju 44, 31-546, Kraków");

        AsyncTaskGetCinemaDates task = new AsyncTaskGetCinemaDates(new AsyncTaskCallback<List<Date>>() {
            @Override
            public void onSuccess(List<Date> dates) {
                for (Date date : dates) {
                    System.out.println("Date: " + date.toString());
                }
            }

            @Override
            public void onFailure(String message) {
                System.out.println("exampleGetCinemaDates failure");
            }
        });
        task.execute(krakowPlazaCinema);
    }

    // Pobiera godziny seansow dla danego kina i dla danej daty, w tym przypadku krakow-plaza, dzisiaj
    // Uwaga! mozna to zlaczyc z exampleGetCinemaMovies, bo to ten sam endpoint.
    // ExternalEvent mozemy zlaczyc z ExternalMovie przy pomocy pola ExternalEvent::externalMovieId i ExternalMovie::externalId
    private void exampleGetCinemaEvents() {

        // Mock
        ExternalCinema krakowPlazaCinema = new ExternalCinema("1063", "Kraków - Galeria Plaza", "al. Pokoju 44, 31-546, Kraków");
        ParamCinemaDate parameters = new ParamCinemaDate(krakowPlazaCinema, new Date());

        AsyncTaskGetCinemaEvents task = new AsyncTaskGetCinemaEvents(new AsyncTaskCallback<List<ExternalEvent>>() {
            @Override
            public void onSuccess(List<ExternalEvent> events) {
                for (ExternalEvent event : events) {
                    System.out.println(event.toString());
                }
            }

            @Override
            public void onFailure(String message) {
                System.out.println("exampleGetCinemaEvents failure");
            }
        });
        task.execute(parameters);
    }
}

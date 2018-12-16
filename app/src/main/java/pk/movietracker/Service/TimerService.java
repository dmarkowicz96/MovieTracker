package pk.movietracker.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pk.movietracker.db.dao.MovieDAO;
import pk.movietracker.model.Movie;
import pk.movietracker.parser.asynctask.AsyncTaskCallback;
import pk.movietracker.parser.asynctask.AsyncTaskGetCinemaEvents;
import pk.movietracker.parser.asynctask.paramwrapper.ParamCinemaDate;
import pk.movietracker.parser.model.ExternalCinema;
import pk.movietracker.parser.model.ExternalEvent;

public class TimerService extends Service {

    private static int SCHEDULE_TIME = 10*60* 1000;
    private MovieDAO movieDAO;
    public TimerService() {
        movieDAO = new MovieDAO();
    }

    private static Timer timer = new Timer();
    private final IBinder boundServiceBinder = new BoundServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "On Bound Service started", Toast.LENGTH_LONG).show();
        timer.scheduleAtFixedRate(new mainTask(), 0, SCHEDULE_TIME);
        return boundServiceBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new mainTask(), 0, SCHEDULE_TIME);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        //timer.cancel();
        return false;
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            //zrzucenie timestampa 1 - konwertowanie na milisekundy
            Date date = new Date(System.currentTimeMillis());
            long millis = date.getTime();

            //rzeczywiste zrzucenie do sharedpreferences
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            prefs.edit().putLong("time", date.getTime()).apply();
            //odczytać dane w formie Date możemy tą komendą Date myDate = new Date(prefs.getLong("time", 0));


            //zadanie które uruchamia pobieranie danych z wifi
            synchronizeData();
        }
    }

    public class BoundServiceBinder extends Binder {
        public TimerService getService(){
            return TimerService.this;
        }

    }

    private void synchronizeData() {

        ArrayList<Movie> oldMovies = movieDAO.getMoviesToSynchronize();
        asyncUpdate(oldMovies);
    }

    // Pobiera godziny seansow dla danego kina i dla danej daty, w tym przypadku krakow-plaza, dzisiaj
    // Uwaga! mozna to zlaczyc z exampleGetCinemaMovies, bo to ten sam endpoint.
    // ExternalEvent mozemy zlaczyc z ExternalMovie przy pomocy pola ExternalEvent::externalMovieId i ExternalMovie::externalId
    private void asyncUpdate( ArrayList<Movie> oldMovies) {

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

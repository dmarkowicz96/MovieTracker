package pk.movietracker.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pk.movietracker.db.dao.MovieDAO;
import pk.movietracker.model.Movie;
import pk.movietracker.parser.ParserUtil;
import pk.movietracker.parser.asynctask.AsyncTaskCallback;
import pk.movietracker.parser.asynctask.AsyncTaskGetCinemaEvents;
import pk.movietracker.parser.asynctask.AsyncTaskGetCinemaMovies;
import pk.movietracker.parser.asynctask.paramwrapper.ParamCinemaDate;
import pk.movietracker.parser.model.DariuszMovie;
import pk.movietracker.parser.model.ExternalCinema;
import pk.movietracker.parser.model.ExternalEvent;
import pk.movietracker.parser.model.ExternalMovie;

public class TimerService extends Service {

    private static int SCHEDULE_TIME = 1*60* 1000;
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

    private void asyncUpdate(final ArrayList<Movie> oldMovies ) {

        // Mock
        ExternalCinema krakowPlazaCinema = new ExternalCinema("1063", "Kraków - Galeria Plaza", "al. Pokoju 44, 31-546, Kraków");

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Date today = calendar.getTime();
        System.out.println("today:    " + today);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        ParamCinemaDate parameters = new ParamCinemaDate(krakowPlazaCinema, tomorrow);

        AsyncTaskGetCinemaMovies task = new AsyncTaskGetCinemaMovies(new AsyncTaskCallback<List<ExternalMovie>>() {
            @Override
            public void onSuccess(List<ExternalMovie> movies) {
                List<Movie> recvMovies = new LinkedList<>();
                for (ExternalMovie movie : movies) {
                    for (ExternalEvent event : movie.getEvents()) {
                        try {
                            Date d = ParserUtil.convertEventDate(event.getEventDate());
                            recvMovies.add(new Movie( movie.getName(), movie.getDescription(), d,false));


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                for (Movie sMovie : recvMovies) {
                    boolean founded = false;
                    for( Movie old : oldMovies) {
                        if(old.getName().equals(sMovie.getName()) && old.getDate().compareTo(sMovie.getDate()) == 0) {
                            founded = true;
                        }
                    }
                    if(!founded) {
                        movieDAO.insertMovie(sMovie);
                    }
                }

               // for (DariuszMovie dariuszMovie : recvMovies) {
                  //  System.out.println(dariuszMovie.toString());
                //}
            }

            @Override
            public void onFailure(String message) {
                System.out.println("exampleGetCinemaMovies failure");
            }
        });
        task.execute(parameters);
    }

}

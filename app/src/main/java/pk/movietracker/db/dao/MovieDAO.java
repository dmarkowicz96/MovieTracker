package pk.movietracker.db.dao;

import android.database.Cursor;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pk.movietracker.db.BaseDao;
import pk.movietracker.model.Movie;

public class MovieDAO extends BaseDao {

    public void insertMovie(Movie movie) {
        //databaseHelper.getWritableDatabase().execSQL("INSERT INTO movie (name, last_name) VALUES( '""');");
    }

    public Movie getMovie(long id) {
        Movie movie = null;
        String idString = String.valueOf(id);
        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery("SELECT * FROM movie WHERE id=?", new String[]{idString});
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                id = cursor.getLong(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String rDate = cursor.getString(cursor.getColumnIndex("start_date"));
                String sFavorite = cursor.getString(cursor.getColumnIndex("favorite"));
                boolean favorite = sFavorite.equals("1")? true : false;

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d=new Date();
                try {
                    d=  dateFormat.parse(rDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                movie = new Movie(id,name,description,d,favorite);
            }
        }
        cursor.close();

        return movie;
    }

    public ArrayList<Movie> getFavoriteMovies() {
        ArrayList<Movie> movieList = new ArrayList<>();

        Cursor cursor = databaseHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM movie WHERE favorite='1'",
                        new String[]{});

        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String rDate = cursor.getString(cursor.getColumnIndex("start_date"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date d=new Date();
                    try {
                        d=  dateFormat.parse(rDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    movieList.add(new Movie(id,name,description,d));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();

        return movieList;
    }

    public void updateState(Movie movie) {
        String favorite = movie.isFavorite() ? "1" : "0";
        databaseHelper.getWritableDatabase().execSQL("UPDATE movie SET favorite='" + favorite+ "' WHERE id='" + movie.getId() +"';");
    }

    public ArrayList<Movie> getMoviesToSynchronize() {
        ArrayList<Movie> movieList = new ArrayList<>();
        Cursor cursor = databaseHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM movie",
                        new String[]{});

        // WHERE date > now

        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String rDate = cursor.getString(cursor.getColumnIndex("start_date"));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date d=new Date();
                    try {
                        d=  dateFormat.parse(rDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    movieList.add(new Movie(id,name,description,d));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();

        return movieList;
    }

    public ArrayList<Movie> getMovies(Date date) {
        ArrayList<Movie> movieList = new ArrayList<>();

        Cursor cursor = databaseHelper.getReadableDatabase()
                .rawQuery("SELECT * FROM movie WHERE date(start_date, 'start of day') = ?",
                        new String[]{formatDate(date)});

        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String rDate = cursor.getString(cursor.getColumnIndex("start_date"));
                    String sFavorite = cursor.getString(cursor.getColumnIndex("favorite"));
                    boolean favorite = sFavorite.equals("1")? true : false;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date d=new Date();
                    try {
                        d=  dateFormat.parse(rDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    movieList.add(new Movie(id,name,description,d,favorite));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();

        return movieList;
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("yyy-MM-dd").format(date);
    }

}

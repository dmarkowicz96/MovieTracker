package pk.movietracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance = null;

    private static final String CREATE_TABLE_MOVIES = "CREATE TABLE movie(" +
            "id integer PRIMARY KEY AUTOINCREMENT not null," +
            "name varchar2(50)," +
            "description varchar2(50000)," +
            "start_date date," +
            "favorite integer" +
            ");";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIES);

        initialData(db);
    }

    private void initialData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO movie (name,description,start_date,favorite) VALUES ('Film1','Opis filmu 1', '2018-12-10', '0')");
        db.execSQL("INSERT INTO movie (name,description,start_date,favorite) VALUES ('Film2','Opis filmu 2', '2018-12-10', '0')");
        db.execSQL("INSERT INTO movie (name,description,start_date,favorite) VALUES ('Film3','Opis filmu 3', '2018-12-10', '0')");
        db.execSQL("INSERT INTO movie (name,description,start_date,favorite) VALUES ('Film4','Opis filmu 4', '2018-12-10', '0')");
        db.execSQL("INSERT INTO movie (name,description,start_date,favorite) VALUES ('Film5','Opis filmu 5', '2018-12-10', '0')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DatabaseHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext(), "MovieTrackerDBDM", null, 1);;
        }

        return instance;
    }
}

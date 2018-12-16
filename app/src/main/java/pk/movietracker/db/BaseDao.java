package pk.movietracker.db;

public abstract class BaseDao {
    protected DatabaseHelper databaseHelper;

    public BaseDao() {
        databaseHelper = DatabaseHelper.getInstance(null);
    }
}

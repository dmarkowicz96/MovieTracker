package pk.movietracker.parser.asynctask;

public interface AsyncTaskCallback<T> {

    public void onSuccess(T object);
    public void onFailure(String message);
}

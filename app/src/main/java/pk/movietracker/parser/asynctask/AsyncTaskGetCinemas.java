package pk.movietracker.parser.asynctask;

import android.os.AsyncTask;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pk.movietracker.parser.ParserUtil;
import pk.movietracker.parser.model.ExternalCinema;


public class AsyncTaskGetCinemas extends AsyncTask<Void, Void, Void> {

    private static final String GET_CINEMA_URL = "https://www.cinema-city.pl/pl/data-api-service/v1/quickbook/10103/cinemas/with-event/until/${date}?attr=&lang=pl_PL";
    private AsyncTaskCallback<List<ExternalCinema>> callback;

    public AsyncTaskGetCinemas(AsyncTaskCallback<List<ExternalCinema>> callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ParserUtil.getFormattedUrl(GET_CINEMA_URL,
                        ImmutableMap.of("date", ParserUtil.formatDate(new Date()))))
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure("failure");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                JsonObject root = new JsonParser().parse(response.body().string()).getAsJsonObject();
                JsonArray cinemas = root.getAsJsonObject("body").getAsJsonArray("cinemas");
                List<ExternalCinema> externalCinemas = new LinkedList<>();
                for (JsonElement cinema : cinemas) {
                    ExternalCinema externalCinema = new Gson().fromJson(cinema, ExternalCinema.class);
                    externalCinemas.add(externalCinema);
                }
                callback.onSuccess(externalCinemas);
            }
        });

        return null;
    }

}

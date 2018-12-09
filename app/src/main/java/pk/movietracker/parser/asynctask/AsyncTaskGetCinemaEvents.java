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
import java.util.ArrayList;
import java.util.List;

import pk.movietracker.parser.ParserUtil;
import pk.movietracker.parser.asynctask.paramwrapper.ParamCinemaDate;
import pk.movietracker.parser.model.ExternalEvent;


public class AsyncTaskGetCinemaEvents extends AsyncTask<ParamCinemaDate, Void, Void> {

    private static final String GET_EVENTS_FROM_CINEMA_BY_DATE = "https://www.cinema-city.pl/pl/data-api-service/v1/quickbook/10103/film-events/in-cinema/${externalId}/at-date/${date}?attr=&lang=pl_PL";
    private AsyncTaskCallback<List<ExternalEvent>> callback;

    public AsyncTaskGetCinemaEvents(AsyncTaskCallback<List<ExternalEvent>> callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ParamCinemaDate... paramGetMovies) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ParserUtil.getFormattedUrl(GET_EVENTS_FROM_CINEMA_BY_DATE,
                        ImmutableMap.of(
                                "externalId", paramGetMovies[0].getCinema().getExternalId(),
                                "date", ParserUtil.formatDate(paramGetMovies[0].getDate()))))
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure("failure");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                JsonObject root = new JsonParser().parse(response.body().string()).getAsJsonObject();
                JsonArray events = root.getAsJsonObject("body").getAsJsonArray("events");
                List<ExternalEvent> externalEvents = new ArrayList<>();

                for (JsonElement movie : events) {
                    ExternalEvent externalEvent = new Gson().fromJson(movie, ExternalEvent.class);
                    externalEvents.add(externalEvent);
                }
                callback.onSuccess(externalEvents);
            }
        });

        return null;
    }
}

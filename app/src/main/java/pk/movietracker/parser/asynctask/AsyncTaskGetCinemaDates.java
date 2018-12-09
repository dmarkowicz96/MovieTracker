package pk.movietracker.parser.asynctask;

import android.os.AsyncTask;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pk.movietracker.parser.ParserUtil;
import pk.movietracker.parser.model.ExternalCinema;


public class AsyncTaskGetCinemaDates extends AsyncTask<ExternalCinema, Void, Void> {

    private static final String GET_CINEMA_DATES = "https://www.cinema-city.pl/pl/data-api-service/v1/quickbook/10103/dates/in-cinema/${externalId}/until/${date}?attr=&lang=pl_PL";
    public AsyncTaskCallback<List<Date>> callback;

    public AsyncTaskGetCinemaDates(AsyncTaskCallback<List<Date>> callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ExternalCinema... externalCinemas) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ParserUtil.getFormattedUrl(GET_CINEMA_DATES,
                        ImmutableMap.of(
                                "externalId", externalCinemas[0].getExternalId(),
                                "date", ParserUtil.formatDate(ParserUtil.getYearInFutureDate()))))
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure("failure");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                JsonObject root = new JsonParser().parse(response.body().string()).getAsJsonObject();
                JsonArray dates = root.getAsJsonObject("body").getAsJsonArray("dates");
                List<Date> parsedDates = new ArrayList<>();

                for (JsonElement date : dates) {
                    try {
                        Date parsedDate = ParserUtil.getFormatter().parse(date.getAsString());
                        parsedDates.add(parsedDate);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                callback.onSuccess(parsedDates);
            }
        });

        return null;
    }
}

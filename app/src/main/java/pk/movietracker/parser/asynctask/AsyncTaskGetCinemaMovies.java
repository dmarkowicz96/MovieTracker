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

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pk.movietracker.parser.ParserUtil;
import pk.movietracker.parser.asynctask.paramwrapper.ParamCinemaDate;
import pk.movietracker.parser.model.ExternalEvent;
import pk.movietracker.parser.model.ExternalMovie;


public class AsyncTaskGetCinemaMovies extends AsyncTask<ParamCinemaDate, Void, Void> {

    private static final String GET_MOVIES_FROM_CINEMA_BY_DATE = "https://www.cinema-city.pl/pl/data-api-service/v1/quickbook/10103/film-events/in-cinema/${externalId}/at-date/${date}?attr=&lang=pl_PL";
    private static final String GET_MOVIE_DESCRIPTION_BY_NAME_AND_ID = "https://www.cinema-city.pl/${link}/${externalId}";
    private static final Pattern FILM_DETAILS_PATTERN = Pattern.compile("var filmDetails = \\{(.*?)\\}");
    private AsyncTaskCallback<List<ExternalMovie>> callback;

    public AsyncTaskGetCinemaMovies(AsyncTaskCallback<List<ExternalMovie>> callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ParamCinemaDate... paramGetMovies) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ParserUtil.getFormattedUrl(GET_MOVIES_FROM_CINEMA_BY_DATE,
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
                JsonArray movies = root.getAsJsonObject("body").getAsJsonArray("films");
                List<ExternalMovie> externalMovies = new ArrayList<>();

                for (JsonElement movie : movies) {
                    ExternalMovie externalMovie = new Gson().fromJson(movie, ExternalMovie.class);
                    externalMovie.setDescription(getMovieDescription(externalMovie));
                    externalMovies.add(externalMovie);
                }

                JsonArray events = root.getAsJsonObject("body").getAsJsonArray("events");
                for (JsonElement movie : events) {
                    ExternalEvent externalEvent = new Gson().fromJson(movie, ExternalEvent.class);
                    for (ExternalMovie externalMovie : externalMovies) {
                        if (externalMovie.getExternalId().equals(externalEvent.getExternalMovieId())) {
                            externalMovie.getEvents().add(externalEvent);
                            break;
                        }
                    }
                }

                callback.onSuccess(externalMovies);
            }
        });

        return null;
    }

    private String getMovieDescription(ExternalMovie externalMovie) {

        try {
            String url = ParserUtil.getFormattedUrl(GET_MOVIE_DESCRIPTION_BY_NAME_AND_ID,
                    ImmutableMap.of(
                            "link", externalMovie.getLink(),
                            "externalId", externalMovie.getExternalId()
                    ));

            Document document = Jsoup.connect(url).get();
            JsonObject filmDetails = null;

            for (Element script : document.getElementsByTag("script")) {
                for (DataNode dataNode : script.dataNodes()) {
                    Matcher matcher = FILM_DETAILS_PATTERN.matcher(dataNode.getWholeData());
                    if (matcher.find()) {
                        filmDetails = new JsonParser()
                                .parse("{" + matcher.group(1) + "}")
                                .getAsJsonObject();
                        break;
                    }
                }
                if (filmDetails != null) {
                    break;
                }
            }
            return filmDetails == null ? null : filmDetails.get("synopsis").getAsString();
        } catch (Exception e) {
            return null;
        }
    }
}

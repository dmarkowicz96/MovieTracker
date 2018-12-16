package pk.movietracker.parser;


import org.apache.commons.lang3.text.StrSubstitutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ParserUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static final SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.ENGLISH);

    public static SimpleDateFormat getFormatter() {
        return dateFormat;
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date convertEventDate(String eventDate) throws ParseException {
        return eventDateFormat.parse(eventDate);
    }

    public static Date getYearInFutureDate() {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.YEAR,1);
        return date.getTime();
    }

    public static String getFormattedUrl(String urlTemlpate, Map<String, String> parameters) {
        StrSubstitutor substitutor = new StrSubstitutor(parameters);
        return substitutor.replace(urlTemlpate);
    }

}


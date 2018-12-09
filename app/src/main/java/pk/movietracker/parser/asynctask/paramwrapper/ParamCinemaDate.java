package pk.movietracker.parser.asynctask.paramwrapper;

import java.util.Date;

import pk.movietracker.parser.model.ExternalCinema;

public class ParamCinemaDate {

    private ExternalCinema cinema;
    private Date date;

    public ParamCinemaDate() { }

    public ParamCinemaDate(ExternalCinema cinema, Date date) {
        this.cinema = cinema;
        this.date = date;
    }

    public ExternalCinema getCinema() {
        return cinema;
    }

    public void setCinema(ExternalCinema cinema) {
        this.cinema = cinema;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

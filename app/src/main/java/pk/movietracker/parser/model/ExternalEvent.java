package pk.movietracker.parser.model;

import com.google.gson.annotations.SerializedName;

public class ExternalEvent {

    @SerializedName("id")
    private String externalId;
    @SerializedName("filmId")
    private String externalMovieId;
    @SerializedName("cinemaId")
    private String externalCinemaId;
    @SerializedName("eventDateTime")
    private String eventDate;
    private Boolean soldOut;
    private String bookingLink;

    public ExternalEvent() {
    }

    public ExternalEvent(String externalId, String externalMovieId, String externalCinemaId, String eventDate, Boolean soldOut, String bookingLink) {
        this.externalId = externalId;
        this.externalMovieId = externalMovieId;
        this.externalCinemaId = externalCinemaId;
        this.eventDate = eventDate;
        this.soldOut = soldOut;
        this.bookingLink = bookingLink;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalMovieId() {
        return externalMovieId;
    }

    public void setExternalMovieId(String externalMovieId) {
        this.externalMovieId = externalMovieId;
    }

    public String getExternalCinemaId() {
        return externalCinemaId;
    }

    public void setExternalCinemaId(String externalCinemaId) {
        this.externalCinemaId = externalCinemaId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Boolean getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
    }

    public String getBookingLink() {
        return bookingLink;
    }

    public void setBookingLink(String bookingLink) {
        this.bookingLink = bookingLink;
    }

    @Override
    public String toString() {
        return "ExternalEvent{" +
                "externalId='" + externalId + '\'' +
                ", externalMovieId='" + externalMovieId + '\'' +
                ", externalCinemaId='" + externalCinemaId + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", soldOut=" + soldOut +
                ", bookingLink='" + bookingLink + '\'' +
                '}';
    }
}

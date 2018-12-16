package pk.movietracker.model;

import java.util.Date;

public class Movie {
    private long id;
    private String name;
    private String description;
    private Date date;
    private boolean isFavorite;

    public Movie() {

    }

    public Movie(long id, String name, String description, Date date, boolean favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.isFavorite = favorite;
    }

    public Movie(String name, String description, Date date, boolean favorite) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.isFavorite = favorite;
    }

    public Movie(long id, String name, String description, Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
